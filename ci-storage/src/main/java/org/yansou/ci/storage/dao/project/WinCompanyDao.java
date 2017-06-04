package org.yansou.ci.storage.dao.project;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.yansou.ci.core.model.project.BiddingData;
import org.yansou.ci.core.model.project.WinCompany;
import org.yansou.ci.storage.common.dao.GeneralDao;

import java.util.List;

/**
 * @author liutiejun
 * @create 2017-05-21 12:17
 */
@Repository("winCompanyDao")
public interface WinCompanyDao extends GeneralDao<WinCompany, Long> {

	@Modifying
	@Query("update WinCompany bean set bean.status = ?1 where bean.id = ?2")
	int updateStatus(Integer status, Long id);

	List<WinCompany> findByBiddingData(BiddingData biddingData);

	List<WinCompany> findByBiddingDataAndStatusNot(BiddingData biddingData, Integer status);

}
