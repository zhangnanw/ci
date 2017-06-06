package org.yansou.ci.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author liutiejun
 * @create 2017-04-14 11:09
 */
@Controller
public class WelcomeController {

	@GetMapping("/welcome")
	public String welcome() {
		return "views/index";
	}

	@GetMapping("/")
	public String index() {
		return "redirect:/welcome";
	}

}
