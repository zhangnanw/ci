package org.yansou.ci.storage.ciimp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.model.project.BiddingData;
import org.yansou.ci.core.model.project.BiddingSnapshot;
import org.yansou.ci.storage.dao.project.BiddingDataDao;
import org.yansou.ci.storage.service.project.BiddingSnapshotService;
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
public class CorvToBidding extends AbsStatistics {
	@Autowired
	private BiddingDataDao dao;

	@Autowired
	private BiddingSnapshotService biddingSnapshotService;

	public void run() {
		try {
			JSONArray arr = qr.query(
					"select * from tab_raw_bidd where url not in(SELECT url from intelligence.ci_bidding_data) limit 1000",
					JSONArrayHandler.create());
			JSONUtils.streamJSONObject(arr).map(this::toBiddingData).map(dao::save).forEach(System.out::println);
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	BiddingData toBiddingData(JSONObject obj) {
		RawBidd2CiBiddingData rd = new RawBidd2CiBiddingData(obj);
		BiddingSnapshot ent = new BiddingSnapshot();
		ent.setSnapshotId(UUID.randomUUID().toString());
		ent.setContext(obj.getString("context"));
		try {
			ent = biddingSnapshotService.save(ent);
			BiddingData res = rd.get();
			res.setSnapshotId(ent.getSnapshotId());
			res.setUrl("/snapshot/bidding/" + ent.getSnapshotId());
			return res;
		} catch (DaoException e) {
			throw new IllegalStateException(e);
		}

	}

}