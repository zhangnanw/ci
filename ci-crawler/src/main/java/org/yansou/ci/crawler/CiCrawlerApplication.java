package org.yansou.ci.crawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import us.codecraft.webmagic.downloader.HttpClientDownloader;

/**
 * @author liutiejun
 * @create 2017-04-11 10:24
 */
@EnableDiscoveryClient
@SpringBootApplication
public class CiCrawlerApplication {

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	@LoadBalanced
	HttpClientDownloader httpClientDownloader() {
		return new HttpClientDownloader();
	}

	public static void main(String[] args) {
		SpringApplication.run(CiCrawlerApplication.class, args);
	}

}
