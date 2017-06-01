package org.yansou.ci.storage.dao.project;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.yansou.ci.core.model.project.SnapshotInfo;
import org.yansou.ci.storage.common.dao.GeneralDao;

/**
 * @author liutiejun
 * @create 2017-05-14 0:15
 */
@Repository("snapshotInfoDao")
public interface SnapshotInfoDao extends GeneralDao<SnapshotInfo, Long> {

	@Modifying
	@Query("update SnapshotInfo bean set bean.status = ?1 where bean.id = ?2")
	int updateStatus(Integer status, Long id);

	SnapshotInfo findSnapshotInfoBySnapshotId(String snapshotId);

}
