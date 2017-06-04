package org.yansou.ci.storage.service.project.impl;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.model.project.BiddingData;
import org.yansou.ci.core.model.project.WinCompany;
import org.yansou.ci.storage.common.dao.GeneralDao;
import org.yansou.ci.storage.common.service.GeneralServiceImpl;
import org.yansou.ci.storage.dao.project.WinCompanyDao;
import org.yansou.ci.storage.service.project.BiddingDataService;
import org.yansou.ci.storage.service.project.WinCompanyService;

import java.util.List;

/**
 * @author liutiejun
 * @create 2017-05-14 0:23
 */
@Service("winCompanyService")
@Transactional
public class WinCompanyServiceImpl extends GeneralServiceImpl<WinCompany, Long> implements WinCompanyService {

	private WinCompanyDao winCompanyDao;

	@Autowired
	private BiddingDataService biddingDataService;

	@Autowired
	@Qualifier("winCompanyDao")
	@Override
	public void setGeneralDao(GeneralDao<WinCompany, Long> generalDao) {
		this.generalDao = generalDao;
		this.winCompanyDao = (WinCompanyDao) generalDao;
	}

	@Override
	public int updateStatus(Integer status, Long id) throws DaoException {
		return winCompanyDao.updateStatus(status, id);
	}

	@Override
	public WinCompany save(WinCompany entity) throws DaoException {
		if (entity == null) {
			return null;
		}

		entity = winCompanyDao.save(entity);

		BiddingData biddingData = biddingDataService.findById(entity.getBiddingData().getId());

		List<WinCompany> winCompanyList = winCompanyDao.findWinCompaniesByBiddingData(biddingData);

		Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
			@Override
			public boolean shouldSkipField(FieldAttributes f) {
				return false;
			}

			@Override
			public boolean shouldSkipClass(Class<?> clazz) {
				return clazz.equals(BiddingData.class);
			}
		}).create();

		String winCompanyInfo = gson.toJson(winCompanyList);

		biddingData.setWinCompanyInfo(winCompanyInfo);

		// 更新BiddingData中的中标单位信息
		biddingDataService.update(biddingData);

		return entity;
	}

}
