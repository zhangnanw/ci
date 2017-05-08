package com.yansou.ci.web.controller;

import com.yansou.ci.common.exception.DaoException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author liutiejun
 * @create 2017-04-11 10:30
 */
@Controller
@RequestMapping(value = "/hello")
public class HelloController {

	@GetMapping(value = "/index")
	public String index() {
		return "index";
	}

	@GetMapping(value = "/exception")
	public String exception() throws Exception {
		throw new Exception("发生错误......");
	}

	@GetMapping(value = "/json/exception")
	public String jsonException() throws DaoException {
		throw new DaoException("发生错误 DaoException......");
	}



}
