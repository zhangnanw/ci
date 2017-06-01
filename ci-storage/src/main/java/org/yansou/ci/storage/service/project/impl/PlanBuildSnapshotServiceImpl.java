//package org.yansou.ci.storage.service.project.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//import org.yansou.ci.common.exception.DaoException;
//import org.yansou.ci.core.model.project.PlanBuildSnapshot;
//import org.yansou.ci.storage.common.dao.GeneralDao;
//import org.yansou.ci.storage.common.service.GeneralServiceImpl;
//import org.yansou.ci.storage.dao.project.PlanBuildSnapshotDao;
//import org.yansou.ci.storage.service.project.PlanBuildSnapshotService;
//
//@Service
//public class PlanBuildSnapshotServiceImpl extends GeneralServiceImpl<PlanBuildSnapshot, Long> implements
//		PlanBuildSnapshotService {
//	@Autowired
//	PlanBuildSnapshotDao dao;
//
//	@Override
//	public int updateStatus(Integer status, Long id) throws DaoException {
//		return 0;
//	}
//
//	@Autowired
//	@Qualifier("planBuildSnapshotDao")
//	@Override
//	public void setGeneralDao(GeneralDao<PlanBuildSnapshot, Long> generalDao) {
//		this.generalDao = generalDao;
//		this.dao = (PlanBuildSnapshotDao) generalDao;
//	}
//
//	public PlanBuildSnapshot getSnapshot(String snapshotId) {
//		return dao.getSnapshot(snapshotId);
//	}
//}
