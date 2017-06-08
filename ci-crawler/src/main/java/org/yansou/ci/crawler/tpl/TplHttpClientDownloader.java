package org.yansou.ci.crawler.tpl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yansou.ci.common.time.TimeStat;

import com.google.common.collect.Sets;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.AbstractDownloader;
import us.codecraft.webmagic.selector.PlainText;
import us.codecraft.webmagic.utils.HttpConstant;
import us.codecraft.webmagic.utils.UrlUtils;

public class TplHttpClientDownloader extends AbstractDownloader {
	private Logger logger = LoggerFactory.getLogger(getClass());

	protected Page handleResponse(Request request, String charset, HttpResponse httpResponse, Task task)
			throws IOException {
		String content = getContent(charset, httpResponse);
		Page page = new Page();
		page.setRawText(content);
		page.setUrl(new PlainText(request.getUrl()));
		page.setRequest(request);
		page.setStatusCode(httpResponse.getStatusLine().getStatusCode());
		return page;
	}

	protected String getHtmlCharset(HttpResponse httpResponse, byte[] contentBytes) throws IOException {
		String charset;
		// charset
		// 1、encoding in http header Content-Type
		String value = httpResponse.getEntity().getContentType().getValue();
		charset = UrlUtils.getCharset(value);
		if (StringUtils.isNotBlank(charset)) {
			logger.debug("Auto get charset: {}", charset);
			return charset;
		}
		// use default charset to decode first time
		Charset defaultCharset = Charset.defaultCharset();
		String content = new String(contentBytes, defaultCharset.name());
		// 2、charset in meta
		if (StringUtils.isNotEmpty(content)) {
			Document document = Jsoup.parse(content);
			Elements links = document.select("meta");
			for (Element link : links) {
				// 2.1、html4.01 <meta http-equiv="Content-Type"
				// content="text/html; charset=UTF-8" />
				String metaContent = link.attr("content");
				String metaCharset = link.attr("charset");
				if (metaContent.indexOf("charset") != -1) {
					metaContent = metaContent.substring(metaContent.indexOf("charset"), metaContent.length());
					charset = metaContent.split("=")[1];
					break;
				}
				// 2.2、html5 <meta charset="UTF-8" />
				else if (StringUtils.isNotEmpty(metaCharset)) {
					charset = metaCharset;
					break;
				}
			}
		}
		logger.debug("Auto get charset: {}", charset);
		// 3、todo use tools as cpdetector for content decode
		return charset;
	}

	protected String getContent(String charset, HttpResponse httpResponse) throws IOException {
		if (charset == null) {
			byte[] contentBytes = IOUtils.toByteArray(httpResponse.getEntity().getContent());
			String htmlCharset = getHtmlCharset(httpResponse, contentBytes);
			if (htmlCharset != null) {
				return new String(contentBytes, htmlCharset);
			} else {
				logger.warn("Charset autodetect failed, use {} as charset. Please specify charset in Site.setCharset()",
						Charset.defaultCharset());
				return new String(contentBytes);
			}
		} else {
			return IOUtils.toString(httpResponse.getEntity().getContent(), charset);
		}
	}

	protected HttpUriRequest getHttpUriRequest(Request request, Site site, Map<String, String> headers) {
		RequestBuilder requestBuilder = selectRequestMethod(request).setUri(request.getUrl());
		if (headers != null)
			headers.forEach(requestBuilder::addHeader);
		@SuppressWarnings("unchecked")
		Map<String, String> reqHeaders = (Map<String, String>) request.getExtra("headers");
		if (null != reqHeaders)
			reqHeaders.forEach(requestBuilder::addHeader);
		@SuppressWarnings("deprecation")
		RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
				.setConnectionRequestTimeout(site.getTimeOut()).setSocketTimeout(site.getTimeOut())
				.setConnectTimeout(site.getTimeOut()).setCookieSpec(CookieSpecs.BEST_MATCH);
		requestBuilder.setConfig(requestConfigBuilder.build());
		return requestBuilder.build();
	}

	// private final Map<String, CloseableHttpClient> httpClients = new
	// HashMap<String, CloseableHttpClient>();

