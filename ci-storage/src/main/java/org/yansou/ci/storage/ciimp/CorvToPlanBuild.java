package org.yansou.ci.storage.ciimp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yansou.ci.common.time.TimeStat;
import org.yansou.ci.common.utils.JSONArrayHandler;
import org.yansou.ci.common.utils.JSONUtils;
import org.yansou.ci.common.utils.PojoUtils;
import org.yansou.ci.core.db.model.project.PlanBuildData;
import org.yansou.ci.core.db.model.project.SnapshotInfo;
import org.yansou.ci.data.mining.utils.Readability;
import org.yansou.ci.storage.service.project.PlanBuildDataService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

/**
 * 
 * @author Administrator
 *
 */
@Component
public class CorvToPlanBuild extends AbsStatistics {
	private static Logger LOG = LogManager.getLogger(CorvToPlanBuild.class);

	@Autowired
	private PlanBuildDataService planBuildDataService;

	Stream<JSONObject> filter(Stream<JSONObject> strema) {
		Map<Object, List<JSONObject>> bg = strema
				.collect(Collectors.groupingBy(o -> ((JSONObject) o).getString("project_number")));
		Builder<JSONObject> build = Stream.builder();
		for (List<JSONObject> list : bg.values()) {
			build.add(__mergeGG(list));
		}
		return build.build();
	}

	/**
	 * 合并公告。
	 * 
	 * @param objs
	 * @return
	 */
	private JSONObject __mergeGG(List<JSONObject> objs) {
		List<JSONObject> list = objs.stream().sorted((a, b) -> b.getString("rowkey").compareTo(a.getString("rowkey")))
				.collect(Collectors.toList());
		JSONObject obj = list.get(0);
		JSONObject lastObj = new JSONObject();
		if (objs.size() > 1) {
			lastObj = list.get(1);
		}
		obj.put("prev", lastObj);
		return obj;
	}

	public void run() {
		try {
			TimeStat ts = new TimeStat();
			String sql = "select * from tab_rcc_project where project_name like '%光伏%' and rowkey not in(SELECT rowkey from `"
					+ TmpConfigRead.getCfgName() + "`.ci_plan_build_data where rowkey is not null)";
			JSONArray arr = qr.query(sql, JSONArrayHandler.create());
			ts.buriePrint("plan-build-query-time:{}", LOG::info);
			filter(JSONUtils.streamJSONObject(arr)).forEachOrdered(this::store);

		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * 保存逻辑
	 * 
	 * @param project
	 */
	public void store(JSONObject project) {
		// 获得网页源码
		JSONObject source = getSourceObj(project.getString("rowkey"));
		// 生成快照对象
		SnapshotInfo snapshot = new SnapshotInfo();
		snapshot.setDataType(2);
		snapshot.setContext(new Readability(source.getString("page_source")).init().outerHtml());
		snapshot.setSnapshotId(UUID.randomUUID().toString());
		try {
			// 解析数据，生成拟在建信息。
			PlanBuildData data = new RccSource2PlanBuildDataInfo(source, project).get();
			// 判断是否可以保存
			if (!LTFilter.isSave(data, snapshot)) {
				LOG.info("no save.");
				return;
			}
			// 继续赋值快照ID和URL。
			data.setSnapshotId(snapshot.getSnapshotId());
			data.setUrl(source.getString("url"));
			// 清理一下对象
			PojoUtils.trimAllStringField(data);
			// 根据对象唯一标识查找库中数据
			PlanBuildData rs = planBuildDataService.findByProjectIdentifie(data.getProjectIdentifie());
			// 如果不存在，插入新数据
			if (rs == null) {
				System.out.println("insert plan build.");
				planBuildDataService.saveDataAndSnapshotInfo(data, snapshot);
			} else {
				// 否则只更新状态即可
				planBuildDataService.updateStatusUpdate(data.getStatusUpdate(), data.getId());
				LOG.info("updateStatusUpdate");
			}
		} catch (Exception e) {
			LOG.error(e);
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
