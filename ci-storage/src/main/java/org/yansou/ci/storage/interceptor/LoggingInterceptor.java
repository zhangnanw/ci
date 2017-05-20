package org.yansou.ci.storage.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 计算每一个请求的执行时间
 * </p>
 *
 * @author liutiejun
 * @create 2017-04-19 10:25
 *
 */
@Component
public class LoggingInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOG = LogManager.getLogger(LoggingInterceptor.class);

	/**
	 * 请求执行开始时间
	 */
	public static final String START_TIME = "_start_time";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		LOG.info("current visiting uri: {}", request.getRequestURL());

		long startTime = System.currentTimeMillis();
		request.setAttribute(START_TIME, startTime);

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		String path = request.getServletPath();

		long startTime = (Long) request.getAttribute(START_TIME);
		long executeTime = System.currentTimeMillis() - startTime;

		LOG.info("current visiting uri: {}, use total time(ms): {}", path, executeTime);
	}

}
