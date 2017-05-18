package com.yansou.ci.storage.service.project;

import com.yansou.ci.core.model.project.BiddingSnapshot;
import com.yansou.ci.storage.common.service.GeneralService;

public interface BiddingSnapshotService extends GeneralService<BiddingSnapshot, Long> {
	BiddingSnapshot getSnapshot(String snapshotId);
}
