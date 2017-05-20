package org.yansou.ci.storage.ciimp;

import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.model.project.PlanBuildData;
import org.yansou.ci.core.model.project.PlanBuildSnapshot;
import org.yansou.ci.storage.dao.project.PlanBuildDataDao;
import org.yansou.ci.storage.service.project.impl.PlanBuildSnapshotServiceImpl;
import org.yansou.common.utils.JSONArrayHandler;
import org.yansou.common.utils.JSONUtils;

import java.sql.SQLException;
import java.util.UUID;

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
				ent.setSnapshotId(UUID.randomUUID().toString());
				try {
					ent = service.save(ent);
					PlanBuildData df = new RccSource2PlanBuildDataInfo(info).get();
					df.setSnapshotId(ent.getSnapshotId());
					df.setUrl("/snapshot/planbuild/" + ent.getSnapshotId());
					return df;
				} catch (DaoException e) {
					throw new IllegalStateException(e);
				}
			}).map(dao::save).forEach(System.out::println);
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}
}
