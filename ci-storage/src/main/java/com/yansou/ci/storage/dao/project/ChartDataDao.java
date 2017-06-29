package com.yansou.ci.storage.dao.project;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yansou.ci.core.model.chart.ChartData;
import com.yansou.ci.storage.common.dao.GeneralDao;

/**
 * 
 * @author zhangyingying
 */
@Repository("ChartDataDao")
public interface ChartDataDao extends GeneralDao<ChartData, Long>{
	
	@Query("select c from orm_chart c where c.ct_time between c.ct_time=?1 and c.ct_time=?2")
	List<ChartData> findAll(Date ctBeginTime,Date ctEndTime);

}
