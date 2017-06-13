package org.yansou.ci.storage.ciimp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.jsoup.Jsoup;
import org.yansou.ci.common.utils.JSONUtils;
import org.yansou.ci.common.utils.RegexUtils;
import org.yansou.ci.core.db.model.project.BiddingData;
import org.yansou.ci.data.mining.analyzer.impl.AreaAnalyzer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

public class RawBidd2CiBiddingData {
	final static private org.apache.logging.log4j.Logger LOG = LogManager.getLogger(RawBidd2CiBiddingData.class);
	final static private Map<String, Integer> codeMap = Maps.newHashMap();

	static {
		String[] arr = "1:北京市;2:天津市;3:上海市;4:重庆市;5:安徽省;6:福建省;7:甘肃省;8:广东省;9:贵州省;10:海南省;11:河北省;12:河南省;13:湖北省;14:湖南省;15:吉林省;16:江苏省;17:江西省;18:辽宁省;19:青海省;20:山东省;21:山西省;22:陕西省;23:四川省;24:云南省;25:浙江省;26:台湾省;27:黑龙江省;28:西藏自治区;29:内蒙古自治区;30:宁夏回族自治区;31:广西壮族自治区;32:新疆维吾尔自治区;33:香港特别行政区;34:澳门特别行政区"
				.split(";");
		for (String line : arr) {
			String[] as = line.split(":");
			codeMap.put(as[1], Integer.valueOf(as[0]));
		}
	}

	public RawBidd2CiBiddingData(JSONObject obj, JSONObject proObj) {
		this.srcObj = obj;
		this.proObj = proObj;
	}

	private JSONObject proObj;
	private JSONObject srcObj;

	public BiddingData get() {
		BiddingData info = new BiddingData();

		// 项目名称（工程名称）也是（公告名）
		String projectName = filterProjectName(Jsoup.parse(srcObj.getString("context")).title());

		// 项目编码，由于备案信息、招中标信息中的项目编码可能不一致，可能有多个值
		String[] projectCodes = null;

		// 项目唯一标识
		String projectIdentifie = null;

		// 项目规模（总采购容量），单位：MW（兆瓦）
		Double projectScale = null;

		// 项目造价，单位：元
		Double projectCost = null;

		// 项目总投资，单位：万元
		Double projectTotalInvestment = null;

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
		String url = srcObj.getString("url");
		// 发布时间
		Date publishTime = getPubTime(srcObj);
		info.setPublishTime(publishTime);
		info.setUrl(url);
		info.setWinTime(winTime);
		info.setStatus(status);
		info.setRemarks(remarks);
		info.setPurchasingMethod(purchasingMethod);
		info.setPurchasingContacts(purchasingContacts);
		info.setPurchasingContactPhone(purchasingContactPhone);
		info.setProjectTotalInvestment(projectTotalInvestment);
		info.setProjectScale(projectScale);
		if (StringUtils.isNotBlank(projectProvince)) {
			codeMap.keySet().stream().filter(Objects::nonNull).filter(key -> key.contains(projectProvince))
					.forEach(key -> {
						info.setProjectProvince(codeMap.get(key));
					});
		}
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
	 * 过滤项目名的规则
	 * 
	 * @param title
	 * @return
	 */
	private String filterProjectName(String title) {
		if (null == title) {
			return null;
		}
		title = title.replace("_中国电力招标网", "");
		return title;
	}

	private Date getPubTime(JSONObject srcObj) {
		String context = srcObj.getString("context");
		String regex = "发布日期\\s*[:：]\\s*(?<time>[0-9]{4}-[0-9]{2}-[0-9]{2})";
		String dates = RegexUtils.regex(regex, context, "time");
		if (null != dates) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			try {
				return sdf.parse(dates);
			} catch (ParseException e) {
				LOG.info(e);
			}
		}
		return null;

	}

	/**
	 * 地区解析器
	 */
	private static AreaAnalyzer AREA_ANALYZER = new AreaAnalyzer();

	private String toProvince() {
		JSONObject res = AREA_ANALYZER.analy(srcObj);
		JSONArray arr = res.getJSONArray("area");
		if (JSONUtils.isEmpty(arr)) {
			return null;
		}
		return arr.getString(0);
	}

	private String toCity() {
		JSONObject res = AREA_ANALYZER.analy(srcObj);
		JSONArray arr = res.getJSONArray("area");
		if (JSONUtils.isEmpty(arr) || arr.size() <= 1) {
			return null;
		}
		return arr.getString(1);
	}

	private String toDistrict() {
		JSONObject res = AREA_ANALYZER.analy(srcObj);
		JSONArray arr = res.getJSONArray("area");
		if (JSONUtils.isEmpty(arr) || arr.size() <= 2) {
			return null;
		}
		return arr.getString(2);
	}
}
