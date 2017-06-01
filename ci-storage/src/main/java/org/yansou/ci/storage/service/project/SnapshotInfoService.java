package org.yansou.ci.storage.service.project;

import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.model.project.SnapshotInfo;
import org.yansou.ci.storage.common.service.GeneralService;

/**
 * @author liutiejun
 * @create 2017-05-14 0:21
 */
public interface SnapshotInfoService extends GeneralService<SnapshotInfo, Long> {

	SnapshotInfo findBySnapshotId(String snapshotId) throws DaoException;

}
