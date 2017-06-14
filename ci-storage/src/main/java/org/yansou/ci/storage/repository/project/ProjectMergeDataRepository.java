package org.yansou.ci.storage.repository.project;

import org.springframework.stereotype.Repository;
import org.yansou.ci.core.db.model.project.ProjectMergeData;
import org.yansou.ci.storage.common.repository.GeneralRepository;

@Repository("projectMergeDataRepository")
public interface ProjectMergeDataRepository extends GeneralRepository<ProjectMergeData, Long> {

}
