package org.yansou.ci.data.mining.nlpir.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yansou.ci.data.mining.Analyzer;
import org.yansou.ci.data.mining.util.RegexUtils;
import org.yansou.ci.data.mining.util.TableScan;
import org.yansou.ci.data.mining.util.TextTools;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class RecordTypeAnalyzer implements Analyzer {
	private static final Logger LOG = LoggerFactory.getLogger(RecordTypeAnalyzer.class);

	private enum NType {
		拟建项目 {
			@Override
			public String[] vals() {
				return new String[] {};
			}
		},
		招标预告 {
			@Override
			public String[] vals() {
				return new String[] {};
			}
		},
		招标公告 {
			@Override
			public String[] vals() {
				return new String[] { "公开招标", "邀标", "询价", "竞争性谈判", "谈判", "竞争性磋商", "磋商", "单一来源", "竞价", "变更", "更正", "流拍",
						"反拍", "澄清", "延期", "采购公示", "资格预审公告", "招标" };
			}
		},
		招标结果 {
			@Override
			public String[] vals() {
				return new String[] { "中标公告", "中标公示", "中标结果公示", "成交", "废标", "流标", "资格预审结果", "终止公告", "资格预审合格公示",
						"结果公示" };
			}
		},
		招标信用信息 {
			@Override
			public String[] vals() {
				return new String[] { "合同", "验收", "违规", "其他" };
			}
		};
		static Map<String, String> tongyici = Maps.newLinkedHashMap();
		static {
			tongyici.put("变更", "更正");
		}

		public abstract String[] vals();
	}

	@Override
	public JSONObject analy(JSONObject obj) {
		JSONObject res = new JSONObject();
		List<String> regexList = Lists.newArrayList();
		Arrays.asList(NType.values()).forEach(type -> {
			Arrays.asList(type.vals()).forEach(val -> {
				regexList.add("(?<t>" + val + ")(?:结果)?(?:公告)?(?:公示)?(?:资格公告)?(?:通知书)?(?:方式采购公示)?");
			});
		});
		regexList.forEach(regex -> {
			List<Map<String, String>> map = RegexUtils.regexToListMap(regex, obj.getString("title"), "t");
			if (!map.isEmpty()) {
				res.put("record_type", map.get(0).get("t"));
			}
		});
		if (res.isEmpty()) {
			String text = obj.getString("title") + obj.getString("context");
			text += TableScan.filter(text);
			text = TextTools.normalize(text);
			if (StringUtils.isBlank(text)) {
				res.put("record_type", "");
			} else {
				res.put("record_type", atype(text));
			}
		}
		return res;
	}

	public static String atype(String text) {
		LOG.debug(text);
		String type = null;
		type = RegexUtils.regex("(?:招标方式:|招标形式:|公告性质:|采购方式为|采购方式:|招标方式\n|招标形式|公告性质)([0-9\u4e00-\u9fa5\\（\\）]+)", text,
				1);
		if (StringUtils.isBlank(type)) {
			if (text.contains("财政局政府采购投诉不予受理决定书") || text.contains("政府采购集中采购目录及标准") || text.contains("财政局政府采购投诉处理决定书")
					|| text.contains("政府采购进口产品管理办法") || text.contains("政府采购进口产品技术参数公示") || text.contains("采购项目技术参数公示")
					|| text.contains("采购意见公示") || text.contains("采购公示") || text.contains("政府采购实施的意见")) {
				type = "采购公告";
			}
		}
		if (StringUtils.isBlank(type)) {
			type = RegexUtils.regex("(?:招标范围及形式:国内)([0-9\u4e00-\u9fa5\\（\\）]+)", text, 1);
		}
		if (StringUtils.isBlank(type)) {
			type = RegexUtils.regex("(?:进行国内|项目将于近期)([0-9\u4e00-\u9fa5\\（\\）]+)", text, 1);
		}
		if (StringUtils.isBlank(type)) {
			type = RegexUtils.regex(
					"(?:进行|组织)(?:[0-9\u4e00-\u9fa5\\（\\）]+)?(公开招标|单一来源|竞争性谈判|竞争性磋商|更正|变更|询价|竞价|澄清|延期|采购公示|资格预审|中标结果公示|废标|验收|开标|电子反拍|反拍)(?:采购)?",
					text, 1);
		}
		if (StringUtils.isBlank(type)) {
			type = RegexUtils.regex("(?:采用)([0-9\u4e00-\u9fa5\\（\\）]+)(?:采购方式)", text, 1);
			if (StringUtils.contains(type, "公开招标以外的")) {
				type = null;
			}
		}
		if (StringUtils.isBlank(type)) {
			type = RegexUtils.regex(
					"(?:以)(公开招标|单一来源|竞争性谈判|竞争性磋商|更正|变更|询价|竞价|澄清|延期|采购公示|资格预审|招标|中标结果公示|废标|验收|电子反拍|反拍)(?:[0-9\u4e00-\u9fa5\\（\\）]+)?(?:方式)",
					text, 1);
		}
		if (StringUtils.isBlank(type)) {
			type = RegexUtils.regex(
					"(?:\n\r)?(竞争性谈判|中标公告|招 标 公 告|废标|终止公告|结果公示|变更公告|更正公告|成 交 公 告|电子反拍|反拍)(?:[0-9\u4e00-\u9fa5\\（\\）]+)?(?:公告)?",
					text, 1);
		}
		if (StringUtils.isBlank(type)) {
			type = RegexUtils.regex(
					"(?:采用)(?: )?(公开招标|单一来源|竞争性谈判|竞争性磋商|更正|变更|询价|询价采购|竞价|澄清|延期|采购公示|资格预审|招标|中标结果公示|废标|验收|电子反拍|反拍)(?:的)?(?:方式)?",
					text, 1);
		}
		if (StringUtils.isBlank(type)) {
			if (text.contains("废标原因") || text.contains("废标情况说明") || text.contains("废标说明") || text.contains("废标理由")
					|| text.contains("依法废标") || text.contains("按规定废标") || text.contains("应于废标") || text.contains("废标情况")
					|| text.contains("采购失败")
					|| ((text.contains("供应商不足三家") || text.contains("供应商不足3家") || text.contains("报名单位不足三家")
							|| text.contains("报名单位不足3家")) && (text.contains("重新组织采购") || text.contains("采购失败")))
					|| text.contains("终止原因") || text.contains("暂停原因") || text.contains("项目谈判失败")
					|| text.contains("本项目至报名截止时，报名单位不足三家") || text.contains("项目取消公告") || text.contains("撤销公告")) {
				type = "废标公告";
			}
		}
		if (StringUtils.isBlank(type)) {
			if (text.contains("项目中标公示") && text.contains("公示日期")) {
				type = "中标公示";
			}
		}
		if (StringUtils.isBlank(type)) {
			if ((text.contains("成交情况") && text.contains("成交金额"))
					|| (text.contains("中标供应商名称") && text.contains("中标供应商地址"))) {
				type = "中标公告";
			}
		}
		if (StringUtils.isBlank(type)) {
			if ((text.contains("更正内容") && text.contains("更正日期")) || text.contains("更正事项")
					|| (text.contains("做如下更正") && text.contains("更正为")) || text.contains("补充说明")
					|| text.contains("作如下统一更正和说明") || text.contains("重启说明理由") || text.contains("首次公告日期")) {
				type = "更正公告";
			}
		}
		if (StringUtils.isBlank(type)) {
			if (text.contains("变更内容") || text.contains("进行如下修改") || (text.contains("原公告") && text.contains("更改为"))) {
				type = "变更公告";
			}
		}
		if (StringUtils.isBlank(type)) {
			if (text.contains("竞争性磋商")) {
				type = "竞争性磋商";
			}
		}
		if (StringUtils.isBlank(type)) {
			type = RegexUtils.regex("(?:政府采购)(公开招标|单一来源|竞争性谈判|竞争性磋商|更正|变更|询价|竞价|澄清|延期|采购公示|资格预审|招标|中标结果公示|废标|验收)", text,
					1);
		}
		if (StringUtils.isBlank(type)) {
			if ((text.contains("中标供应商名称") || text.contains("中标情况") || text.contains("中标人") || text.contains("评标情况"))
					&& (text.contains("中标金额") || text.contains("成交金额") || text.contains("投标金额"))
					|| text.contains("资格预审合格供应商公告")) {
				type = "中标公告";
			}
		}
		if (StringUtils.isBlank(type)) {
			if ((text.contains("中共中央直属机关") || text.contains("中央国家机关"))
					&& (text.contains("公开征求意见") || text.contains("公开征求潜在供应商意见")
							|| (text.contains("延长") && text.contains("执行期限的公告")) || text.contains("征求意见"))) {
				type = "其他公告";
			}
		}
		if (StringUtils.isBlank(type)) {
			if (text.contains("中标候选人公示") || (text.contains("暂停公告") && text.contains("暂停原因"))
					|| (text.contains("投诉处理决定书") && text.contains("投诉人")) || text.contains("暂停接受各投标人的报名")
					|| (text.contains("北京市财政局") && text.contains("政府采购集中采购目录及标准的通知")) || text.contains("退还保证金")
					|| text.contains("标前参数公示及征询意见") || text.contains("标前答疑会公告") || text.contains("论证意见进行公示")
					|| text.contains("控制价公示")) {
				type = "其他公告";
			}
		}
		if (StringUtils.isBlank(type)) {
			if (text.contains("采购公示") || text.contains("政府采购进口产品管理办法")) {
				type = "采购公告";
			}
		}
		if (StringUtils.isBlank(type)) {
			if (((text.contains("招标文件售价") || text.contains("投标截止及开标时间") || text.contains("获取招标文件地点"))
					&& text.contains("开标地点")) || text.contains("投标人资格要求") || text.contains("对社会资本的资格要求")
					|| text.contains("供应商资格要求") || text.contains("公开征集")) {
				type = "招标公告";
			}
		}
		if (StringUtils.isBlank(type)) {
			if (text.contains("技术标准和要求 ") || text.contains("主要需求进行公示") || text.contains("技术参数公示")
					|| text.contains("资格预审公告")) {
				type = "其他公告";
			}
		}
		if (StringUtils.length(type) > 7) {
			type = null;
		}
		return type;
	}

	@Override
	public boolean match(JSONObject obj) {
		return true;
	}

}
