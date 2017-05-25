package org.yansou.ci.storage.ciimp;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	Stream<JSONObject> filter(Stream<JSONObject> strema) {
		Map<Object, List<JSONObject>> bg = strema
				.collect(Collectors.groupingBy(o -> ((JSONObject) o).getString("project_number")));
		for (List<JSONObject> list : bg.values()) {
			list.sort((a, b) -> a.getString("rowkey").compareTo(b.getString("rowkey")));
			for (JSONObject obj : list) {
				System.out.println(obj.getString("rowkey"));
			}
		}
		System.out.println("------------");
		return strema;
	}

	public void run() {
		try {
			TimeStat ts = new TimeStat();
			String sql = "select * from tab_rcc_project where rowkey not in(SELECT rowkey from `intelligence-"
					+ TmpConfigRead.getCfgName() + "`.ci_plan_build_data)";
			JSONArray arr = qr.query(sql, JSONArrayHandler.create());
			ts.buriePrint("plan-build-query-time:{}", LOG::info);
			filter(JSONUtils.streamJSONObject(arr)).map(project -> {
				JSONObject source = getSourceObj(project.getString("rowkey"));
				PlanBuildSnapshot ent = new PlanBuildSnapshot();
				ent.setContext(source.getString("page_source"));
				ent.setSnapshotId(UUID.randomUUID().toString());
				try {
					ent = service.save(ent);
					PlanBuildData df = new RccSource2PlanBuildDataInfo(source, project).get();
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

	private JSONObject getSourceObj(String rowkey) {
		String sql = "select * from tab_rcc_source where rowkey=?";
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
