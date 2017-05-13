package com.yansou.ci.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author liutiejun
 * @create 2017-04-19 10:15
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {

	private static final Logger LOG = LogManager.getLogger(LoginController.class);

	@GetMapping("/show")
	public String show() {
		LOG.info("show login page-------------------");
		return "views/login/show";
	}

	@GetMapping("/success")
	public String success() {
		LOG.info("Login Success");

		return "redirect:/welcome/index";
	}

	@GetMapping("/failure")
	public String failure() {
		LOG.info("show login failure page-------------------");
		return "views/login/failure";
	}

}
