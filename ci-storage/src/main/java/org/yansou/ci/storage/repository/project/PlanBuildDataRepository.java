package org.yansou.ci.storage.repository.project;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.yansou.ci.core.db.model.project.PlanBuildData;
import org.yansou.ci.storage.common.repository.GeneralRepository;

@Repository("planBuildDataRepository")
public interface PlanBuildDataRepository extends GeneralRepository<PlanBuildData, Long> {

	PlanBuildData findByProjectIdentifie(String projectIdentifie);

	@Modifying
	@Query("update PlanBuildData bean set bean.statusUpdate = ?1 where bean.id = ?2")
	int updateStatusUpdate(String statusUpdate, Long id);
}
