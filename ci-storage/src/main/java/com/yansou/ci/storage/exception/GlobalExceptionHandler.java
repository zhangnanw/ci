package com.yansou.ci.storage.exception;

import com.yansou.ci.common.exception.DaoException;
import com.yansou.ci.common.model.error.ErrorInfo;
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
		errorInfo.setCode(500);
		errorInfo.setMessage(e.getMessage());
		errorInfo.setUrl(request.getRequestURL().toString());
		errorInfo.setData("Some Data");

		return errorInfo;

	}

}
