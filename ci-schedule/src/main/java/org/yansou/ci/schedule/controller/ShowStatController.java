package org.yansou.ci.schedule.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/status")
public class ShowStatController {
	@GetMapping("/show")
	public @ResponseBody String show() {
		return "{'status':'survival'}";
	}
}
