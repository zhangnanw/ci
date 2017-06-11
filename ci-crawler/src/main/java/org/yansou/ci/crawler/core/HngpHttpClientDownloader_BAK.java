package org.yansou.ci.crawler.core;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
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
import org.apache.http.protocol.HttpContext;

import com.google.common.collect.Maps;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.utils.HttpConstant;

public class HngpHttpClientDownloader_BAK extends HttpClientDownloader {
	private static List<String> _HeaderKeyList = Arrays.asList("Proxy-Authorization", "Proxy-Switch-Ip", "User-Agent",
			"Accept", "Connection", "Cookie", "Host", "Accept-Encoding", "Accept-Language");
	private HttpClientGenerator0 httpClientGenerator = new HttpClientGenerator0();
	private Map<Site, CloseableHttpClient> httpClients = Maps.newHashMap();

	private CloseableHttpClient getHttpClient(Site site) {
		// return httpClientGenerator.getClient(site);
		if (site == null) {
			return httpClientGenerator.getClient(null);
		}
		CloseableHttpClient httpClient = httpClients.get(site);
		if (httpClient == null) {
			synchronized (this) {
				httpClient = httpClients.get(site);
				if (httpClient == null) {
					httpClient = httpClientGenerator.getClient(site);
					httpClients.put(site, httpClient);
				}
			}
		}
		return httpClient;
	}

	protected boolean statusAccept(Set<Integer> acceptStatCode, int statusCode) {
		return acceptStatCode.contains(statusCode);
	}

	/**
	 * 处理锚标记
	 * 
	 * @param request
	 */
	private void anchorEdit(Request request) {

	}

	protected RequestBuilder selectRequestMethod(Request request) {
		String method = request.getMethod();
		if (null == method) {
			method = StringUtils.EMPTY;
		}
		if (method.equalsIgnoreCase(HttpConstant.Method.GET)) {
			return RequestBuilder.get();
		} else if (method.equalsIgnoreCase(HttpConstant.Method.POST)) {
			RequestBuilder requestBuilder = RequestBuilder.post();
			NameValuePair[] nameValuePair = (NameValuePair[]) request.getExtra("nameValuePair");
			if (nameValuePair.length > 0) {
				requestBuilder.addParameters(nameValuePair);
			}
			return requestBuilder;
		} else if (request.getUrl().contains("#ArgPost")) {
			RequestBuilder requestBuilder = RequestBuilder.post();
			try {
				NameValuePair[] ps = URLEncodedUtils.parse(new URI(request.getUrl()), "UTF-8").stream()
						.toArray(x -> new NameValuePair[x]);
				requestBuilder.addParameters(ps);
			} catch (URISyntaxException e) {
				throw new IllegalArgumentException(e);
			}
			request.putExtra("#ArgPost", "true");
			return requestBuilder;
		} else if (StringUtils.isBlank(request.getMethod())) {
			return RequestBuilder.get();
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
			connectionManager.setDefaultMaxPerRoute(400);
			connectionManager.setMaxTotal(400);
			connectionManager.setDefaultSocketConfig(
					SocketConfig.custom().setTcpNoDelay(false).setSoKeepAlive(true).setSoReuseAddress(true).build());

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
			HttpClientBuilder httpClientBuilder = HttpClients.custom().setConnectionManager(connectionManager);
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
