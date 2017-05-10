package com.yansou.ci.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author liutiejun
 * @create 2017-04-11 10:24
 */
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class CiWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(CiWebApplication.class, args);
	}

}
