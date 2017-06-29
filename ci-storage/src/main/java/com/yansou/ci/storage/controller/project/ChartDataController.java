package com.yansou.ci.storage.controller.project;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yansou.ci.common.exception.DaoException;
import com.yansou.ci.core.model.chart.ChartData;
import com.yansou.ci.core.rest.request.RestRequest;
import com.yansou.ci.core.rest.response.SimpleRestResponse;
import com.yansou.ci.storage.service.project.ChartDataService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author zhangyingying
 */
@RestController
@RequestMapping(value="/ChartData")
public class ChartDataController {
	
	@Autowired
	private ChartDataService chartDataService;
	
	@ApiOperation(value="获取数据信息")
	@PostMapping(value="/find")
	public SimpleRestResponse find(@RequestBody RestRequest restRequest) throws DaoException{
		if(restRequest==null){
			return SimpleRestResponse.exception("请求参数为空");
		}
		//获取开始时间和结束时间
		Date ctBeginTime=restRequest.getCtBeginTime();
		Date ctEndTime=restRequest.getCtEndTime();
		List<ChartData> chartDataList=chartDataService.findAll(ctBeginTime, ctEndTime);
		return SimpleRestResponse.ok(chartDataList);
	}
	
}
