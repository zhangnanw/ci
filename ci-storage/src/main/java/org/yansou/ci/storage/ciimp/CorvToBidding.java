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
import org.yansou.ci.core.model.project.BiddingData;
import org.yansou.ci.core.model.project.SnapshotInfo;
import org.yansou.ci.data.mining.utils.Readability;
import org.yansou.ci.storage.service.project.BiddingDataService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author Administrator
 *
 */
@Component
public class CorvToBidding extends AbsStatistics {
	private static final Logger LOG = LogManager.getLogger(CorvToBidding.class);

	@Autowired
	private BiddingDataService biddingDataService;

	public void run() {
		try {
			TimeStat ts = new TimeStat();
			String sql = "select * from tab_raw_bidd where url not in(SELECT url from `intelligence-"
					+ TmpConfigRead.getCfgName() + "`.ci_bidding_data) limit 1000";
			System.out.println(sql);
			JSONArray arr = qr.query(sql, JSONArrayHandler.create());
			ts.buriePrint("bidding-query-time:{}", LOG::info);
			JSONUtils.streamJSONObject(arr).forEach(this::store);
			ts.buriePrint("bidding-read-time:{}", LOG::info);
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	void store(JSONObject obj) {
		RawBidd2CiBiddingData rd = new RawBidd2CiBiddingData(obj, null);
		SnapshotInfo snapsshot = new SnapshotInfo();
		snapsshot.setDataType(1);
		snapsshot.setSnapshotId(UUID.randomUUID().toString());
		snapsshot.setContext(new Readability(obj.getString("context")).init().outerHtml());
		BiddingData data = rd.get();
		if (LTFilter.isSave(data, snapsshot)) {
			data.setSnapshotId(snapsshot.getSnapshotId());
			data.setUrl(obj.getString("url"));
			try {
				biddingDataService.saveDataAndSnapshotInfo(data, snapsshot);
				LOG.info("insert bidding");
			} catch (DaoException e) {
				LOG.error(e);
			}
		}

	}

}
