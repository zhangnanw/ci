package org.yansou.ci.storage.service.project;

import org.yansou.ci.core.model.project.SnapshotInfo;
import org.yansou.ci.storage.common.service.GeneralService;

public interface SnapshotService extends GeneralService<SnapshotInfo, Long> {
	SnapshotInfo getSnapshot(String snapshotId);
}
