package com.yansou.ci.storage.dao.project;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yansou.ci.core.model.project.BiddingSnapshot;
import com.yansou.ci.storage.common.dao.GeneralDao;

@Repository("biddingSnapshotDao")
public interface BiddingSnapshotDao extends GeneralDao<BiddingSnapshot, Long> {
	@Query("select u from BiddingSnapshot u where u.snapshotId = :snapshotId")
	BiddingSnapshot getSnapshot(String snapshotId);
}
