package com.yansou.ci.storage.ciimp;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yansou.common.util.JSONArrayHandler;
import org.yansou.common.util.JSONUtils;

import com.alibaba.fastjson.JSONArray;
import com.yansou.ci.storage.dao.project.PlanBuildDataDao;

/**
 * 
 * @author Administrator
 *
 */
@Component
public class CorvToPlanBuild extends AbsStatistics {
	@Autowired
	private PlanBuildDataDao dao;

	public void run() {
		try {
			JSONArray arr = qr.query(
					"select * from tab_rcc_source where rowkey not in(SELECT rowkey from intelligence.ci_plan_build_data) limit 200",
					JSONArrayHandler.create());
			JSONUtils.streamJSONObject(arr).map(RccSource2PlanBuildDataInfo::new).map(info -> info.get()).map(dao::save)
					.forEach(System.out::println);
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}
}
