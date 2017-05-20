package com.yansou.ci.storage.dao.project;

import org.springframework.stereotype.Repository;

import com.yansou.ci.core.model.project.PlanBuildData;
import com.yansou.ci.storage.common.dao.GeneralDao;
@Repository
public interface PlanBuildDataDao extends GeneralDao<PlanBuildData, Long> {

}
