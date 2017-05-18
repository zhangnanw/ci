package com.yansou.ci.storage.ciimp;

import java.sql.SQLException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.yansou.common.util.JSONArrayHandler;
import org.yansou.common.util.JSONUtils;

import com.alibaba.fastjson.JSONArray;
import com.yansou.ci.core.rest.response.RestResponse;
import com.yansou.ci.storage.dao.project.BiddingDataDao;
import com.yansou.ci.storage.service.project.BiddingSnapshotService;

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
			JSONUtils.streamJSONObject(arr).map(RawBidd2CiBiddingData::new).map(info -> info.get()).map(info -> {
				info.setSnapshotId(UUID.randomUUID().toString());
				return info;
			}).map(dao::save).forEach(System.out::println);
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}
}
