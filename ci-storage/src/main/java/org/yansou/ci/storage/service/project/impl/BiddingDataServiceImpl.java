package org.yansou.ci.storage.service.project.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.model.project.BiddingData;
import org.yansou.ci.core.model.project.SnapshotInfo;
import org.yansou.ci.storage.common.dao.GeneralDao;
import org.yansou.ci.storage.common.service.GeneralServiceImpl;
import org.yansou.ci.storage.dao.project.BiddingDataDao;
import org.yansou.ci.storage.service.project.BiddingDataService;
import org.yansou.ci.storage.service.project.SnapshotInfoService;

/**
 * @author liutiejun
 * @create 2017-05-14 0:23
 */
@Service("biddingDataService")
@Transactional
public class BiddingDataServiceImpl extends GeneralServiceImpl<BiddingData, Long> implements BiddingDataService {

	private BiddingDataDao biddingDataDao;
	@Autowired
	private SnapshotInfoService snapshotInfoService;

	@Autowired
	@Qualifier("biddingDataDao")
	@Override
	public void setGeneralDao(GeneralDao<BiddingData, Long> generalDao) {
		this.generalDao = generalDao;
		this.biddingDataDao = (BiddingDataDao) generalDao;
	}

	@Override
	public int updateStatus(Integer status, Long id) throws DaoException {
		return biddingDataDao.updateStatus(status, id);
	}

	@Override
	public void saveDataAndSnapshotInfo(BiddingData data, SnapshotInfo snap) throws DaoException {
		snapshotInfoService.save(snap);
		this.save(data);
	}
}
