package com.yansou.ci.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

/**
 * @author liutiejun
 * @create 2017-04-14 11:09
 */
@Controller
public class WelcomeController {

	@GetMapping("/welcome")
	public String welcome(ModelMap modelMap) {
		modelMap.put("time", new Date());
		modelMap.put("message", "北京欢迎你");

		return "welcome";
	}

}
