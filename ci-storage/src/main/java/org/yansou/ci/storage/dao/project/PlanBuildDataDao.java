package org.yansou.ci.storage.dao.project;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.yansou.ci.core.model.project.PlanBuildData;
import org.yansou.ci.storage.common.dao.GeneralDao;

@Repository("planBuildDataDao")
public interface PlanBuildDataDao extends GeneralDao<PlanBuildData, Long> {

	@Modifying
	@Query("update PlanBuildData bean set bean.status = ?1 where bean.id = ?2")
	int updateStatus(Integer status, Long id);

}
