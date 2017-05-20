package org.yansou.ci.storage.dao.project;

import org.springframework.stereotype.Repository;
import org.yansou.ci.core.model.project.PlanBuildData;
import org.yansou.ci.storage.common.dao.GeneralDao;
@Repository
public interface PlanBuildDataDao extends GeneralDao<PlanBuildData, Long> {

}
