package org.yansou.ci.crawler.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实体类:储存某些重要的网页信息
 *     例如:下一页 当前网页源码 快照页面地址 等等
 * 2015年3月18日下午5:50:24
 * @author luanyang
 */
public class Page {

	private String rawHtml; //网页源码内容
	private String url;//来源网址
	private List<String> urlList = new ArrayList<String>();
	private Map<String,String> values = new HashMap<String,String>();//用来存储网页快照 需要抓取的信息
	public String getRawHtml() {
		return rawHtml;
	}

	public void setRawHtml(String rawHtml) {
		this.rawHtml = rawHtml;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<String> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<String> urlList) {
		this.urlList = urlList;
	}
	
	public void addNextUrl(String url){
		this.urlList.add(url);
	}

	public Map<String, String> getValues() {
		return values;
	}

	public void setValues(Map<String, String> values) {
		this.values = values;
	}
	
	public void addUrl(String url){
		this.urlList.add(url);
	}

	public void addFile(String key,String value){
		this.values.put(key, value);
	}
}
