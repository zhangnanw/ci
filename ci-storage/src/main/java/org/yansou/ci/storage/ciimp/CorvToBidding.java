package org.yansou.ci.storage.ciimp;

import java.sql.SQLException;
import java.util.Objects;
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
import org.yansou.ci.core.model.project.BiddingSnapshot;
import org.yansou.ci.storage.dao.project.BiddingDataDao;
import org.yansou.ci.storage.service.project.BiddingSnapshotService;

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
	private BiddingDataDao dao;
	@Autowired
	private BiddingSnapshotService biddingSnapshotService;

	public void run() {
		try {
			TimeStat ts = new TimeStat();
			String sql = "select * from tab_raw_bidd where url not in(SELECT url from `intelligence-"
					+ TmpConfigRead.getCfgName() + "`.ci_bidding_data) limit 1000";
			System.out.println(sql);
			JSONArray arr = qr.query(sql, JSONArrayHandler.create());
			ts.buriePrint("bidding-query-time:{}", LOG::info);
			JSONUtils.streamJSONObject(arr).map(this::toBiddingData).filter(Objects::nonNull).map(dao::save)
					.forEach(System.out::println);
			ts.buriePrint("bidding-read-time:{}", LOG::info);
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	BiddingData toBiddingData(JSONObject obj) {
		RawBidd2CiBiddingData rd = new RawBidd2CiBiddingData(obj, null);
		BiddingSnapshot ent = new BiddingSnapshot();
		ent.setSnapshotId(UUID.randomUUID().toString());
		ent.setContext(obj.getString("context"));
		try {

			BiddingData res = rd.get();
			if (LTFilter.isSave(res, ent)) {
				ent = biddingSnapshotService.save(ent);
			} else {
				return null;
			}
			res.setSnapshotId(ent.getSnapshotId());
			res.setUrl("/snapshot/bidding/" + ent.getSnapshotId());
			return res;
		} catch (DaoException e) {
			throw new IllegalStateException(e);
		}

	}

}
