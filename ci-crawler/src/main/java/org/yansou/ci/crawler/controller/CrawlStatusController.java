package org.yansou.ci.crawler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yansou.ci.core.crawl.model.CrawlStatus;

@Controller
@RequestMapping
public class CrawlStatusController {
	@GetMapping
	public CrawlStatus status() {
		return null;
	}
}
