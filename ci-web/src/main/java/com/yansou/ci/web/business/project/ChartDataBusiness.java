package com.yansou.ci.web.business.project;

import java.util.Date;

import com.yansou.ci.core.model.chart.ChartData;
import com.yansou.ci.web.business.GeneralBusiness;

/**
 * 
 * @author zhangyingying
 *
 */
public interface ChartDataBusiness extends GeneralBusiness<ChartData, Long>{
	
	ChartData[] findAll(Date ctBeginTime,Date ctEndTime);

}
