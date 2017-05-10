package com.yansou.ci.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author liutiejun
 * @create 2017-04-11 10:24
 */
@EnableEurekaServer
@SpringBootApplication
public class CiEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CiEurekaServerApplication.class, args);
	}

}
