package com.yansou.ci.storage.dao.project;

import com.yansou.ci.core.model.project.BiddingData;
import com.yansou.ci.storage.common.dao.GeneralDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author liutiejun
 * @create 2017-05-14 0:15
 */
@Repository("biddingDataDao")
public interface BiddingDataDao extends GeneralDao<BiddingData, Long> {

	@Modifying
	@Query("update BiddingData bd set bd.status = ?1 where bd.id = ?2")
	int updateStatus(Integer status, Long id);

}
