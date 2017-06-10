package org.yansou.ci.crawler.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.yansou.ci.common.time.TimeStat;

/**
 * 下载页面源码工具类 2015年3月18日下午5:50:01
 * 
 * @author luanyang
 */
public class PageUtils {

	public static String getContent(String url) {
		String content = null;
		HttpClientBuilder bulider = HttpClients.custom();
		CloseableHttpClient httpClient = bulider.build();
		HttpGet httpGet = new HttpGet(url);
		try {
			TimeStat ts = new TimeStat();
			CloseableHttpResponse response = httpClient.execute(httpGet);
			ts.buriePrint("request:{}", System.err::println);
			Integer statusLine = response.getStatusLine().getStatusCode();
			if (statusLine == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				content = EntityUtils.toString(entity);
				ts.buriePrint("request:{}  read:{}", System.err::println);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpGet != null) {
					httpGet.abort();
				}
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return content;
	}
}