	private HttpClientGenerator0 httpClientGenerator = new HttpClientGenerator0();

	private CloseableHttpClient getHttpClient(Site site) {
		return httpClientGenerator.getClient(site);
		//
		// if (site == null) {
		// return httpClientGenerator.getClient(null);
		// }
		// String domain = site.getDomain();
		// CloseableHttpClient httpClient = httpClients.get(domain);
		// if (httpClient == null) {
		// synchronized (this) {
		// httpClient = httpClients.get(domain);
		// if (httpClient == null) {
		// httpClient = httpClientGenerator.getClient(site);
		// httpClients.put(domain, httpClient);
		// }
		// }
		// }
		// return httpClient;
	}

	@Override
	public void setThread(int thread) {
		httpClientGenerator.setPoolSize(thread);
	}

	@Override
	public Page download(Request request, Task task) {
		TimeStat ts = new TimeStat();
		Site site = null;
		if (task != null) {
			site = task.getSite();
		}
		Set<Integer> acceptStatCode;
		String charset = null;
		Map<String, String> headers = null;
		if (site != null) {
			acceptStatCode = site.getAcceptStatCode();
			charset = site.getCharset();
			headers = site.getHeaders();
		} else {
			acceptStatCode = Sets.newHashSet(200);
		}
		CloseableHttpResponse httpResponse = null;
		int statusCode = 0;
		HttpUriRequest httpUriRequest = null;
		try {
			BasicHttpContext context = new BasicHttpContext();
			httpUriRequest = getHttpUriRequest(request, site, headers);
			httpResponse = getHttpClient(site).execute(httpUriRequest, context);
			statusCode = httpResponse.getStatusLine().getStatusCode();
			request.putExtra("status_code", statusCode);
			if (statusAccept(acceptStatCode, statusCode)) {
				Page page = handleResponse(request, charset, httpResponse, task);
				HttpRequestWrapper warpReq = (HttpRequestWrapper) context.getAttribute("http.request");
				String newReq = "http://" + warpReq.getLastHeader("Host").getValue() + warpReq.getURI();
				page.setUrl(new PlainText(newReq));
				onSuccess(request);

				return page;
			} else {
				logger.warn("code error " + statusCode + "\t" + request.getUrl());
				return null;
			}
		} catch (IOException e) {
			statusCode = -1;
			logger.warn("download page " + request.getUrl() + " error. MSG[{}]", e.getMessage());
			if (site.getCycleRetryTimes() > 0) {
				Page page = addToCycleRetry(request, site);
				if (null == page) {
					logger.info("[Abandon]{}", request.getUrl());
				}
				return page;
			}
			onError(request);
			return null;
		} finally {
			try {
				if (httpResponse != null) {
					EntityUtils.consume(httpResponse.getEntity());
				} else {
					if (null != httpUriRequest)
						httpUriRequest.abort();
				}
			} catch (IOException e) {
				logger.warn("close response fail", e);
			}
			request.putExtra("status_code", statusCode);
			ts.buriePrint("downloader:{}", logger::info);
		}
	}

	private Page addToCycleRetry(Request request, Site site) {
		// TODO Auto-generated method stub
		return null;
	}

	protected boolean statusAccept(Set<Integer> acceptStatCode, int statusCode) {
		return acceptStatCode.contains(statusCode);
	}

	protected RequestBuilder selectRequestMethod(Request request) {
		String method = request.getMethod();
		if (method == null || method.equalsIgnoreCase(HttpConstant.Method.GET)) {
			// default get
			return RequestBuilder.get();
		} else if (method.equalsIgnoreCase(HttpConstant.Method.POST)) {
			RequestBuilder requestBuilder = RequestBuilder.post();
			NameValuePair[] nameValuePair = (NameValuePair[]) request.getExtra("nameValuePair");
			if (nameValuePair.length > 0) {
				requestBuilder.addParameters(nameValuePair);
			}
			return requestBuilder;
		} else if (method.equalsIgnoreCase(HttpConstant.Method.HEAD)) {
			return RequestBuilder.head();
		} else if (method.equalsIgnoreCase(HttpConstant.Method.PUT)) {
			return RequestBuilder.put();
		} else if (method.equalsIgnoreCase(HttpConstant.Method.DELETE)) {
			return RequestBuilder.delete();
		} else if (method.equalsIgnoreCase(HttpConstant.Method.TRACE)) {
			return RequestBuilder.trace();
		}
		throw new IllegalArgumentException("Illegal HTTP Method " + method);
	}

