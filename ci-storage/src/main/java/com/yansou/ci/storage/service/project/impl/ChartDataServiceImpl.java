package com.yansou.ci.storage.service.project.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.domain.Specification;

import com.yansou.ci.common.exception.DaoException;
import com.yansou.ci.common.page.PageCriteria;
import com.yansou.ci.core.model.chart.ChartData;
import com.yansou.ci.storage.common.dao.GeneralDao;
import com.yansou.ci.storage.common.service.GeneralServiceImpl;
import com.yansou.ci.storage.dao.project.ChartDataDao;
import com.yansou.ci.storage.service.project.ChartDataService;

/**
 * @author zhangyingying
 */
public class ChartDataServiceImpl extends GeneralServiceImpl<ChartData, Long> implements ChartDataService{
	
	private ChartDataDao chartDataDao;
	
	@Autowired
	@Qualifier("chartDataDao")
	@Override
	public void setGeneralDao(GeneralDao<ChartData,Long> generalDao){
		this.generalDao=generalDao;
		this.chartDataDao=(ChartDataDao) generalDao;
	}
	
	@Override
	public List<ChartData> findAll(Date ctBeginTime,Date ctEndTime) throws DaoException{
		return chartDataDao.findAll(ctBeginTime, ctEndTime);
	}

	@Override
	public int updateStatus(Integer status, Long id) throws DaoException {
		return 0;
	}

	@Override
	public Specification<ChartData> createSpecification(PageCriteria pageCriteria) {
		return null;
	}

}
