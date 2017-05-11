package com.yansou.ci.storage.exception;

import com.yansou.ci.common.exception.DaoException;
import com.yansou.ci.common.model.error.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理
 *
 * @author liutiejun
 * @create 2017-04-12 16:42
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("exception", e);
		modelAndView.addObject("url", request.getRequestURL());
		modelAndView.setViewName("error/5xx");

		return modelAndView;
	}

	@ExceptionHandler(value = DaoException.class)
	@ResponseBody
	public ErrorInfo<String> jsonErrorHandler(HttpServletRequest request, DaoException e) {
		ErrorInfo<String> errorInfo = new ErrorInfo<>();

		errorInfo.setTimestamp(System.currentTimeMillis());
		errorInfo.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorInfo.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		errorInfo.setException(e.getClass().getSimpleName());
		errorInfo.setMessage(e.getMessage());
		errorInfo.setPath(request.getRequestURL().toString());
		errorInfo.setData("Some Data");

		return errorInfo;
	}

}
