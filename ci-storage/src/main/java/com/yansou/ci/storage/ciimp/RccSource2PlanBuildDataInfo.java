package com.yansou.ci.storage.ciimp;

import java.util.Date;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yansou.ci.core.model.project.PlanBuildData;

import wang.biaoshu.analyzer.nlpir.impl.AreaAnalyzer;

public class RccSource2PlanBuildDataInfo {

	public RccSource2PlanBuildDataInfo(JSONObject obj) {
		this.obj = obj;
	}
	// RccSource2PlanBuildData

	private JSONObject obj;

	public PlanBuildData get() {
		PlanBuildData info = new PlanBuildData();
		// 项目地址，省
		String projectProvince = toProvince();
		// 项目地址，市
		String projectCity = toCity();
		// 项目地址，区县
		String projectDistrict = toDistrict();

		String projectName = null;// 项目名称（工程名称）

		String[] projectCodes = null;// 项目编码，由于备案信息、招中标信息中的项目编码可能不一致，可能有多个值

		String projectIdentifie = null;// 项目唯一标识

		Double projectScale = null;// 项目规模（总采购容量），单位：MW（兆瓦）

		Long projectCost = null;// 项目造价，单位：元

		Long projectTotalInvestment = null;// 项目总投资，单位：元

		String projectDescription = null;// 项目描述

		String projectAddress = null;// 项目详细地址

		String projcetOwner = null;// 甲方、项目业主、开发商、采购人、项目法人

		Integer ownerType = null;// 业主类型

		String parentCompany = null;// 项目业主、开放商、采购人的母公司

		Integer planBuildStatus = null;// 拟在建项目阶段，由乐叶提供

		String purchaseSituation = null;// 设备购置情况，直接从RCC中获取

		String designer = null;// 设计师

		String statusUpdate = null;// 状态更新

		Date planStartTime = null;// 计划开工时间

		// 状态
		Integer status = 0;
		// URL
		String url = obj.getString("url");

		info.setDesigner(designer);
		info.setOwnerType(ownerType);
		info.setParentCompany(parentCompany);
		info.setPlanBuildStatus(planBuildStatus);
		info.setPlanStartTime(planStartTime);
		info.setProjcetOwner(projcetOwner);
		info.setProjectAddress(projectAddress);
		info.setProjectCity(projectCity);
		info.setProjectCodes(projectCodes);
		info.setProjectCost(projectCost);
		info.setProjectDescription(projectDescription);
		info.setProjectDistrict(projectDistrict);
		info.setProjectIdentifie(projectIdentifie);
		info.setProjectName(projectName);
		info.setProjectProvince(projectProvince);
		info.setProjectScale(projectScale);
		info.setProjectTotalInvestment(projectTotalInvestment);
		info.setPurchaseSituation(purchaseSituation);
		info.setStatus(status);
		info.setStatusUpdate(statusUpdate);
		info.setUpdateTime(new Date());
		info.setCreateTime(new Date());
		info.setUrl(url);
		return info;
	}

	/**
	 * 地区解析器
	 */
	private static AreaAnalyzer AREA_ANALYZER = new AreaAnalyzer();

	private String toProvince() {
		JSONObject res = AREA_ANALYZER.analy(obj);
		JSONArray arr = res.getJSONArray("area");
		if (null == arr || arr.isEmpty()) {
			return null;
		}
		return arr.getString(0);
	}

	private String toCity() {
		JSONObject res = AREA_ANALYZER.analy(obj);
		JSONArray arr = res.getJSONArray("area");
		if (null == arr || arr.size() <= 1) {
			return null;
		}
		return arr.getString(1);
	}

	private String toDistrict() {
		JSONObject res = AREA_ANALYZER.analy(obj);
		JSONArray arr = res.getJSONArray("area");
		if (null == arr || arr.size() <= 2) {
			return null;
		}
		return arr.getString(2);
	}
}