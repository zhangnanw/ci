package org.yansou.ci.crawler.utils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yansou.ci.crawler.entity.Paper;

import java.io.IOException;

public class HtmlUtil {

	private static Logger logger = LoggerFactory.getLogger(HtmlUtil.class);

	/**
	 * 下载页面 获取内容
	 * 
	 * @param url
	 * @return
	 */
	public static String getContent(String url) {
		
		String result = null;
		HttpClientBuilder builder = HttpClients.custom();
		CloseableHttpClient httpclient = builder.build();
		HttpGet request = new HttpGet(url);
//		request.setHeader(
//				"User-Agent",
//				"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
		try {
			long start_time = System.currentTimeMillis();
			CloseableHttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity);
			logger.info("下载页面成功URL是{},耗时{}毫秒 ", url, System.currentTimeMillis()
					- start_time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 下载页面 获取内容byte字节
	 * 
	 * @param url
	 * @param type
	 *            content-type类型
	 * @return
	 * @throws IOException 
	 */
	public static byte[] getContentByte(Paper paper, String type) throws Exception {
		byte[] result = null;
		HttpClientBuilder builder = HttpClients.custom();
		CloseableHttpClient httpclient = builder.build();
		HttpGet request = new HttpGet(paper.getDownloadUrl());
		request.setHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
		
		long start_time = System.currentTimeMillis();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
		request.setConfig(requestConfig);
		CloseableHttpResponse response = httpclient.execute(request);
		if (response.containsHeader("Content-Type")) {
			Header header = response.getHeaders("Content-Type")[0];
			if (!header.getValue().toLowerCase().contains(type)) {
				System.out.println("content-type实际类型:" + header.getValue());
				System.out.println("不是" + type + "页面不再进行下载！！！" + paper.getDownloadUrl());
				paper.setFlag(3);
				return null;
			}
		}
		HttpEntity entity = response.getEntity();
		result = EntityUtils.toByteArray(entity);
		logger.info("下载文件{},耗时{}毫秒 ", paper.getDownloadUrl(), System.currentTimeMillis()
				- start_time);
		return result;
	}

	/**
	 * 下载页面 获取内容
	 * 
	 * @param url
	 * @return
	 */
	public static String getContent(String url, String httpProxyHost,
			int httpProxyPort) {
		IllegalArgumentException thr = null;
		for (int i = 0; i < 5; i++) {
			String result = null;
			HttpHost proxy = new HttpHost(httpProxyHost, httpProxyPort);
			HttpClientBuilder build = HttpClients.custom();
			CloseableHttpClient httpclient = build.setProxy(proxy).build();
			HttpGet request = new HttpGet(url);
			request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			request.setHeader("Accept-Encoding", "gzip, deflate, sdch");
			request.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
			request.setHeader("Host", "academic.research.microsoft.com");
			request.setHeader("Connection", "keep-alive");
			request.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.36");
//			request.setHeader("User-Agent","");
			try {
				long start_time = System.currentTimeMillis();
				CloseableHttpResponse response = httpclient.execute(request);
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity);
				logger.info("下载页面成功URL是{},耗时{}毫秒 ", url,
						System.currentTimeMillis() - start_time);
				return result;
			} catch (Exception e) {
				thr = new IllegalArgumentException(e);
			}
		}
		throw thr;
	}
	
	
	public static byte[] getContentByPicture(String url) {
		byte[] result = null;
		HttpClientBuilder builder = HttpClients.custom();
		CloseableHttpClient httpclient = builder.build();
		HttpGet request = new HttpGet(url);
		request.setHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
		try {
			long start_time = System.currentTimeMillis();
			CloseableHttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			result =  EntityUtils.toByteArray(entity);
			logger.info("下载页面成功URL是{},耗时{}毫秒 ", url, System.currentTimeMillis()
					- start_time);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if (request != null) {
					request.abort();
				}
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
}
