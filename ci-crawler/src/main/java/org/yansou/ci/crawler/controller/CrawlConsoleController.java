package org.yansou.ci.crawler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yansou.ci.core.crawl.model.CrawlStatus;

@Controller
@RequestMapping("/console")
public class CrawlConsoleController {
	
	
	
	
	@GetMapping("/status")
	public CrawlStatus status() {
		return null;
	}

}
