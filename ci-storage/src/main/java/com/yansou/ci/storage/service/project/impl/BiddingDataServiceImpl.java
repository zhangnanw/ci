package com.yansou.ci.storage.service.project.impl;

import com.yansou.ci.common.exception.DaoException;
import com.yansou.ci.common.page.PageCriteria;
import com.yansou.ci.core.model.project.BiddingData;
import com.yansou.ci.storage.common.dao.GeneralDao;
import com.yansou.ci.storage.common.service.GeneralServiceImpl;
import com.yansou.ci.storage.dao.project.BiddingDataDao;
import com.yansou.ci.storage.service.project.BiddingDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author liutiejun
 * @create 2017-05-14 0:23
 */
@Service("biddingDataService")
@Transactional
public class BiddingDataServiceImpl extends GeneralServiceImpl<BiddingData, Long> implements BiddingDataService {

	private BiddingDataDao biddingDataDao;

	@Autowired
	@Qualifier("biddingDataDao")
	@Override
	public void setGeneralDao(GeneralDao<BiddingData, Long> generalDao) {
		this.generalDao = generalDao;
		this.biddingDataDao = (BiddingDataDao) generalDao;
	}

	@Override
	public Specification<BiddingData> createSpecification(PageCriteria pageCriteria) {
		return null;
	}

	@Override
	public int updateStatus(Integer status, Long id) throws DaoException {
		return biddingDataDao.updateStatus(status, id);
	}

}
