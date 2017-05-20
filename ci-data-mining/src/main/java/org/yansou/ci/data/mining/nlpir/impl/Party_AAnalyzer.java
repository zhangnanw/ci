package org.yansou.ci.data.mining.nlpir.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yansou.ci.data.mining.Analyzer;
import org.yansou.ci.data.mining.ansj.Ansj;
import org.yansou.ci.data.mining.util.ErrUtils;
import org.yansou.ci.data.mining.util.RegexUtils;
import org.yansou.ci.data.mining.util.TableScan;
import org.yansou.ci.data.mining.util.TextTools;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;

/**
 * 
 * @author LENOVO
 *
 */
public class Party_AAnalyzer implements Analyzer {
	private static final Logger LOG = LoggerFactory.getLogger(Party_AAnalyzer.class);

	@Override
	public boolean match(JSONObject obj) {
		return true;
	}

	@Override
	public JSONObject analy(JSONObject obj) {
		// try {
		// Ansj.insertWord();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		JSONObject res = new JSONObject();
		String text = obj.getString("title") + obj.getString("context");
		text += TableScan.filter(text);
		text = TextTools.normalize(text);
		// .replaceAll("^[;:.-(),]", "")
		String result = sa(text);
		res.put("party_a", result);
		if (StringUtils.isBlank(result)) {
			ErrUtils.checkAdd(obj, true, "甲方找不到", 0.0004);
			LOG.debug("甲方找不到");
		}
		try {
			result = result.replaceAll("[()（）“”\"]", "");
			LOG.debug(result);
			char[] t1 = null;
			t1 = result.toCharArray();
			int t0 = t1.length;
			int count = 0;
			for (int i = 0; i < t0; i++) {
				if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+"))
					count++;
			}
			if (count > 25) {
				ErrUtils.checkAdd(obj, true, "甲方字数超过25个", 16);
				LOG.debug("甲方字数超过25个");
			}
			String fch = Ansj.getInstance().raragraphProcess(result);
			if (StringUtils.contains(fch, "u") || StringUtils.contains(fch, "c") || StringUtils.contains(fch, "d")
					|| StringUtils.contains(fch, "e") || StringUtils.contains(fch, "w")) {
				ErrUtils.checkAdd(obj, true, "甲方包含助词连词副词叹词或符号", 0.0016);
				LOG.debug("甲方包含助词连词副词叹词或符号");
			}
		} catch (Exception e) {
		}
		return res;
	}

