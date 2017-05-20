package org.yansou.ci.storage.dao.project;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.yansou.ci.core.model.project.BiddingSnapshot;
import org.yansou.ci.storage.common.dao.GeneralDao;

@Repository("biddingSnapshotDao")
public interface BiddingSnapshotDao extends GeneralDao<BiddingSnapshot, Long> {
	@Query("select bs from BiddingSnapshot bs where bs.snapshotId = :snapshotId")
	BiddingSnapshot getSnapshot(@Param("snapshotId") String snapshotId);
}
