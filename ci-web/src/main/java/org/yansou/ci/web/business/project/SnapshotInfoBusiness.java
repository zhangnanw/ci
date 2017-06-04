package org.yansou.ci.web.business.project;

import org.yansou.ci.core.model.project.SnapshotInfo;
import org.yansou.ci.web.business.GeneralBusiness;

/**
 * @author liutiejun
 * @create 2017-05-31 21:47
 */
public interface SnapshotInfoBusiness extends GeneralBusiness<SnapshotInfo, Long> {

	SnapshotInfo findBySnapshotId(String snapshotId);

}
