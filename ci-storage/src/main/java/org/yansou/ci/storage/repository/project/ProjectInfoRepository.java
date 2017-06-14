package org.yansou.ci.storage.repository.project;

import org.springframework.stereotype.Repository;
import org.yansou.ci.core.db.model.project.ProjectInfo;
import org.yansou.ci.storage.common.repository.GeneralRepository;

@Repository("projectInfoRepository")
public interface ProjectInfoRepository extends GeneralRepository<ProjectInfo, Long> {

}
