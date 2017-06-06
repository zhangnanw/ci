package org.yansou.ci.storage.repository.project;

import org.springframework.stereotype.Repository;
import org.yansou.ci.core.model.project.BiddingData;
import org.yansou.ci.core.model.project.WinCompany;
import org.yansou.ci.storage.common.repository.GeneralRepository;

import java.util.List;

/**
 * @author liutiejun
 * @create 2017-05-21 12:17
 */
@Repository("winCompanyRepository")
public interface WinCompanyRepository extends GeneralRepository<WinCompany, Long> {

	List<WinCompany> findByBiddingData(BiddingData biddingData);

	List<WinCompany> findByBiddingDataAndStatusNot(BiddingData biddingData, Integer status);

}
