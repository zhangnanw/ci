package org.yansou.ci.crawler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yansou.ci.core.crawl.model.CrawlStatus;
import org.yansou.ci.crawler.core.ConsoleManager;

@Controller
@RequestMapping("/console")
public class CrawlConsoleController {
	@Autowired
	private ConsoleManager consoleManager;
	@GetMapping("/status")
	public CrawlStatus status() {
		return null;
	}

}
