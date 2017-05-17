package com.yansou.ci.storage.controller.project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/snapshot")
public class SnapshotController {
	@ApiOperation("招中标快照")
	@GetMapping("/bidding")
	public @ResponseBody String bidding(String id) {
		return "这是一个快照";
	}
}
