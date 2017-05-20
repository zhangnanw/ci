package com.yansou.ci.storage.ciimp;

import java.util.Date;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yansou.ci.core.model.project.BiddingData;

import wang.biaoshu.analyzer.nlpir.impl.AreaAnalyzer;

public class RawBidd2CiBiddingData {

	public RawBidd2CiBiddingData(JSONObject obj) {
		this.obj = obj;
	}

	private JSONObject obj;

	public BiddingData get() {
		BiddingData info = new BiddingData();

		// 项目名称（工程名称）
		String projectName = null;

		// 项目编码，由于备案信息、招中标信息中的项目编码可能不一致，可能有多个值
		String[] projectCodes = null;

		// 项目唯一标识
		String projectIdentifie = null;

		// 项目规模（总采购容量），单位：MW（兆瓦）
		Double projectScale = null;

		// 项目造价，单位：元
		Long projectCost = null;

		// 项目总投资，单位：元
		Long projectTotalInvestment = null;

		// 项目描述
		String projectDescription = null;

		// 项目详细地址
		String projectAddress = null;

		// 项目地址，省
		String projectProvince = toProvince();

		// 项目地址，市
		String projectCity = toCity();

		// 项目地址，区县
		String projectDistrict = toDistrict();

		// 甲方、项目业主、开发商、采购人、项目法人
		String projcetOwner = null;

		// 业主类型
		Integer ownerType = null;

		// 项目业主、开放商、采购人的母公司
		String parentCompany = null;

		// 采购方式，公开招标、竞争性谈判、市场询价、单一来源、其它
		Integer purchasingMethod = null;
		// 单晶硅规格
		String monocrystallineSpecification = null;

		// 单晶硅的采购容量，单位：MW（兆瓦）
		Double monocrystallineCapacity = null;

		// 多晶硅规格
		String polysiliconSpecification = null;

		// 多晶硅的采购容量，单位：MW（兆瓦）
		Double polysiliconCapacity = null;

		// 产品的部署类型，分布式、集中式、渔光、农光，需要乐叶确定
		Integer deploymentType = null;

		// 招标时间
		Date biddingTime = null;

		// 中标时间
		Date winTime = null;

		// 招标预算
		Double biddingBudget = null;

		// 中标金额
		Double winAmount = null;

		// 中标单价
		Double winPrice = null;

		// 中标容量，单位：MW（兆瓦）
		Double winCapacity = null;

		// 中标单位
		String winCompany = null;

		// 资金来源
		String fundSource = null;

		// 采购联系人
		String purchasingContacts = null;

		// 采购联系人电话
		String purchasingContactPhone = null;

		// 代理机构
		String agency = null;

		// 代理机构联系人
		String agencyContacts = null;

		// 代理机构联系人电话
		String agencyContactPhone = null;

		// 客户类别，一类客户、二类客户、三类客户、互补企业、设计院、竞争对手，需要乐叶确定
		Integer customerType = null;

		// 备注
		String remarks = null;

		// 状态
		Integer status = 0;
		// URL
		String url = obj.getString("url");
		
		info.setUrl(url);
		info.setWinTime(winTime);
		info.setStatus(status);
		info.setRemarks(remarks);
		info.setPurchasingMethod(purchasingMethod);
		info.setPurchasingContacts(purchasingContacts);
		info.setPurchasingContactPhone(purchasingContactPhone);
		info.setProjectTotalInvestment(projectTotalInvestment);
		info.setProjectScale(projectScale);
		info.setProjectProvince(projectProvince);
		info.setProjectName(projectName);
		info.setProjectIdentifie(projectIdentifie);
		info.setProjectDistrict(projectDistrict);
		info.setProjectDescription(projectDescription);
		info.setProjectCost(projectCost);
		info.setProjectCodes(projectCodes);
		info.setProjectCity(projectCity);
		info.setProjectAddress(projectAddress);
		info.setProjcetOwner(projcetOwner);
		info.setPolysiliconSpecification(polysiliconSpecification);
		info.setPolysiliconCapacity(polysiliconCapacity);
		info.setParentCompany(parentCompany);
		info.setOwnerType(ownerType);
		info.setMonocrystallineSpecification(monocrystallineSpecification);
		info.setFundSource(fundSource);
		info.setDeploymentType(deploymentType);
		info.setCustomerType(customerType);
		info.setAgencyContacts(agencyContacts);
		info.setAgencyContactPhone(agencyContactPhone);
		info.setAgency(agency);
		info.setMonocrystallineCapacity(monocrystallineCapacity);
		info.setBiddingBudget(biddingBudget);
		info.setBiddingTime(biddingTime);
		info.setCreateTime(new Date());
		info.setUpdateTime(new Date());
		return info;
	}

	/**
	 * 地区解析器
	 */
	private static AreaAnalyzer AREA_ANALYZER = new AreaAnalyzer();

	private String toProvince() {
		JSONObject res = AREA_ANALYZER.analy(obj);
		JSONArray arr = res.getJSONArray("area");
		if (arr.isEmpty()) {
			return null;
		}
		return arr.getString(0);
	}

	private String toCity() {
		JSONObject res = AREA_ANALYZER.analy(obj);
		JSONArray arr = res.getJSONArray("area");
		if (arr.size() <= 1) {
			return null;
		}
		return arr.getString(1);
	}

	private String toDistrict() {
		JSONObject res = AREA_ANALYZER.analy(obj);
		JSONArray arr = res.getJSONArray("area");
		if (arr.size() <= 2) {
			return null;
		}
		return arr.getString(2);
	}
}
