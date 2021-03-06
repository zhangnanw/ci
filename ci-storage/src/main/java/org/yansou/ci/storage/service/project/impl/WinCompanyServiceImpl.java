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
import org.yansou.ci.common.utils.SimpleDateUtils;
import org.yansou.ci.core.db.model.AbstractModel;
import org.yansou.ci.core.db.model.project.BiddingData;
import org.yansou.ci.core.db.model.project.WinCompany;
import org.yansou.ci.core.rest.report.ReportRo;
import org.yansou.ci.core.rest.report.ReportUtils;
import org.yansou.ci.storage.common.repository.GeneralRepository;
import org.yansou.ci.storage.common.service.GeneralServiceImpl;
import org.yansou.ci.storage.repository.project.WinCompanyRepository;
import org.yansou.ci.storage.service.project.BiddingDataService;
import org.yansou.ci.storage.service.project.WinCompanyService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liutiejun
 * @create 2017-05-14 0:23
 */
@Service("winCompanyService")
@Transactional
public class WinCompanyServiceImpl extends GeneralServiceImpl<WinCompany, Long> implements WinCompanyService {

	private WinCompanyRepository winCompanyRepository;

	@Autowired
	private BiddingDataService biddingDataService;

	@Autowired
	@Qualifier("winCompanyRepository")
	@Override
	public void setGeneralRepository(GeneralRepository<WinCompany, Long> generalRepository) {
		this.generalRepository = generalRepository;
		this.winCompanyRepository = (WinCompanyRepository) generalRepository;
	}

	@Override
	public WinCompany save(WinCompany entity) throws DaoException {
		if (entity == null) {
			return null;
		}

		Double winAmount = entity.getWinAmount();// 中标单位-中标金额，单位：万元
		if (winAmount == null) {
			entity.setWinAmount(0.0D);
		}

		Double winPrice = entity.getWinPrice();// 中标单位-中标单价 = 中标金额 / 中标容量，单位：元每瓦
		if (winPrice == null) {
			entity.setWinPrice(0.0D);
		}

		Double winCapacity = entity.getWinCapacity();// 中标单位-中标容量，单位：MW（兆瓦）
		if (winCapacity == null) {
			entity.setWinCapacity(0.0D);
		}

		entity.setStatus(AbstractModel.Status.NORMAL.getValue());
		entity = winCompanyRepository.save(entity);

		BiddingData biddingData = biddingDataService.findById(entity.getBiddingData().getId());

		List<WinCompany> winCompanyList = winCompanyRepository.findByBiddingDataAndStatusNot(biddingData,
				AbstractModel.Status.DELETE.getValue());

		Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
			@Override
			public boolean shouldSkipField(FieldAttributes f) {
				return f.getName().endsWith("Time");
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

	@Override
	public WinCompany update(WinCompany entity) throws DaoException {
		return save(entity);
	}

	@Override
	public ReportRo statisticsByWinCapacity(Date startTime, Date endTime, int limit) throws DaoException {
		if (startTime == null) {
			startTime = SimpleDateUtils.getADate(1970, 01, 01, 0, 0, 0);
		}

		if (endTime == null) {
			endTime = SimpleDateUtils.getCurrDate();
		}

		if (limit <= 0) {
			limit = 20;
		}

		String hql = "select bean.companyName as companyName, sum(bean.winCapacity) as totalCapacity from WinCompany bean " +
				"where bean.winTime between :startTime and :endTime " +
				"group by bean.companyName order by totalCapacity desc";

		Map<String, Object> valuesMap = new HashMap<>();
		valuesMap.put("startTime", startTime);
		valuesMap.put("endTime", endTime);

		List<Map<String, Object>> dataList = winCompanyRepository.findByHql(hql, 0, limit, valuesMap);

		String xKey = "companyName";
		String yKey = null;
		String[] serieKeys = new String[]{"totalCapacity"};

		return ReportUtils.convert(dataList, xKey, yKey, serieKeys);
	}

	@Override
	public ReportRo statisticsByWinCount(Date startTime, Date endTime, int limit) throws DaoException {
		if (startTime == null) {
			startTime = SimpleDateUtils.getADate(1970, 01, 01, 0, 0, 0);
		}

		if (endTime == null) {
			endTime = SimpleDateUtils.getCurrDate();
		}

		if (limit <= 0) {
			limit = 20;
		}

		String hql = "select bean.companyName as companyName, count(bean.winCapacity) as winCount from WinCompany bean " +
				"where bean.winTime between :startTime and :endTime " +
				"group by bean.companyName order by winCount desc";

		Map<String, Object> valuesMap = new HashMap<>();
		valuesMap.put("startTime", startTime);
		valuesMap.put("endTime", endTime);

		List<Map<String, Object>> dataList = winCompanyRepository.findByHql(hql, 0, limit, valuesMap);

		String xKey = "companyName";
		String yKey = null;
		String[] serieKeys = new String[]{"winCount"};

		return ReportUtils.convert(dataList, xKey, yKey, serieKeys);
	}

}
