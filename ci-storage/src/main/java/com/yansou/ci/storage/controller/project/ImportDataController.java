package com.yansou.ci.storage.controller.project;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.ActuatorMetricWriter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.yansou.ci.core.rest.response.SimpleRestResponse;
import com.yansou.ci.storage.ciimp.CorvToBidding;
import com.yansou.ci.storage.ciimp.CorvToPlanBuild;

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
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private CorvToBidding corvBidding;
	@Autowired
	private CorvToPlanBuild corvPlanBuild;

	@ApiOperation("导入招标中标数据")
	@GetMapping("/bidding")
	public SimpleRestResponse bidding() {
		corvBidding.run();
		LOG.info("imp bidding 1000 done .");
		return SimpleRestResponse.ok();
	}

	@ApiOperation("导入拟在建数据")
	@GetMapping("/planbuild")
	public SimpleRestResponse planBuild() {
		corvPlanBuild.run();
		LOG.info("imp plan build 200 done.");
		return SimpleRestResponse.ok();
	}
}
