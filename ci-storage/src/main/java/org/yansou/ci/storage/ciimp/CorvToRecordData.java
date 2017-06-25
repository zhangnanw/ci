package org.yansou.ci.storage.ciimp;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yansou.ci.common.utils.JSONArrayHandler;
import org.yansou.ci.core.db.model.project.RecordData;
import org.yansou.ci.core.db.model.project.SnapshotInfo;
import org.yansou.ci.storage.service.project.RecordDataService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Component
public class CorvToRecordData extends AbsStatistics {
	@Autowired
	private RecordDataService recordDataService;

	public void run() {
		try {
			JSONArray arr = qr.query("SELECT * FROM tab_put_on_record WHERE ", JSONArrayHandler.create());
			for (int i = 0; i < arr.size(); i++) {
				JSONObject obj = arr.getJSONObject(i);
				RecordData rd = new PutIn2RecordData(obj).get();
				SnapshotInfo snap = new SnapshotInfo();
			}
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	private static class PutIn2RecordData {
		private JSONObject srcObj;

		public PutIn2RecordData(JSONObject srcObj) {

		}

		public RecordData get() {
			RecordData rd = new RecordData();
			return rd;
		}
	}

}
