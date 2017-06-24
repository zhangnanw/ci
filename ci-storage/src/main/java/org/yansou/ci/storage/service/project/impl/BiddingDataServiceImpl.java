package org.yansou.ci.storage.service.project.impl;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.common.utils.SimpleDateUtils;
import org.yansou.ci.core.db.model.project.BiddingData;
import org.yansou.ci.core.db.model.project.SnapshotInfo;
import org.yansou.ci.core.rest.report.ReportRo;
import org.yansou.ci.core.rest.report.ReportUtils;
import org.yansou.ci.storage.common.repository.GeneralRepository;
import org.yansou.ci.storage.common.service.GeneralServiceImpl;
import org.yansou.ci.storage.repository.project.BiddingDataRepository;
import org.yansou.ci.storage.service.project.BiddingDataService;
import org.yansou.ci.storage.service.project.SnapshotInfoService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liutiejun
 * @create 2017-05-14 0:23
 */
@Service("biddingDataService")
@Transactional
public class BiddingDataServiceImpl extends GeneralServiceImpl<BiddingData, Long> implements BiddingDataService {

	private BiddingDataRepository biddingDataRepository;
	@Autowired
	private SnapshotInfoService snapshotInfoService;

	@Autowired
	@Qualifier("biddingDataRepository")
	@Override
	public void setGeneralRepository(GeneralRepository<BiddingData, Long> generalRepository) {
		this.generalRepository = generalRepository;
		this.biddingDataRepository = (BiddingDataRepository) generalRepository;
	}

	@Override
	public BiddingData save(BiddingData entity) throws DaoException {
		// 产品类型的判断，1-单晶硅，2-多晶硅，3-单晶硅、多晶硅，4-未知
		Integer productType = entity.getProductType();

		if (productType == null) {
			String[] monocrystallineSpecification = entity.getMonocrystallineSpecification();// 单晶硅规格
			Double[] monocrystallineCapacity = entity.getMonocrystallineCapacity();// 单晶硅的采购容量，单位：MW（兆瓦）
			String[] polysiliconSpecification = entity.getPolysiliconSpecification();// 多晶硅规格
			Double[] polysiliconCapacity = entity.getPolysiliconCapacity();// 多晶硅的采购容量，单位：MW（兆瓦）

			int monFlag = 0;
			int polFlag = 0;

			if (ArrayUtils.isEmpty(monocrystallineSpecification) || ArrayUtils.isEmpty(monocrystallineCapacity)) {
				monFlag = 1;
			}

			if (ArrayUtils.isEmpty(polysiliconSpecification) || ArrayUtils.isEmpty(polysiliconCapacity)) {
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

		return super.save(entity);
	}

	@Override
	public void saveDataAndSnapshotInfo(BiddingData data, SnapshotInfo snap) throws DaoException {
		snapshotInfoService.save(snap);
		this.save(data);
	}

	@Override
	public List<BiddingData> findByProjectIdentifie(String projectIdentifie) throws DaoException {
		return biddingDataRepository.findByProjectIdentifie(projectIdentifie);
	}

	@Override
	public int updateChecked(Long[] ids, Integer checked) throws DaoException {
		if (ArrayUtils.isEmpty(ids) || checked == null) {
			return -1;
		}

		return biddingDataRepository.updateChecked(ids, checked);
	}

	@Override
	public ReportRo statisticsByProductType(Date startTime, Date endTime) throws DaoException {
		if (startTime == null) {
			startTime = SimpleDateUtils.getADate(1970, 01, 01, 0, 0, 0);
		}

		if (endTime == null) {
			endTime = SimpleDateUtils.getCurrDate();
		}

		String hql = "select bean.productType, count(*) productTypeCount from BiddingData bean where " + "bean" + "" +
				".publishTime between :startTime and :endTime group by bean.productType";

		Map<String, Object> valuesMap = new HashMap<>();
		valuesMap.put("startTime", startTime);
		valuesMap.put("endTime", endTime);

		List<Map<String, Object>> dataList = biddingDataRepository.findByHql(hql, valuesMap);

		String xKey = "productType";
		String yKey = null;
		String[] serieKeys = new String[]{"productTypeCount"};

		return ReportUtils.convert(dataList, xKey, yKey, serieKeys);
	}
}
