package org.yansou.ci.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author liutiejun
 * @create 2017-04-11 10:24
 */
@EnableDiscoveryClient
@SpringBootApplication
public class CiStorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(CiStorageApplication.class, args);
	}

}
