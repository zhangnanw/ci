package com.yansou.ci.storage.ciimp;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yansou.common.util.JSONArrayHandler;
import org.yansou.common.util.JSONUtils;

import com.alibaba.fastjson.JSONArray;
import com.yansou.ci.common.exception.DaoException;
import com.yansou.ci.core.model.project.PlanBuildSnapshot;
import com.yansou.ci.storage.dao.project.PlanBuildDataDao;
import com.yansou.ci.storage.service.project.impl.PlanBuildSnapshotServiceImpl;

/**
 * 
 * @author Administrator
 *
 */
@Component
public class CorvToPlanBuild extends AbsStatistics {
	@Autowired
	private PlanBuildDataDao dao;
	@Autowired
	private PlanBuildSnapshotServiceImpl service;

	public void run() {
		try {
			JSONArray arr = qr.query(
					"select * from tab_rcc_source where rowkey not in(SELECT rowkey from intelligence.ci_plan_build_data) limit 200",
					JSONArrayHandler.create());
			JSONUtils.streamJSONObject(arr).map(info -> {
				PlanBuildSnapshot ent = new PlanBuildSnapshot();
				ent.setContext(info.getString("page_source"));
				try {
					service.save(ent);
				} catch (DaoException e) {
					throw new IllegalStateException(e);
				}
				new RccSource2PlanBuildDataInfo(info).get();
				return info;
			}).map(RccSource2PlanBuildDataInfo::new).map(info -> info.get()).map(dao::save)
					.forEach(System.out::println);
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}
}
