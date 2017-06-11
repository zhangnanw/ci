package com.pyc.paper.repository;

import org.yansou.ci.crawler.utils.RedisUtils;

public class RedisResposity implements Repository {

	RedisUtils redisUtils = new RedisUtils();
	private String lowkey = "spider.todo.low";
	private String heightkey = "spider.todo.height";

	public String poll() {
		String url = redisUtils.poll(heightkey);
		if (url == null) {
			url = redisUtils.poll(lowkey);
		}
		return url;
	}

	public void add(String nextUrl) {
		this.redisUtils.add(lowkey, nextUrl);
	}

	public void addHeight(String nextUrl) {
		this.redisUtils.add(heightkey, nextUrl);
	}

}
