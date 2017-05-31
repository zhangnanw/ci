package org.yansou.ci.storage.service.project.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.model.project.MergeData;
import org.yansou.ci.storage.common.dao.GeneralDao;
import org.yansou.ci.storage.common.service.GeneralServiceImpl;
import org.yansou.ci.storage.dao.project.MergeDataDao;
import org.yansou.ci.storage.service.project.MergeDataService;

/**
 * @author liutiejun
 * @create 2017-05-14 0:23
 */
@Service("mergeDataService")
@Transactional
public class MergeDataServiceImpl extends GeneralServiceImpl<MergeData, Long> implements MergeDataService {

	private MergeDataDao mergeDataDao;

	@Autowired
	@Qualifier("mergeDataDao")
	@Override
	public void setGeneralDao(GeneralDao<MergeData, Long> generalDao) {
		this.generalDao = generalDao;
		this.mergeDataDao = (MergeDataDao) generalDao;
	}

	@Override
	public int updateStatus(Integer status, Long id) throws DaoException {
		return mergeDataDao.updateStatus(status, id);
	}

}