	public static void setDefaultRetryTimes(int num) {
		HttpClientGenerator0.DEFAULT_RETRY_TIMES = num;
	}

	static class HttpClientGenerator0 {
		/**
		 * 默认重试次数
		 */
		private static int DEFAULT_RETRY_TIMES = 4;
		private PoolingHttpClientConnectionManager connectionManager;

		HttpClientGenerator0() {
			Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", PlainConnectionSocketFactory.INSTANCE)
					.register("https", SSLConnectionSocketFactory.getSocketFactory()).build();
			connectionManager = new PoolingHttpClientConnectionManager(registry);
			connectionManager.setDefaultMaxPerRoute(100);
			connectionManager.setDefaultSocketConfig(
					SocketConfig.custom().setTcpNoDelay(true).setSoKeepAlive(true).setSoReuseAddress(true).build());
		}

		public HttpClientGenerator0 setPoolSize(int poolSize) {
			connectionManager.setMaxTotal(poolSize);
			return this;
		}

		public CloseableHttpClient getClient(Site site) {
			return generateClient(site);
		}

		private CloseableHttpClient generateClient(Site site) {
			if (site.getRetryTimes() == 0 && 0 != DEFAULT_RETRY_TIMES) {
				site.setRetryTimes(DEFAULT_RETRY_TIMES);
			}
			HttpClientBuilder httpClientBuilder = HttpClients.custom();
			// .setConnectionManager(connectionManager);
			if (site != null && site.getUserAgent() != null) {
				httpClientBuilder.setUserAgent(site.getUserAgent());
			} else {
				httpClientBuilder.setUserAgent("");
			}
			if (site == null || site.isUseGzip()) {
				httpClientBuilder.addInterceptorFirst(new HttpRequestInterceptor() {
					public void process(final HttpRequest request, final HttpContext context)
							throws HttpException, IOException {
						if (!request.containsHeader("Accept-Encoding")) {
							request.addHeader("Accept-Encoding", "gzip");
						}
					}
				});
			}
			SocketConfig socketConfig = SocketConfig.custom().setSoKeepAlive(true).setTcpNoDelay(true).build();
			httpClientBuilder.setDefaultSocketConfig(socketConfig);
			if (site != null) {
				httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(site.getRetryTimes(), true));
			}
			httpClientBuilder.setRetryHandler((exception, executionCount, context) -> {
				if (executionCount >= 5)
					return false;
				if (exception instanceof NoHttpResponseException)
					return true;
				else if (exception instanceof ClientProtocolException)
					return true;
				return false;
			});
			generateCookie(httpClientBuilder, site);
			return httpClientBuilder.build();
		}

		private void generateCookie(HttpClientBuilder httpClientBuilder, Site site) {
			CookieStore cookieStore = new BasicCookieStore();
			for (Map.Entry<String, String> cookieEntry : site.getCookies().entrySet()) {
				BasicClientCookie cookie = new BasicClientCookie(cookieEntry.getKey(), cookieEntry.getValue());
				cookie.setDomain(site.getDomain());
				cookieStore.addCookie(cookie);
			}
			for (Map.Entry<String, Map<String, String>> domainEntry : site.getAllCookies().entrySet()) {
				for (Map.Entry<String, String> cookieEntry : domainEntry.getValue().entrySet()) {
					BasicClientCookie cookie = new BasicClientCookie(cookieEntry.getKey(), cookieEntry.getValue());
					cookie.setDomain(domainEntry.getKey());
					cookieStore.addCookie(cookie);
				}
			}
			httpClientBuilder.setDefaultCookieStore(cookieStore);
		}

	}
}
