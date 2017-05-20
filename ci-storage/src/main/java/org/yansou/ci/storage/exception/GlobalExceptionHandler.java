package org.yansou.ci.storage.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yansou.ci.core.rest.response.SimpleRestResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理
 *
 * @author liutiejun
 * @create 2017-04-12 16:42
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger LOG = LogManager.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public SimpleRestResponse jsonErrorHandler(HttpServletRequest request, Exception e) {
		LOG.error("Request: " + request.getRequestURL() + " raised " + e.getMessage(), e);

		return SimpleRestResponse.exception(e);
	}

}
