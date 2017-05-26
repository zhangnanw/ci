package org.yansou.ci.storage.ciimp;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.yansou.ci.common.utils.RegexUtils;
import org.yansou.ci.core.model.project.PlanBuildData;
import org.yansou.ci.data.mining.nlpir.impl.AreaAnalyzer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

public class RccSource2PlanBuildDataInfo {

	public RccSource2PlanBuildDataInfo(JSONObject obj, JSONObject proObj) {
		this.obj = obj;
		this.po = proObj;
	}

	private JSONObject obj;
	private JSONObject po;

	public PlanBuildData get() {

		String ctx = obj.getString("page_source");

		PlanBuildData info = new PlanBuildData();
		info.setRowkey(obj.getString("rowkey"));
		// 项目地址，省
		String projectProvince = toProvince();
		// 项目地址，市
		String projectCity = toCity();
		// 项目地址，区县
		String projectDistrict = toDistrict();

		String projectName = po.getString("project_name");// 项目名称（工程名称）

		String[] projectCodes = null;// 项目编码，由于备案信息、招中标信息中的项目编码可能不一致，可能有多个值

		String projectIdentifie = po.getString("peoject_number");// 项目唯一标识

		Double projectScale = null;// 项目规模（总采购容量），单位：MW（兆瓦）

		Long projectCost = null;// 项目造价，单位：元

		Long projectTotalInvestment = po.getLong("investment_amounts");// 项目总投资，单位：元

		String projectDescription = null;// 项目描述

		String projectAddress = null;// 项目详细地址

		String projcetOwner = null;// 甲方、项目业主、开发商、采购人、项目法人

		Integer ownerType = null;// 业主类型

		String parentCompany = null;// 项目业主、开放商、采购人的母公司

		Integer planBuildStatus = null;// 拟在建项目阶段，由乐叶提供
		String purchaseSituation = RegexUtils.regex("设备购置情况[:：](.{5,80})", ctx.replaceAll("<[^>]*>", ""), 1);// 设备购置情况，直接从RCC中获取

		String designer = null;// 设计师

		String statusUpdate = po.getString("project_stage");// 状态更新

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
		info.setProjectDescription(projectDescription);
		info.setProjectDistrict(projectDistrict);
		info.setProjectIdentifie(projectIdentifie);
		info.setProjectName(projectName);
		info.setProjectProvince(projectProvince);
		info.setProjectScale(projectScale);
		info.setPurchaseSituation(purchaseSituation);
		info.setStatus(status);
		info.setStatusUpdate(statusUpdate);
		info.setUpdateTime(new Date());
		info.setCreateTime(new Date());
		info.setUrl(url);
		return info;
	}

	public String path(JSONObject obj, String path) {
		if (null == obj) {
			return null;
		}
		JSONPath jpath = JSONPath.compile(path);
		return (String) jpath.eval(obj);
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
