package org.yansou.ci.storage.service.project.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.model.project.PlanBuildData;
import org.yansou.ci.storage.common.dao.GeneralDao;
import org.yansou.ci.storage.common.service.GeneralServiceImpl;
import org.yansou.ci.storage.dao.project.PlanBuildDataDao;
import org.yansou.ci.storage.service.project.PlanBuildDataService;

/**
 * @author liutiejun
 * @create 2017-05-14 0:23
 */
@Service("planBuildDataService")
@Transactional
public class PlanBuildDataServiceImpl extends GeneralServiceImpl<PlanBuildData, Long> implements PlanBuildDataService {

	private PlanBuildDataDao planBuildDataDao;

	@Autowired
	@Qualifier("planBuildDataDao")
	@Override
	public void setGeneralDao(GeneralDao<PlanBuildData, Long> generalDao) {
		this.generalDao = generalDao;
		this.planBuildDataDao = (PlanBuildDataDao) generalDao;
	}

	@Override
	public int updateStatus(Integer status, Long id) throws DaoException {
		return planBuildDataDao.updateStatus(status, id);
	}

}
