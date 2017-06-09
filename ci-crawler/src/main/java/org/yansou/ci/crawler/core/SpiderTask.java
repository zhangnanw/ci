package org.yansou.ci.crawler.core;

import org.yansou.ci.core.db.model.crawl.CrawlSeed;

import com.alibaba.fastjson.JSONObject;

public class SpiderTask implements Runnable {

	private CrawlSeed seed;

	private ConsoleManager consoleManager;

	public SpiderTask(ConsoleManager consoleManager, CrawlSeed seed) {
		this.consoleManager = consoleManager;
		this.seed = seed;
	}

	JSONObject request = new JSONObject();

	@Override
	public void run() {

	}
}
