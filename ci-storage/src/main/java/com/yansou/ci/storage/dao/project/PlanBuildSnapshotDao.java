package com.yansou.ci.storage.dao.project;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yansou.ci.core.model.project.PlanBuildSnapshot;
import com.yansou.ci.storage.common.dao.GeneralDao;

@Repository("planBuildSnapshotDao")
public interface PlanBuildSnapshotDao extends GeneralDao<PlanBuildSnapshot, Long> {
	
	
	@Query("select u from PlanBuildSnapshot u where u.snapshotId = :snapshotId")
	PlanBuildSnapshot getSnapshot(String snapshotId);
}
