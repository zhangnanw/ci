package org.yansou.ci.storage.merge;

import org.springframework.beans.factory.annotation.Autowired;
import org.yansou.ci.storage.ciimp.Dlzb2PlanBuildDataInfo;
import org.yansou.ci.storage.service.project.BiddingDataService;
import org.yansou.ci.storage.service.project.PlanBuildDataService;

/**
 * Created by Administrator on 2017/6/12.
 */
public class ProjectMergeProcess implements Runnable {
    @Autowired
    PlanBuildDataService planBuildDataService;
    @Autowired
    BiddingDataService biddingDataService;


    @Override
    public void run() {

    }
}
