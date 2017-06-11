package org.yansou.ci.storage.ciimp;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.yansou.ci.common.utils.JSONUtils;
import org.yansou.ci.common.utils.RegexUtils;
import org.yansou.ci.core.db.model.project.PlanBuildData;
import org.yansou.ci.data.mining.analyzer.impl.AreaAnalyzer;
import org.yansou.ci.data.mining.utils.TextTools;
import org.yansou.ci.storage.util.JSOUPUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.google.common.collect.Maps;

/**
 * RCC拟在建信息转换为ci里使用的拟在建信息。
 * 
 * @author Administrator
 *
 */
public final class Dlzb2PlanBuildDataInfo {

	private static final Logger LOG = LogManager.getLogger(Dlzb2PlanBuildDataInfo.class);
	final static private Map<String, Integer> codeMap = Maps.newHashMap();
	static {
		String[] arr = "1:北京市;2:天津市;3:上海市;4:重庆市;5:安徽省;6:福建省;7:甘肃省;8:广东省;9:贵州省;10:海南省;11:河北省;12:河南省;13:湖北省;14:湖南省;15:吉林省;16:江苏省;17:江西省;18:辽宁省;19:青海省;20:山东省;21:山西省;22:陕西省;23:四川省;24:云南省;25:浙江省;26:台湾省;27:黑龙江省;28:西藏自治区;29:内蒙古自治区;30:宁夏回族自治区;31:广西壮族自治区;32:新疆维吾尔自治区;33:香港特别行政区;34:澳门特别行政区"
				.split(";");
		for (String line : arr) {
			String[] as = line.split(":");
			codeMap.put(as[1], Integer.valueOf(as[0]));
		}
	}

	public Dlzb2PlanBuildDataInfo(JSONObject srcObj, JSONObject proObj) {
		this.srcObj = srcObj;
		this.proObj = proObj;
	}

	private JSONObject srcObj;
	private JSONObject proObj;

	public PlanBuildData get() {

		String ctx = srcObj.getString("context");
		String text = TextTools.normalize(ctx);
		PlanBuildData info = new PlanBuildData();

		info.setRowkey(srcObj.getString("url"));
		// 项目地址，省
		String projectProvince = toProvince();
		// 项目地址，市
		String projectCity = toCity();
		// 项目地址，区县
		String projectDistrict = toDistrict();

		String projectName = getProjectName(ctx);// 项目名称（工程名称）

		String[] projectCodes = null;// 项目编码，由于备案信息、招中标信息中的项目编码可能不一致，可能有多个值

		String projectIdentifie = null;// 项目唯一标识

		Double projectScale = null;// 项目规模（总采购容量），单位：MW（兆瓦）

		Double projectCost = null;// 项目造价，单位：万元

		Double projectTotalInvestment = Optional
				.ofNullable(RegexUtils.regex("投资总额\\s*([0-9,.]+)万元", TextTools.normalize(ctx), 1))
				.map(p -> p.replaceAll("[,，]", "")).map(Double::valueOf).orElse(null);// 项目总投资，单位：万元
		if (Objects.isNull(projectTotalInvestment)) {
			projectTotalInvestment = Optional
					.ofNullable(RegexUtils.regex("投资总额\\s*([0-9,.]+)亿元", TextTools.normalize(ctx), 1))
					.map(p -> p.replaceAll("[,，]", "")).map(Double::valueOf).map(price -> 10000 * price).orElse(null);
		}
		String projectDescription = null;// 项目描述

		String projectAddress = null;// 项目详细地址

		String projcetOwner = JSOUPUtils
				.find(ctx,
						"body > div:nth-child(8) > div.m_l.f_l > div > table > tbody > tr:nth-child(5) > td:nth-child(2)")
				.map(e -> e.text()).orElse(null);// 甲方、项目业主、开发商、采购人、项目法人

		Integer ownerType = null;// 业主类型

		String parentCompany = null;// 项目业主、开放商、采购人的母公司

		String planBuildStatus = JSOUPUtils
				.find(ctx,
						"body > div:nth-child(8) > div.m_l.f_l > div > table > tbody > tr:nth-child(2) > td:nth-child(4)")
				.map(e -> e.text()).orElse(null);// 拟在建项目阶段，由乐叶提供

		String purchaseSituation = null;// 设备购置情况，直接从RCC中获取

		String designer = null;// 设计院
		String statusUpdate = null;// 状态更新
		Date planStartTime = null;// 计划开工时间
		// 状态
		Integer status = 0;
		// URL
		String url = srcObj.getString("url");

		Date publishTime = Optional.ofNullable(RegexUtils.regex("加入时间\\s*([0-9]{4}-[0-9]{2}-[0-9]{2})", text, 1))
				.map(t -> {
					try {
						return new java.text.SimpleDateFormat("yyyy-MM-dd").parse(t);
					} catch (ParseException ep) {
						throw new IllegalStateException(ep);
					}
				}).orElse(null);// 发布时间
		info.setPublishTime(publishTime);
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
		if (StringUtils.isNotBlank(projectProvince)) {
			codeMap.keySet().stream().filter(Objects::nonNull).filter(key -> key.contains(projectProvince))
					.forEach(key -> {
						info.setProjectProvince(codeMap.get(key));
					});
		}
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

	private String getProjectName(String context) {
		return Jsoup.parse(context).select("#title").text();
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
