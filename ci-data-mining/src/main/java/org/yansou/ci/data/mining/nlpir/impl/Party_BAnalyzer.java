package org.yansou.ci.data.mining.nlpir.impl;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yansou.ci.data.mining.Analyzer;
import org.yansou.ci.data.mining.ansj.Ansj;
import org.yansou.ci.data.mining.util.ErrUtils;
import org.yansou.ci.data.mining.util.Readability;
import org.yansou.ci.data.mining.util.RegexUtils;
import org.yansou.ci.data.mining.util.TableScan;
import org.yansou.ci.data.mining.util.TableScanSikpRC;
import org.yansou.ci.data.mining.util.TextTools;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

/**
 * 
 * @author LENOVO
 *
 */

public class Party_BAnalyzer implements Analyzer {

	private static final Logger LOG = LoggerFactory.getLogger(Party_BAnalyzer.class);
	final static public List<String> PRICE_WORD = Lists.newArrayList("金额", "资金", "价格", "价", "单价");

	@Override
	public boolean match(JSONObject obj) {
		return true;
	}

	final static String NUM_TOP = "(?<num>[0-9,.]+).{0,5}?(?<unit>[百千万亿元￥]{0,3})";
	final static String UNIT_TOP = "(?<unit>[百千万亿元￥]{0,3}).{0,5}?(?<num>[0-9,.]+)";
	final static String SUFFIX_AOMUNT_PATTERN = "(?<price>[0-9,.]+.{0,3}[千万亿元￥]*?|[千万亿元￥]*?.{0,3}[0-9,.]+)";
	final static List<String> price_regex_list = Lists.newArrayList("小[計计].{0,5}?" + SUFFIX_AOMUNT_PATTERN,
			RegexUtils.NUM_CHAR + "{1,2}\\s*包.{0,5}?" + SUFFIX_AOMUNT_PATTERN, "项目总?金额.{0,6}?" + SUFFIX_AOMUNT_PATTERN,
			RegexUtils.NUM_CHAR + "{1,2}\\s*包[^;；]{0,20}?" + SUFFIX_AOMUNT_PATTERN);

	private static final void addRegexMapTop(List<String> list, String regex) {
		list.add(regex + ".{1,7}?(?<unit>[千万亿元￥]+).{0,7}?(?<num>[0-9,.]+)");
		list.add(regex + ".{1,7}?(?<num>[0-9,.]+).{0,7}?(?<unit>[千万亿元￥]+)");
	}

	final static List<String> bid_amount_regex_list = Arrays.asList(
			"(?is)(?:中标|成交|合同).{0,5}?金额.{1,3}?\\pP.{1,7}?" + SUFFIX_AOMUNT_PATTERN,
			"(?:中标).{0,5}?总报价.{1,7}?" + SUFFIX_AOMUNT_PATTERN,
			"(?is)合同总?金额.{0,3}?[\\pP].{1,7}?(?<price>[0-9,.]+.{0,3}[万元]+?)",
			"(?:中标结果).{1,7}?(?<price>[0-9,.]+.{0,3}[万元]+?)");
	final static List<String> bid_amount_regex_list_map = Lists.newArrayList(
			"(?:中标|成交).{0,5}?金额.{0,7}?(?<num>[0-9,.]+).{0,7}?(?<unit>[千万亿元￥])",
			"(?:中标|成交).{0,5}?金额.{0,7}?(?<unit>[千万亿元￥]).{0,7}?(?<num>[0-9,.]+)",
			"(?:中标|成交)金额\\s*[:：;]\\s*(?<num>[0-9,.]+).{0,4}?(?<unit>[千万亿元￥]?)", "中标单?价(?:总和)?.{0,8}?" + NUM_TOP,
			"中标单?价(?:总和)?.{0,8}?" + UNIT_TOP, "最终报价.{1,7}?(?<unit>[千万亿元￥]+).{0,7}?(?<num>[0-9,.]+)",
			"最终报价.{1,7}?(?<num>[0-9,.]+).{0,7}?(?<unit>[千万亿元￥]+)");
	static {
		addRegexMapTop(bid_amount_regex_list_map, "成交价");
		addRegexMapTop(bid_amount_regex_list_map, "中标总价");
	}
	final static List<String> price_regex_list_map = Lists.newArrayList();
	static {
		// 统一修改正则表达式状态（不区分大小写，英文句号可以匹配换行）
		String top = "(?is)";
		_addRegexTop(bid_amount_regex_list, top);
		_addRegexTop(bid_amount_regex_list_map, top);
		_addRegexTop(price_regex_list, top);
	}

