package com.yansou.ci.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author liutiejun
 * @create 2017-04-12 15:59
 */
@Configuration
@EnableSwagger2
public class Swagger2Configurer {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.yansou.ci.web.controller"))
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("大数据平台-统一数据服务接口")
				.version("1.0.0")
				.build();
	}

}
