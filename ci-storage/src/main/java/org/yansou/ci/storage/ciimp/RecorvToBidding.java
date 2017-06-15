package org.yansou.ci.storage.ciimp;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.db.model.project.BiddingData;
import org.yansou.ci.storage.service.project.BiddingDataService;

import com.alibaba.fastjson.JSONObject;

public class RecorvToBidding extends AbsStatistics implements Runnable {

	private static final Logger LOG = LogManager.getLogger(RecorvToBidding.class);

	JSONObjectStoreMySQLDao jsonObjectStoreMysqlDao;

	public RecorvToBidding() {
		super();
		jsonObjectStoreMysqlDao = new JSONObjectStoreMySQLDao(qr.getDataSource(), "tab_raw_bidd", "url");
	}

	@Autowired
	BiddingDataService biddingDataService;

	@Override
	public void run() {

		try {
			List<BiddingData> list = biddingDataService.findByHtmlSourceNotNull();
			for (BiddingData data : list) {
				JSONObject bidding = new JSONObject();
				bidding.put("url", data.getUrl());
				bidding.put("context", data.getHtmlSource());
			}
		} catch (DaoException e) {

		}

	}

}
