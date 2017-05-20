package org.yansou.ci.storage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.yansou.ci.storage.interceptor.LoggingInterceptor;

/**
 * @author liutiejun
 * @create 2017-04-19 10:25
 */
@Configuration
public class CiWebMvcConfigurer extends WebMvcConfigurerAdapter {

	@Autowired
	private LoggingInterceptor loggingInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loggingInterceptor);
	}

}
