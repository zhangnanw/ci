package com.yansou.ci.storage.dao.project;

import org.springframework.stereotype.Repository;

import com.yansou.ci.core.model.project.BiddingSnapshot;
import com.yansou.ci.storage.common.dao.GeneralDao;

@Repository("biddingSnapshotDao")
public interface BiddingSnapshotDao extends GeneralDao<BiddingSnapshot, String> {

}
