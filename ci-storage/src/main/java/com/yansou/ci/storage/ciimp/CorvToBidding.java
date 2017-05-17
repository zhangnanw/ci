package com.yansou.ci.storage.ciimp;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yansou.common.util.JSONArrayHandler;
import org.yansou.common.util.JSONUtils;

import com.alibaba.fastjson.JSONArray;
import com.yansou.ci.storage.dao.project.BiddingDataDao;

/**
 * 
 * @author Administrator
 *
 */
@Component
public class CorvToBidding extends AbsStatistics {
	@Autowired
	private BiddingDataDao dao;

	public void run() {
		try {
			JSONArray arr = qr.query(
					"select * from tab_raw_bidd where url not in(SELECT url from intelligence.ci_bidding_data_info) limit 1000",
					JSONArrayHandler.create());
			JSONUtils.streamJSONObject(arr).map(RawBidd2CiBiddingData::new).map(info -> info.get()).map(dao::save)
					.forEach(System.out::println);
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
	}
}
