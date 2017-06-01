package org.yansou.ci.storage.service.project.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.model.project.SnapshotInfo;
import org.yansou.ci.storage.common.dao.GeneralDao;
import org.yansou.ci.storage.common.service.GeneralServiceImpl;
import org.yansou.ci.storage.dao.project.SnapshotInfoDao;
import org.yansou.ci.storage.service.project.SnapshotInfoService;

/**
 * @author liutiejun
 * @create 2017-06-01 14:01
 */
@Service("snapshotInfoService")
@Transactional
public class SnapshotInfoServiceImpl extends GeneralServiceImpl<SnapshotInfo, Long> implements SnapshotInfoService {

	private SnapshotInfoDao snapshotInfoDao;

	@Autowired
	@Qualifier("snapshotInfoDao")
	@Override
	public void setGeneralDao(GeneralDao<SnapshotInfo, Long> generalDao) {
		this.generalDao = generalDao;
		this.snapshotInfoDao = (SnapshotInfoDao) generalDao;
	}

	@Override
	public int updateStatus(Integer status, Long id) throws DaoException {
		return snapshotInfoDao.updateStatus(status, id);
	}

	@Override
	public SnapshotInfo findBySnapshotId(String snapshotId) throws DaoException {
		if (StringUtils.isBlank(snapshotId)) {
			return null;
		}

		return snapshotInfoDao.findSnapshotInfoBySnapshotId(snapshotId);
	}

}
