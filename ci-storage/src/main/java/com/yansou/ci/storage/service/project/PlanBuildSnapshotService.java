package com.yansou.ci.storage.service.project;

import org.springframework.data.jpa.repository.Query;

import com.yansou.ci.core.model.project.PlanBuildSnapshot;
import com.yansou.ci.storage.common.service.GeneralService;

public interface PlanBuildSnapshotService extends GeneralService<PlanBuildSnapshot, Long> {

	PlanBuildSnapshot getSnapshot(String snapshotId);
}
