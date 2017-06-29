package com.yansou.ci.storage.service.project;

import java.util.Date;
import java.util.List;

import com.yansou.ci.common.exception.DaoException;
import com.yansou.ci.core.model.chart.ChartData;
import com.yansou.ci.storage.common.service.GeneralService;

/**
 * @author zhangyingying
 */
public interface ChartDataService extends GeneralService<ChartData, Long>{
	
	List<ChartData> findAll(Date ctBeginTime,Date ctEndTime) throws DaoException;

}
