package org.yansou.ci.storage.repository.project;

import org.springframework.stereotype.Repository;
import org.yansou.ci.core.db.model.project.SnapshotInfo;
import org.yansou.ci.storage.common.repository.GeneralRepository;

/**
 * @author liutiejun
 * @create 2017-05-14 0:15
 */
@Repository("snapshotInfoRepository")
public interface SnapshotInfoRepository extends GeneralRepository<SnapshotInfo, Long> {

	SnapshotInfo findSnapshotInfoBySnapshotId(String snapshotId);

}
