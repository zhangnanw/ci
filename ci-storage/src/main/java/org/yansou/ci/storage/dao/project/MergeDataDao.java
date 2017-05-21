package org.yansou.ci.storage.dao.project;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.yansou.ci.core.model.project.MergeData;
import org.yansou.ci.storage.common.dao.GeneralDao;

/**
 * @author liutiejun
 * @create 2017-05-21 12:19
 */
@Repository("mergeDataDao")
public interface MergeDataDao extends GeneralDao<MergeData, Long> {

	@Modifying
	@Query("update MergeData bean set bean.status = ?1 where bean.id = ?2")
	int updateStatus(Integer status, Long id);

}
