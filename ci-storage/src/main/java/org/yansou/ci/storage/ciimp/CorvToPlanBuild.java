package org.yansou.ci.storage.ciimp;

import java.sql.SQLException;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.common.time.TimeStat;
import org.yansou.ci.common.utils.JSONArrayHandler;
import org.yansou.ci.common.utils.JSONUtils;
import org.yansou.ci.core.model.project.PlanBuildData;
import org.yansou.ci.core.model.project.PlanBuildSnapshot;
import org.yansou.ci.storage.dao.project.PlanBuildDataDao;
import org.yansou.ci.storage.service.project.impl.PlanBuildSnapshotServiceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author Administrator
 *
 */
@Component
public class CorvToPlanBuild extends AbsStatistics {
	private static Logger LOG = LogManager.getLogger(CorvToPlanBuild.class);
	@Autowired
	private PlanBuildDataDao dao;
	@Autowired
	private PlanBuildSnapshotServiceImpl service;

	public void run() {
		try {
			TimeStat ts = new TimeStat();
			String sql = "select * from tab_rcc_source where rowkey not in(SELECT rowkey from `intelligence-"
					+ TmpConfigRead.getCfgName() + "`.ci_plan_build_data) limit 10";
			JSONArray arr = qr.query(sql, JSONArrayHandler.create());
			ts.buriePrint("plan-build-query-time:{}", LOG::info);
			JSONUtils.streamJSONObject(arr).map(obj -> {
				PlanBuildSnapshot ent = new PlanBuildSnapshot();
				ent.setContext(obj.getString("page_source"));
				ent.setSnapshotId(UUID.randomUUID().toString());
				String rowkey = obj.getString("rowkey");
				try {
					ent = service.save(ent);
					PlanBuildData df = new RccSource2PlanBuildDataInfo(obj, getProjectObj(rowkey)).get();
					df.setSnapshotId(ent.getSnapshotId());
					df.setUrl("/snapshot/planbuild/" + ent.getSnapshotId());
					return df;
				} catch (DaoException e) {
					throw new IllegalStateException(e);
				}
			}).map(dao::save).forEach(System.out::println);
			ts.buriePrint("plan-build-read-time:{}", LOG::info);
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	private JSONObject getProjectObj(String rowkey) {
		String sql = "select * from tab_rcc_project where rowkey=?";
		try {
			JSONArray arr = qr.query(sql, JSONArrayHandler.create(), rowkey);
			if (arr.size() > 1) {
				throw new IllegalStateException("result num not unique.");
			}
			return arr.getJSONObject(0);
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}

	}
}
