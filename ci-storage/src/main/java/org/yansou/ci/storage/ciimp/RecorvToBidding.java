package org.yansou.ci.storage.ciimp;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.db.model.project.BiddingData;
import org.yansou.ci.core.db.model.project.SnapshotInfo;
import org.yansou.ci.data.mining.utils.Readability;
import org.yansou.ci.storage.service.project.BiddingDataService;

import java.util.List;
import java.util.UUID;

@Component
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
                jsonObjectStoreMysqlDao.syncStore(bidding);

//                if (StringUtils.isBlank(data.getSnapshotId())) {
                SnapshotInfo snap = new SnapshotInfo();
                Readability read = new Readability(data.getHtmlSource());
                read.init();
                snap.setContext(read.html());
                snap.setDataType(1);
                snap.setSnapshotId(UUID.randomUUID().toString());
                data.setSnapshotId(snap.getSnapshotId());
                biddingDataService.saveDataAndSnapshotInfo(data, snap);
                LOG.info("create snapshot:" + data.getUrl());
//                }
            }
        } catch (DaoException e) {
            LOG.info(e);
        }
    }

}