	@Override
	public JSONObject analy(JSONObject obj) {
		String err = new String();
		JSONObject res = new JSONObject();
		String sourceCtx = obj.getString("context");
		// 将控制符羊符号，换证中文羊符号。
		sourceCtx = sourceCtx.replace((char) 165, (char) 65509);
		// 将表格转换为文本追加在Html后面。
		String tableStr = TableScanSikpRC.filter(sourceCtx);
		tableStr = TextTools.normalize(tableStr);
		tableStr = wordFilter(tableStr);
		tableStr = removeChPrice(tableStr);
		String abilityCtx = sourceCtx;
		Readability read = new Readability(abilityCtx);
		read.init();
		abilityCtx = read.outerHtml();
		abilityCtx = TextTools.normalize(abilityCtx);
		abilityCtx = wordFilter(abilityCtx);
		abilityCtx = removeChPrice(abilityCtx);
		String ctx = sourceCtx;
		// 将html格式化。
		ctx = TextTools.normalize(ctx);
		ctx = wordFilter(ctx);
		ctx = removeChPrice(ctx);
		List<String> contextList = Lists.newArrayList(abilityCtx, ctx, tableStr);
		String text = obj.getString("context");
		text += TableScan.filter(text);
		text = TextTools.normalize(text);
		text = removeChPrice(text);
		text = clearInterfere(text);
		if (StringUtils.contains(text, "制造商："))
			ErrUtils.checkAdd(obj, true, "存在非甲乙丙三方的机构名", 256);
		LOG.debug(text);
		long[] Number = null;
		for (String context : contextList) {
			context = clearInterfere(context);
			Number = regex1(res, "bid_amount", context, bid_amount_regex_list);
			if (Number.length > 0 && Number[0] > 0) {
				break;
			}
			Number = regexMap(res, "bid_amount", context, bid_amount_regex_list_map);
			String title = obj.getString("title");
			LOG.debug("1");
			if (Number.length > 0 && Number[0] > 0) {
				break;
			}
			if (StringUtils.contains(title, "中标公告") || StringUtils.contains(title, "结果公告")
					|| StringUtils.contains(title, "成交公告")) {
				Number = regex1(res, "bid_amount", context, price_regex_list);
				LOG.debug("2");
				if (Number == null)
					Number = regexMap(res, "bid_amount", context, price_regex_list_map);
				LOG.debug("3");
			}
			if (Number.length > 0 && Number[0] > 0) {
				break;
			}
			Number = readLineBid_amount(res, text);
		}
		// String fch = Ansj.getInstance().raragraphProcess(text);
		// LOG.debug(fch);|
		String tbb = "";
		try {
			String sb = "";// 分出的词
			String fch = Ansj.getInstance().raragraphProcess(text);
			LOG.debug(fch);
			// String sc="";//拼接后的公司
			String sf = "";// 包含金额的公司+金额
			String[] se = new String[20];
			for (int i = 0; i < 20; i++) {
				se[i] = "";// 将每个sc存储
			}
			int k = 0;
			// StringBuilder sc = new StringBuilder();;
			String[] string;
			string = fch.split(" ");
			int startmark = -1;
			int endmark = -1;
			for (int i = 0; i < string.length; i++) {
				if (string[i].contains("中标") || string[i].contains("候选") || string[i].contains("包")
						|| string[i].contains("合格") || string[i].contains("商")) {
					for (int j = i; j < string.length; j++) {
						if ((string[j].contains("戴尔") || string[j].contains("省")
								|| (string[j].contains("市") && !string[j].contains("市政")) || string[j].contains("ns")
								|| string[j].contains("济南") || string[j].contains("/ns")
								|| (string[j].contains("nr") && !string[j].contains("陆") && !string[j].contains("元")
										&& !string[j].contains("伍"))
								|| string[j].contains("nz") || string[j].contains("中央") || string[j].contains("国家")
								|| string[j].contains("航天")) && startmark < 0) {
							LOG.debug("123456");
							startmark = j;
						}
						if (string[j].contains("公司") && (j - startmark < 15)) {
							LOG.debug("公司");
							endmark = j;
							if (endmark > 0 && startmark > 0) {
								for (int i1 = startmark; i1 <= endmark && i1 < string.length; i1++) {
									String[] temporary = string[i1].split("/");
									try {
										sb = sb + temporary[0];
										LOG.debug(temporary[0]);
									} catch (Exception e) {
									}
								}
								String sd = Party_AAnalyzer.sa(text);
								if (!StringUtils.contains(sb, sd) && StringUtils.isNotBlank(sb) && !sb.contains("采购")
										&& !sb.contains("6") && !sb.contains("按照") && !sb.contains("号")
										&& !sb.contains("招标") && !sb.contains("1") && !sb.contains("服务")
										&& !sb.contains("咨询") && !sb.contains("交易") && !sb.contains("代理")
										&& !sb.contains("供应") && !sb.contains("法") && !sb.contains("无")
										&& !sb.contains("电话")) {
									// sc=sc+sb+",";
									Preconditions.checkArgument(se.length>k);
									se[k] = sb + ":";
									LOG.debug(se[k]);
									k++;
								}
								sb = "";
							}
							startmark = -1;
							endmark = -1;
						}
					}
					break;
				}
			}
			int count = 0;
			LOG.debug(se[count]);
			while (StringUtils.isNotBlank(se[count]) && count < 20) {
				if (count < Number.length) {
					sf = sf + se[count] + Number[count] + ",";
					LOG.debug(sf);
				} else {
					sf = sf + se[count] + "NaN" + ",";
					LOG.debug(sf);
					AmountAnalyzer analyzer = new AmountAnalyzer();
					JSONObject resb = analyzer.analy(new JSONObject(obj));
					try {
						String bid_amount = resb.getString("bid_amount");
						if (StringUtils.isNotBlank(bid_amount)) {
							err = "乙方对应金额不足";
						}
					} catch (Exception e) {
					}
				}
				count++;
				try {
					if (StringUtils.isBlank(se[count])) {
						LOG.debug(sf);
						break;
					}
				} catch (Exception e) {
					LOG.debug(sf);
					break;
				}
			}
			LOG.debug(sf);
			try {
				sf = sf.replaceAll("[,，;',]$", "");
			} catch (Exception e) {
			}
			String str = Party_BAnalyzer.toJSONString(sf);
			if (StringUtils.isNotBlank(sf) && !StringUtils.contains(sf, "职业") && !StringUtils.contains(sf, "包")
					&& !StringUtils.contains(sf, "管理") && !StringUtils.contains(sf, "我公司")) {
				LOG.debug(str);
				res.put("party_b", str);
				ErrUtils.checkAdd(obj, true, err, 4);
				return res;
			}
			tbb = RegexUtils.regex(
					"(?:中标人名称|成交供应商|供应商名称|中标供应商名称|供应商|中标单位|中标单位名称|采购人确认中标人为|确认中标人为|确定成交供应商):([\u4e00-\u9fa5]+)", text,
					1);
			LOG.debug(tbb);
			if (StringUtils.contains(tbb, "包") || StringUtils.contains(tbb, "供应商") || StringUtils.contains(tbb, "无")
					|| StringUtils.contains(tbb, "委托")) {
				tbb = "";
			}
			LOG.debug("15");
			LOG.debug(tbb);
			Preconditions.checkArgument(Number.length > 0);
			if (Number[0] > 0 && StringUtils.isNotBlank(tbb))
				tbb = tbb + ":" + Number[0];
			else if (StringUtils.isNotBlank(tbb))
				tbb = tbb + ":" + "NaN";
			LOG.debug(tbb);
			try {
				if (tbb.contains("按照"))
					tbb = "";
			} catch (Exception e) {
			}
			if (StringUtils.isNotBlank(tbb)) {

				int countL = 0;
				try {

					while (countL < Number.length && Number[countL] > 0 && countL < 20) {
						countL++;
					}
					LOG.debug(tbb);
					if (countL > 1) {
						err = "乙方机构数量不完整";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				LOG.debug(tbb);
				String str1 = Party_BAnalyzer.toJSONString(tbb);
				LOG.debug(str1);
				LOG.debug("12");
				res.put("party_b", str1);
				if (StringUtils.isNotBlank(err))
					ErrUtils.checkAdd(obj, true, err, 1);
				return res;
			} else if (Number[0] > 0) {
				err = "乙方机构数量不完整";
				if (StringUtils.isNotBlank(err))
					ErrUtils.checkAdd(obj, true, err, 1);
			}
		} catch (Exception e) {
		}
		return res;
	}

	final static long[] readLineBid_amount(JSONObject res, String context) {
		context = context.replaceAll("(?<=(?:中标|成交|合同)(?:资金|金额|价格?))[:；：]", "\n");
		context = context.replaceAll("[；;]", "\n");
		context = context.replaceAll("\n+", "\n");
		StringReader sr = new StringReader(context);
		int j = 0;
		try {
			List<String> list = IOUtils.readLines(sr);
			boolean isBud = false;
			long[] tPrice = new long[list.size()];
			for (int i = 0; i < list.size(); i++) {
				String line = list.get(i);
				if (line.matches(
						".{0,6}(?:中标|成交|合同)(?:" + PRICE_WORD.stream().reduce((a, b) -> a + '|' + b) + ")?.{0,6}")) {
					isBud = true;
					continue;
				}
				if (isBud) {
					tPrice[j] = textToPrice(line);
					if (tPrice[j] <= 0) {
						isBud = false;
					} else {
						j++;
					}
				}
			}
			for (int g = j; g < list.size(); g++) {
				tPrice[g] = -1;
			}
			return tPrice;
		} catch (IOException e) {
		}
		return null;
	}

	final static long textToPrice(String text) {
		// if (text.matches("[一二三四五六七八九十廿卅十百千壹贰叁肆伍陆柒捌玖拾佰仟万亿元圆角分零整]{4,}")) {
		// System.out.println("发现中文数字");
		// System.out.println(text);
		// }
		Pattern[] YuanRegexs = { Pattern.compile("(?<num>[0-9,.]+).{0,5}?(?<unit>[千万亿元￥])"),
				Pattern.compile("(?<unit>[千万亿元￥]).{0,5}?元.{0,5}?(?<num>[0-9,.]+)") };
		// text = text.replaceAll("[^0-9\\.十百千万亿元￥拾佰仟]+", "");
		List<Long> list = Lists.newArrayList();
		for (Pattern pattern : YuanRegexs) {
			Matcher match = pattern.matcher(text);
			if (match.find()) {
				String numStr = match.group("num");
				String unit = match.group("unit");
				list.add(parsePrice(numStr, unit));
			}
		}
		if (list.isEmpty()) {
			return -1;
		}
		return list.stream().mapToLong(a -> a).max().getAsLong();
	}

	private String clearInterfere(String context) {
		context = context.replaceAll("[ \t]", "");
		context = context.replaceAll("[大小]写", "");
		context = context.replaceAll(":+", ":");
		return context;
	}

	final static long parsePrice(String numStr, String unit) {
		if (StringUtils.isBlank(unit)) {
			unit = StringUtils.EMPTY;
		}
		unit = unit.replaceAll("[元￥$]", "");
		numStr = numStr.replace(",", "").trim();
		if (numStr.trim().equals(".") || StringUtils.isBlank(numStr)) {
			return -1;
		}
		try {
			Double numf = Double.parseDouble(numStr);
			// 将单位从元变成分
			long num = Double.valueOf(numf * 100).longValue();
			switch (unit) {
			case "千":
				num *= 1000;
				break;
			case "万":
				num *= 10000;
				break;
			case "百万":
				num *= 1000000;
				break;
			case "千万":
				num *= 10000000;
				break;
			case "亿":
				num *= 100000000;
				break;
			default:
				break;
			}
			// 单位还原成元
			num /= 100;
			return num;
		} catch (Exception e) {
		}
		return -1;
	}

	final static private String removeChPrice(String text) {
		text = text.replaceAll("[一二三四五六七八九十廿卅十百千壹贰叁肆伍陆柒捌玖拾佰仟万亿元圆角分零整]{5,}", "");
		text = text.replaceAll("[大小]写", "");
		text = text.replaceAll("[一二三四五六七八九十]段标", "");
		try {
			text = text.replaceAll("[人民币", "");
			text = text.replaceAll("[（人民币）", "");
		} catch (Exception e) {
		}
		text = text.replaceAll("[:：]+", ":");
		return text;
	}

	final static String toJSONString(String str) {
		JSONArray jarr = new JSONArray();
		String[] arr = str.split("[，,]");
		for (String line : arr) {
			String[] st = line.split(":");
			String name = "";
			Long price = null;
			if (st.length > 0) {
				name = st[0];
			}
			if (st.length > 1 && StringUtils.isNumeric(st[1])) {
				price = Long.valueOf(st[1]);
			}
			JSONObject jobj = new JSONObject();
			if (StringUtils.isNoneBlank(name)) {
				if (null != price && price > 0)
					jobj.put(name, price);
				else
					jobj.put(name, "NaN");
			}
			jarr.add(jobj);
		}
		return jarr.toJSONString();
	}

	private String wordFilter(String context) {
		context = context.replaceAll("中\\s*标\\s*金\\s*额", "中标金额");
		context = context.replaceAll("预\\s*算\\s*金\\s*额", "预算金额");
		context = context.replaceAll("金\\s*额", "金额");
		return context;
	}

	final static long[] regex1(JSONObject res, String key, String context, List<String> regexList) {
		for (String regex : regexList) {
			List<String> valList = RegexUtils.regexList(regex, context, "price");
			long[] jiageArr = valList.stream().filter(txt -> StringUtils.isNotBlank(txt.replace(',', ' ')))
					.mapToLong(AmountAnalyzer::textToPrice).toArray();
			if (jiageArr != null)
				return jiageArr;
		}
		return null;
	}

	final static long[] regexMap(JSONObject res, String key, String context, List<String> regexList) {
		for (String regex : regexList) {
			List<Map<String, String>> valList = RegexUtils.regexToListMap(regex, context, "num", "unit");
			long[] jiageArr = valList.stream().mapToLong(map -> parsePrice(map.get("num"), map.get("unit"))).toArray();
			if (jiageArr != null)
				return jiageArr;
		}
		return null;
	}

	private final static void _addRegexTop(List<String> regexList, String top) {
		for (int i = 0; i < regexList.size(); i++) {
			regexList.set(i, top + regexList.get(i));
		}
	}
}
