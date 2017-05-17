package com.yansou.ci.storage.controller.project;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yansou.ci.core.rest.response.SimpleRestResponse;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author n.zhang
 *
 */
@RestController
@RequestMapping(value = "/importdata")
public class ImportDataController {
	private static final Logger LOG = LogManager.getLogger(ImportDataController.class);

	@ApiOperation("导入招标中标数据")
	@GetMapping("/bidding")
	public SimpleRestResponse bidding() {
		return SimpleRestResponse.ok();
	}
	@ApiOperation("导入拟在建数据")
	@GetMapping("/planbuild")
	public SimpleRestResponse planBuild(){
		return SimpleRestResponse.ok();
	}
}
