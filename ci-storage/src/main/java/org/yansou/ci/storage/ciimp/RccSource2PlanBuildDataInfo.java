package org.yansou.ci.storage.ciimp;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.yansou.ci.common.utils.JSONUtils;
import org.yansou.ci.common.utils.RegexUtils;
import org.yansou.ci.core.model.project.PlanBuildData;
import org.yansou.ci.data.mining.nlpir.impl.AreaAnalyzer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

/**
 * RCC拟在建信息转换为ci里使用的拟在建信息。
 * 
 * @author Administrator
 *
 */
public class RccSource2PlanBuildDataInfo {

	public RccSource2PlanBuildDataInfo(JSONObject srcObj, JSONObject proObj) {
		this.srcObj = srcObj;
		this.proObj = proObj;
	}

	private JSONObject srcObj;
	private JSONObject proObj;

	public PlanBuildData get() {
		JSONObject prevPro = proObj.getJSONObject("prev");

		String ctx = srcObj.getString("page_source");

		PlanBuildData info = new PlanBuildData();
		info.setRowkey(srcObj.getString("rowkey"));
		// 项目地址，省
		String projectProvince = toProvince();
		// 项目地址，市
		String projectCity = toCity();
		// 项目地址，区县
		String projectDistrict = toDistrict();

		String projectName = proObj.getString("project_name");// 项目名称（工程名称）

		String[] projectCodes = null;// 项目编码，由于备案信息、招中标信息中的项目编码可能不一致，可能有多个值

		String projectIdentifie = proObj.getString("project_number");// 项目唯一标识

		Double projectScale = null;// 项目规模（总采购容量），单位：MW（兆瓦）

		Long projectCost = null;// 项目造价，单位：元

		Long projectTotalInvestment = proObj.getLong("investment_amounts");// 项目总投资，单位：元

		String projectDescription = null;// 项目描述

		String projectAddress = null;// 项目详细地址

		String projcetOwner = getValue(proObj.getString("proprietor"), "company");// 甲方、项目业主、开发商、采购人、项目法人

		Integer ownerType = null;// 业主类型

		String parentCompany = null;// 项目业主、开放商、采购人的母公司

		Integer planBuildStatus = null;// 拟在建项目阶段，由乐叶提供
		String purchaseSituation = RegexUtils.regex("设备购置情况[:：](.{5,80})", ctx.replaceAll("<[^>]*>", ""), 1);// 设备购置情况，直接从RCC中获取

		String designer = getValue(proObj.getString("designing_institute"), "company");
		String privStatus = prevPro.getString("project_stage");
		String statusUpdate = proObj.getString("project_stage");// 状态更新
		// 状态变更
		if (StringUtils.isNoneBlank(privStatus, statusUpdate)) {
			if (!StringUtils.equals(privStatus, statusUpdate)) {
				statusUpdate = privStatus + " 变更为 " + statusUpdate;
			}
		}
		Date planStartTime = null;// 计划开工时间
		// 状态
		Integer status = 0;
		// URL
		String url = srcObj.getString("url");

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

	public String path(JSONObject obj, String path) {
		if (null == obj) {
			return null;
		}
		JSONPath jpath = JSONPath.compile(path);
		return (String) jpath.eval(obj);
	}

	private String toPhoneCodes(String str) {
		JSONArray arr = JSON.parseArray(str);
		StringBuffer sb = new StringBuffer();
		JSONUtils.streamJSONObject(arr).map(this::toPhoneCode).forEach(a -> sb.append(a + ","));
		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}

	private String toPhoneCode(JSONObject obj) {
		String countryCode = obj.getString("countryCode");
		String areaCode = obj.getString("areaCode");
		String phone = obj.getString("phone");
		String mobile = obj.getString("mobile");
		if ("86".equals(countryCode)) {
			countryCode = "0";
		}
		if (StringUtils.isNoneBlank(countryCode, areaCode, phone)) {
			return countryCode + areaCode + "-" + phone;
		}
		if (StringUtils.isNoneBlank(mobile)) {
			if ("0".equals(countryCode)) {
				return mobile;
			} else {
				return "+" + countryCode + mobile;
			}

		}
		if (StringUtils.isNoneBlank(phone)) {
			return phone;
		}
		return "";
	}

	String getCellValue(String jsonStr) {
		if (StringUtils.isBlank(jsonStr)) {
			return "";
		}
		JSONArray arr = JSON.parseArray(jsonStr);
		StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
		for (JSONObject obj : arr.stream().map(o -> (JSONObject) o).collect(Collectors.toList())) {
			Optional.ofNullable(obj.getString("company")).filter(StringUtils::isNotBlank).map(x -> "公司：" + x)
					.ifPresent(out::println);
			Optional.ofNullable(obj.getString("contractor")).filter(StringUtils::isNotBlank).map(x -> "联系人：" + x)
					.ifPresent(out::println);
			Optional.ofNullable(obj.getString("remark")).filter(StringUtils::isNotBlank).map(x -> "备注：" + x)
					.ifPresent(out::println);
			Optional.ofNullable(obj.getString("phone")).filter(StringUtils::isNotBlank).map(this::toPhoneCodes)
					.map(x -> "联系方式：" + x).ifPresent(out::println);
			Optional.ofNullable(obj.getString("email")).filter(StringUtils::isNotBlank).map(x -> "邮件：" + x)
					.ifPresent(out::println);
			Optional.ofNullable(obj.getString("website")).filter(StringUtils::isNotBlank).map(x -> "网址：" + x)
					.ifPresent(out::println);
			out.println();
		}
		return sw.toString();
	}

	String getValue(String jsonStr, String key) {
		if (StringUtils.isBlank(jsonStr)) {
			return "";
		}
		JSONArray arr = JSON.parseArray(jsonStr);
		StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
		for (JSONObject obj : arr.stream().map(o -> (JSONObject) o).collect(Collectors.toList())) {
			Optional.ofNullable(obj.getString(key)).filter(StringUtils::isNotBlank).ifPresent(out::println);
			out.println();
			break;
		}
		return sw.toString();
	}

	/**
	 * 地区解析器
	 */
	private static AreaAnalyzer AREA_ANALYZER = new AreaAnalyzer();

	private String toProvince() {
		JSONObject res = AREA_ANALYZER.analy(srcObj);
		JSONArray arr = res.getJSONArray("area");
		if (null == arr || arr.isEmpty()) {
			return null;
		}
		return arr.getString(0);
	}

	private String toCity() {
		JSONObject res = AREA_ANALYZER.analy(srcObj);
		JSONArray arr = res.getJSONArray("area");
		if (null == arr || arr.size() <= 1) {
			return null;
		}
		return arr.getString(1);
	}

	private String toDistrict() {
		JSONObject res = AREA_ANALYZER.analy(srcObj);
		JSONArray arr = res.getJSONArray("area");
		if (null == arr || arr.size() <= 2) {
			return null;
		}
		return arr.getString(2);
	}
}
