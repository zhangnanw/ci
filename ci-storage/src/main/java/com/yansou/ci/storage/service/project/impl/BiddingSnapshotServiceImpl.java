package com.yansou.ci.storage.service.project.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yansou.ci.common.exception.DaoException;
import com.yansou.ci.common.page.PageCriteria;
import com.yansou.ci.core.model.project.BiddingSnapshot;
import com.yansou.ci.storage.common.dao.GeneralDao;
import com.yansou.ci.storage.common.service.GeneralServiceImpl;
import com.yansou.ci.storage.dao.project.BiddingSnapshotDao;
import com.yansou.ci.storage.service.project.BiddingSnapshotService;

@Service("biddingSnapshotService")
@Transactional
public class BiddingSnapshotServiceImpl extends GeneralServiceImpl<BiddingSnapshot, Long>
		implements BiddingSnapshotService {
	@Autowired
	private BiddingSnapshotDao dao;

	@Autowired
	@Qualifier("biddingSnapshotDao")
	@Override
	public void setGeneralDao(GeneralDao<BiddingSnapshot, Long> generalDao) {
		this.generalDao = generalDao;
	}

	@Override
	public int updateStatus(Integer status, Long id) throws DaoException {
		return 0;
	}

	@Override
	public Specification<BiddingSnapshot> createSpecification(PageCriteria pageCriteria) {
		return null;
	}

	@Override
	public BiddingSnapshot getSnapshot(String snapshotId) {
		return dao.getSnapshot(snapshotId);
	}
}
