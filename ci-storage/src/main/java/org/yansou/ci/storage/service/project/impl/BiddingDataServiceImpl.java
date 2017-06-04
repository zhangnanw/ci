package org.yansou.ci.storage.service.project.impl;

import org.apache.commons.lang3.StringUtils;
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
	public BiddingData save(BiddingData entity) throws DaoException {
		// 产品类型的判断，1-单晶硅，2-多晶硅，3-单晶硅、多晶硅，4-未知
		Integer productType = entity.getProductType();

		if (productType == null) {
			String monocrystallineSpecification = entity.getMonocrystallineSpecification();// 单晶硅规格
			Double monocrystallineCapacity = entity.getMonocrystallineCapacity();// 单晶硅的采购容量，单位：MW（兆瓦）
			String polysiliconSpecification = entity.getPolysiliconSpecification();// 多晶硅规格
			Double polysiliconCapacity = entity.getPolysiliconCapacity();// 多晶硅的采购容量，单位：MW（兆瓦）

			int monFlag = 0;
			int polFlag = 0;

			if (StringUtils.isNotBlank(monocrystallineSpecification) || (monocrystallineCapacity != null &&
					monocrystallineCapacity > 0.0D)) {
				monFlag = 1;
			}

			if (StringUtils.isNotBlank(polysiliconSpecification) || (polysiliconCapacity != null &&
					polysiliconCapacity > 0.0D)) {
				polFlag = 1;
			}

			if (monFlag == 0) {
				if (polFlag == 1) {
					productType = BiddingData.ProductType.POL.getValue();
				} else {
					productType = BiddingData.ProductType.UNKNOWN.getValue();
				}
			} else {
				if (polFlag == 0) {
					productType = BiddingData.ProductType.MON.getValue();
				} else {
					productType = BiddingData.ProductType.MON_POL.getValue();
				}
			}

			entity.setProductType(productType);
		}

		return biddingDataDao.save(entity);
	}

	@Override
	public void saveDataAndSnapshotInfo(BiddingData data, SnapshotInfo snap) throws DaoException {
		snapshotInfoService.save(snap);
		this.save(data);
	}
}