	public static String sa(String text) {
		LOG.debug(text);
		String p_a = null;
		p_a = RegexUtils.regex(
				"(?:采购人（公章）|招标人名称|招标人|招 标 人|采购人|招标单位名称|招标单位|采购单位名称|采购人名称|招标机构|采购单位|采购机构名称|委托单位|申请单位|项目单位):([0-9\u4e00-\u9fa5\\（\\）]+)",
				text, 1);
		LOG.debug(p_a);
		if (StringUtils.isBlank(p_a) || StringUtils.contains(p_a, "代理") || StringUtils.contains(p_a, "供应")) {
			LOG.debug("12");
			p_a = RegexUtils.regex("(?:采购人) ([0-9\u4e00-\u9fa5\\（\\）]+)", text, 1);
		}
		if (StringUtils.isBlank(p_a) || StringUtils.contains(p_a, "代理") || StringUtils.contains(p_a, "供应"))
			p_a = RegexUtils.regex("(?:谈判人名称|项目实施机构|甲方):([0-9\u4e00-\u9fa5\\（\\）]+)", text, 1);
		if (StringUtils.isBlank(p_a) || StringUtils.contains(p_a, "代理") || StringUtils.contains(p_a, "供应"))
			p_a = RegexUtils.regex("受([0-9\u4e00-\u9fa5\\（\\）]+)的委托", text, 1);
		if (StringUtils.isBlank(p_a) || StringUtils.contains(p_a, "代理") || StringUtils.contains(p_a, "供应"))
			p_a = RegexUtils.regex("受 ([0-9\u4e00-\u9fa5\\（\\）]+)委托", text, 1);
		if (StringUtils.isBlank(p_a) || StringUtils.contains(p_a, "代理") || StringUtils.contains(p_a, "供应")) {
			LOG.debug("12");
			p_a = RegexUtils.regex("受([0-9\u4e00-\u9fa5\\（\\）]+)委托", text, 1);
		}
		if (StringUtils.isBlank(p_a) || StringUtils.contains(p_a, "代理") || StringUtils.contains(p_a, "供应"))
			p_a = RegexUtils.regex("(?:集中采购机构|业主单位|采购人名称:|采购人\\(甲方\\):)\n([0-9\u4e00-\u9fa5\\（\\）]+)", text, 1);
		if (StringUtils.isBlank(p_a) || StringUtils.contains(p_a, "代理") || StringUtils.contains(p_a, "供应"))
			p_a = RegexUtils.regex("(?:采购人\r名称)([0-9\u4e00-\u9fa5\\（\\）]+)", text, 1);
		if (StringUtils.isBlank(p_a) || StringUtils.contains(p_a, "代理") || StringUtils.contains(p_a, "供应"))
			p_a = RegexUtils.regex("(?:招 标 人|建设单位|采 购 人|采购 人):([0-9\u4e00-\u9fa5\\（\\）]+)", text, 1);
		if (StringUtils.isBlank(p_a) || StringUtils.contains(p_a, "代理") || StringUtils.contains(p_a, "供应"))
			p_a = RegexUtils.regex("(?:采购人联系方式:\n名 称):([0-9\u4e00-\u9fa5\\（\\）]+)", text, 1);
		LOG.debug(p_a);
		if (p_a == "人" || StringUtils.contains(p_a, "采购") || StringUtils.contains(p_a, "代理")
				|| StringUtils.contains(p_a, "供应") || StringUtils.contains(p_a, "采购") || StringUtils.contains(p_a, "名称")
				|| StringUtils.contains(p_a, "买方") || StringUtils.contains(p_a, "招标文件")
				|| StringUtils.contains(p_a, "中标文件") || StringUtils.contains(p_a, "用户")
				|| StringUtils.contains(p_a, "招标人") || StringUtils.contains(p_a, "招标单位")
				|| StringUtils.contains(p_a, "地址") || StringUtils.contains(p_a, "评审专家"))
			p_a = "";
		boolean judje = true;
		try {
			p_a = p_a.replaceAll("（预算单位）", "");
		} catch (Exception e) {
		}
		if (StringUtils.isNotBlank(p_a) && !StringUtils.contains(p_a, "代理") && !StringUtils.contains(p_a, "采购")
				&& !StringUtils.contains(p_a, "供应")) {
			return p_a;
		}
		// 北京市黄庄职业高中
		String fch = Ansj.getInstance().raragraphProcess(text);
		LOG.debug(fch);
		String[] string;
		string = fch.split(" ");
		int colon = -1;
		int kword = -1;
		Boolean grade = true;
		int i;
		for (i = 0; i < 30 && i < string.length; i++) {
			LOG.debug(string[i]);
			if (string[i].contains("/userDefine") || string[i].contains("中直机关")) {
				String[] sourceStrArray = string[i].split("/");
				judje = false;
				return sourceStrArray[0];
			}
			// ||string[i].contains("公告")
			if (string[i].contains(":") || string[i].contains("－") || string[i].contains("—") || string[i].contains("-")
					|| string[i].contains("]") || string[i].contains("由") || string[i].contains("人/n")
					|| string[i].contains("受") || string[i].contains("“/w")) {
				LOG.debug("开始" + i);
				colon = i;
			}
			if (string[i].contains("卫生院") || string[i].contains("大队") || string[i].contains("公司")
					|| string[i].contains("管理局") || string[i].contains("学院") || string[i].contains("所")
					|| string[i].contains("馆") || string[i].contains("基地") || string[i].contains("住宅")
					|| string[i].contains("堂") || string[i].contains("心") || string[i].contains("校")
					|| string[i].contains("单位") || string[i].contains("小区") || string[i].contains("医院")
					|| string[i].contains("中心") || string[i].contains("院") || string[i].contains("大学")
					|| string[i].contains("厅") || string[i] == ("家") || string[i].contains("人寿")
					|| string[i].contains("卫生部") || string[i].contains("彩票") || string[i].contains("中学")
					|| string[i].contains("小学") || string[i].contains("铁") || string[i].contains("站")
					|| string[i].contains("协会") || string[i].contains("组") || string[i].contains("总局")
					|| string[i].contains("国家机关") || string[i].contains("民政局")) {
				LOG.debug("1结束" + i);
				kword = i;
				break;
			} else if (kword < 0 && (string[i].contains("会") || string[i].contains("社") || string[i].contains("支队")
					|| (string[i].contains("乡") && !string[i].contains("新乡市")) || string[i].contains("局")
					|| string[i].contains("机关"))) {
				LOG.debug("2结束" + i);
				kword = i;
				grade = false;
			} else if (grade = true && kword < 0
					&& (string[i].contains("部") || (string[i].contains("室") && !string[i].contains("外"))
							|| string[i].contains("区") || string[i].contains("政府") || string[i].contains("网")
							|| string[i].contains("机构"))) {
				LOG.debug("3结束" + i);
				kword = i;
			}
		}
		int ErrorN = 0;
		if (kword > colon && judje) {
			StringBuilder sb = new StringBuilder(256);
			for (i = colon > 0 ? colon + 1 : 0; i <= kword; i++) {
				LOG.debug(string[i]);
				try {
					String[] temporary = string[i].split("/");
					Preconditions.checkArgument(temporary.length > 0);
					if (temporary[0].contains("、") || temporary[0].contains("：") || temporary[0].contains("了")) {
						ErrorN = 1;
						break;
					}
					Preconditions.checkArgument(temporary.length > 0);
					if (!temporary[0].contains("\\n") && !temporary[0].contains("\n") && !temporary[0].contains("  ")
							&& !temporary[0].contains("\"") && !temporary[0].contains(" ")
							&& !temporary[0].contains("年") && !temporary[0].contains("关于")
							&& !temporary[0].contains("招标人") && !temporary[0].contains("《")
							&& !temporary[0].contains("号") && !temporary[0].contains("0") && !temporary[0].contains("1")
							&& !temporary[0].contains("2") && !temporary[0].contains("3") && !temporary[0].contains("4")
							&& !temporary[0].contains("5") && !temporary[0].contains("6") && !temporary[0].contains("7")
							&& !temporary[0].contains("8") && !temporary[0].contains("9")
							&& !temporary[0].contains("g"))
						sb.append(temporary[0]);
					LOG.debug(temporary[0]);
				} catch (Exception e) {
				}
			}
			String sa = sb.toString();
			if (ErrorN == 0 && StringUtils.isNotBlank(sa) && !StringUtils.contains(sa, "我")
					&& !StringUtils.contains(sa, "预算") && !StringUtils.contains(sa, ";")
					&& !StringUtils.contains(sa, "第一期") && !StringUtils.contains(sa, "第二期")
					&& !StringUtils.contains(sa, "采购") && !StringUtils.contains(sa, "已"))
				return sa;
		} else if (kword < colon) {
			StringBuilder sb = new StringBuilder(256);
			for (i = 0; i <= kword; i++) {
				String[] temporary = string[i].split("/");
				if (temporary[0].contains("、") || temporary[0].contains("：") || temporary[0].contains("了")) {
					ErrorN = 1;
					break;
				}
				if (!temporary[0].contains("\\n") && !temporary[0].contains("\n") && !temporary[0].contains("  ")
						&& !temporary[0].contains("\"") && !temporary[0].contains(" ") && !temporary[0].contains("年")
						&& !temporary[0].contains("关于") && !temporary[0].contains("招标人") && !temporary[0].contains("《")
						&& !temporary[0].contains("号") && !temporary[0].contains("0") && !temporary[0].contains("1")
						&& !temporary[0].contains("2") && !temporary[0].contains("3") && !temporary[0].contains("4")
						&& !temporary[0].contains("5") && !temporary[0].contains("6") && !temporary[0].contains("7")
						&& !temporary[0].contains("8") && !temporary[0].contains("9") && !temporary[0].contains("g"))
					sb.append(temporary[0]);
				// LOG.debug(temporary[0]);
			}
			String sa = sb.toString();
			if (ErrorN == 0 && StringUtils.isNotBlank(sa) && !StringUtils.contains(sa, "我")
					&& !StringUtils.contains(sa, "预算") && !StringUtils.contains(sa, ";")
					&& !StringUtils.contains(sa, "已") && !StringUtils.contains(sa, "第一期")
					&& !StringUtils.contains(sa, "第二期") && !StringUtils.contains(sa, "采购"))
				return sa;
		}
		if (StringUtils.isBlank(p_a) || StringUtils.contains(p_a, "代理") || StringUtils.contains(p_a, "供应")) {
			LOG.debug("123");
			p_a = RegexUtils.regex("受([0-9\u4e00-\u9fa5\\（\\）]+)委托", text, 1);
			if (!StringUtils.isBlank(p_a) || StringUtils.contains(p_a, "代理") || StringUtils.contains(p_a, "供应"))
				return p_a;
		}
		if ((StringUtils.isBlank(p_a) || StringUtils.contains(p_a, "代理") || StringUtils.contains(p_a, "供应"))
				&& StringUtils.contains(text, "北京市黄庄职业高中"))
			p_a = "北京市黄庄职业高中";
		if (StringUtils.isBlank(p_a) || StringUtils.contains(p_a, "代理") || StringUtils.contains(p_a, "供应")) {
			LOG.debug("1234");
			p_a = RegexUtils.regex("(?:名称):([0-9\u4e00-\u9fa5\\（\\）]+)", text, 1);
			if (!StringUtils.contains(p_a, "咨询") && !StringUtils.contains(p_a, "代理") && !StringUtils.contains(p_a, "项目")
					&& !StringUtils.contains(p_a, "采购") && !StringUtils.contains(p_a, "招标")
					&& !StringUtils.contains(p_a, "服务") && !StringUtils.contains(p_a, "展览"))
				return p_a;
		}
		return null;
	}
}
