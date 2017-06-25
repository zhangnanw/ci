package org.yansou.ci.storage.ciimp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yansou.ci.common.utils.JSONArrayHandler;
import org.yansou.ci.common.utils.JSONUtils;
import org.yansou.ci.core.db.model.project.RecordData;
import org.yansou.ci.core.db.model.project.SnapshotInfo;
import org.yansou.ci.data.mining.analyzer.impl.AreaAnalyzer;
import org.yansou.ci.storage.service.project.RecordDataService;

import java.util.UUID;

@Component
public class CorvToRecordData extends AbsStatistics {
    @Autowired
    private RecordDataService recordDataService;

    public void run() {
        try {
            JSONArray arr = qr.query("SELECT * FROM tab_put_on_record WHERE url not in(SELECT url FROM `" + TmpConfigRead.getCfgName() + "`.ci_record_data)", JSONArrayHandler.create());
            for (int i = 0; i < arr.size(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                RecordData rd = new PutIn2RecordData(obj).get();
                SnapshotInfo snap = new SnapshotInfo();
                String snapshotId = UUID.randomUUID().toString();
                snap.setSnapshotId(snapshotId);
                snap.setDataType(3);
                rd.setSnapshotId(snapshotId);
                recordDataService.saveDataAndSnapshotInfo(rd, snap);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static class PutIn2RecordData {
        private JSONObject srcObj;

        public PutIn2RecordData(JSONObject srcObj) {
            this.srcObj = srcObj;
        }

        public RecordData get() {
            RecordData rd = new RecordData();
            rd.setUrl(srcObj.getString("url"));
            rd.setProjectName(srcObj.getString("title"));
            if (StringUtils.isBlank(rd.getProjectName())) {
                rd.setProjectName(srcObj.getString("anchor_text"));
            }
            rd.setProjectProvince(toProvince());
            rd.setProjectCity(toCity());
            rd.setProjectDistrict(toDistrict());
            return rd;
        }

        /**
         * 地区解析器
         */
        private static AreaAnalyzer AREA_ANALYZER = AreaAnalyzer.getExamples();

        private String toProvince() {
            JSONObject res = AREA_ANALYZER.analy(srcObj);
            JSONArray arr = res.getJSONArray("area");
            if (JSONUtils.isEmpty(arr)) {
                return null;
            }
            return arr.getString(0);
        }

        private String toCity() {
            JSONObject res = AREA_ANALYZER.analy(srcObj);
            JSONArray arr = res.getJSONArray("area");
            if (JSONUtils.isEmpty(arr) || arr.size() <= 1) {
                return null;
            }
            return arr.getString(1);
        }

        private String toDistrict() {
            JSONObject res = AREA_ANALYZER.analy(srcObj);
            JSONArray arr = res.getJSONArray("area");
            if (JSONUtils.isEmpty(arr) || arr.size() <= 2) {
                return null;
            }
            return arr.getString(2);
        }
    }

}
