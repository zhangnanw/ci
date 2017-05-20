package com.yansou.ci.storage.service.project.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yansou.ci.common.exception.DaoException;
import com.yansou.ci.common.page.PageCriteria;
import com.yansou.ci.core.model.project.PlanBuildSnapshot;
import com.yansou.ci.storage.common.dao.GeneralDao;
import com.yansou.ci.storage.common.service.GeneralServiceImpl;
import com.yansou.ci.storage.dao.project.PlanBuildSnapshotDao;
import com.yansou.ci.storage.service.project.PlanBuildSnapshotService;

@Service
public class PlanBuildSnapshotServiceImpl extends GeneralServiceImpl<PlanBuildSnapshot, Long>
		implements PlanBuildSnapshotService {
	@Autowired
	PlanBuildSnapshotDao dao;

	@Override
	public int updateStatus(Integer status, Long id) throws DaoException {
		return 0;
	}

	@Autowired
	@Qualifier("planBuildSnapshotDao")
	@Override
	public void setGeneralDao(GeneralDao<PlanBuildSnapshot, Long> generalDao) {
		this.generalDao = generalDao;
		this.dao = (PlanBuildSnapshotDao) generalDao;
	}

	@Override
	public Specification<PlanBuildSnapshot> createSpecification(PageCriteria pageCriteria) {
		return null;
	}

	public PlanBuildSnapshot getSnapshot(String snapshotId) {
		return dao.getSnapshot(snapshotId);
	}
}
