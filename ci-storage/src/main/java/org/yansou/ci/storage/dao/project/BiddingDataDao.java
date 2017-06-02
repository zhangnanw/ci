package org.yansou.ci.storage.dao.project;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.yansou.ci.core.model.project.BiddingData;
import org.yansou.ci.storage.common.dao.GeneralDao;

/**
 * @author liutiejun
 * @create 2017-05-14 0:15
 */
@Repository("biddingDataDao")
public interface BiddingDataDao extends GeneralDao<BiddingData, Long> {
	@Modifying
	@Query("update BiddingData bean set bean.status = ?1 where bean.id = ?2")
	int updateStatus(Integer status, Long id);
}
