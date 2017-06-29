package com.yansou.ci.web.business.project.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.yansou.ci.common.datatables.DataTableVo;
import com.yansou.ci.core.model.chart.ChartData;
import com.yansou.ci.core.rest.request.RestRequest;
import com.yansou.ci.core.rest.response.CountResponse;
import com.yansou.ci.core.rest.response.IdResponse;
import com.yansou.ci.core.rest.response.chart.ChartDataArrayResponse;
import com.yansou.ci.web.business.project.ChartDataBusiness;

/**
 * 
 * @author zhangyingying
 *
 */
@Component("ChartDataBusiness")
public class ChartDataBusinessImpl implements ChartDataBusiness{
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public ChartData[] findAll(Date ctBeginTime,Date ctEndTime){
		String requestUrl="http://"+ CI_STORAGE+"/chartData/find";
		
		RestRequest restRequest=new RestRequest();
		restRequest.setCtBeginTime(ctBeginTime);
		restRequest.setCtEndTime(ctEndTime);
		
		HttpEntity<RestRequest> httpEntity=new HttpEntity<RestRequest>(restRequest);
		
		ChartDataArrayResponse restResponse=restTemplate.postForObject(requestUrl, httpEntity, ChartDataArrayResponse.class);
		
		ChartData[] chartDatas=restResponse.getResult();
		if(chartDatas==null){
			chartDatas=new ChartData[0];
		}
		return chartDatas;
	}

	@Override
	public ChartData findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChartData[] findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataTableVo<ChartData> pagination(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResponse save(ChartData entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResponse update(ChartData entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CountResponse deleteById(Long[] ids) {
		// TODO Auto-generated method stub
		return null;
	}


}
