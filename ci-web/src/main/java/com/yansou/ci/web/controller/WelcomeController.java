package com.yansou.ci.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author liutiejun
 * @create 2017-04-14 11:09
 */
@Controller
@RequestMapping(value = "/welcome")
public class WelcomeController {

	@GetMapping("/index")
	public String index() {
		return "views/index";
	}

}
