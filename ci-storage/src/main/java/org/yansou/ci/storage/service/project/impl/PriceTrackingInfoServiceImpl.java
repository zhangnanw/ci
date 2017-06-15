package org.yansou.ci.storage.service.project.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yansou.ci.core.db.model.project.PriceTrackingInfo;
import org.yansou.ci.storage.common.repository.GeneralRepository;
import org.yansou.ci.storage.common.service.GeneralServiceImpl;
import org.yansou.ci.storage.repository.project.PriceTrackingInfoRepository;
import org.yansou.ci.storage.service.project.PriceTrackingInfoService;

/**
 * @author liutiejun
 * @create 2017-05-14 0:23
 */
@Service("priceTrackingInfoService")
@Transactional
public class PriceTrackingInfoServiceImpl extends GeneralServiceImpl<PriceTrackingInfo, Long> implements
		PriceTrackingInfoService {

	private PriceTrackingInfoRepository priceTrackingInfoRepository;

	@Autowired
	@Qualifier("priceTrackingInfoRepository")
	@Override
	public void setGeneralRepository(GeneralRepository<PriceTrackingInfo, Long> generalRepository) {
		this.generalRepository = generalRepository;
		this.priceTrackingInfoRepository = (PriceTrackingInfoRepository) generalRepository;
	}

}
