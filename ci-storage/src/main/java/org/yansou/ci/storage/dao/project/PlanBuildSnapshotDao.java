package org.yansou.ci.storage.dao.project;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.yansou.ci.core.model.project.PlanBuildSnapshot;
import org.yansou.ci.storage.common.dao.GeneralDao;

@Repository("planBuildSnapshotDao")
public interface PlanBuildSnapshotDao extends GeneralDao<PlanBuildSnapshot, Long> {

	@Query("select ps from PlanBuildSnapshot ps where ps.snapshotId = :snapshotId")
	PlanBuildSnapshot getSnapshot(@Param("snapshotId") String snapshotId);

}
