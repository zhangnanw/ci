package wang.biaoshu.analyzer.nlpir.impl;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.LongStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.primitives.Longs;

import wang.biaoshu.analyzer.Analyzer;
import wang.biaoshu.analyzer.util.ErrUtils;
import wang.biaoshu.analyzer.util.Readability;
import wang.biaoshu.analyzer.util.RegexUtils;
import wang.biaoshu.analyzer.util.TableScanSikpRC;
import wang.biaoshu.analyzer.util.TextTools;

/**
 * 抽取预算,中标金额
 * 
 * @author zhang
 *
 */
final public class AmountAnalyzer implements Analyzer {

	static final Logger LOG = LoggerFactory.getLogger(AmountAnalyzer.class);

	private static final void addRegexMapTop(List<String> list, String regex) {
		list.add(regex + ".{1,7}?(?<unit>[千万亿元￥]+).{0,7}?(?<num>[0-9,.]+)");
		list.add(regex + ".{1,7}?(?<num>[0-9,.]+).{0,7}?(?<unit>[千万亿元￥]+)");
	}

	@Override
	final public boolean match(JSONObject obj) {
		return true;
	}

	final static public List<String> PRICE_WORD = Lists.newArrayList("金额", "资金", "价格", "价");
	final static public List<String> BID_AMOUNT_WORD = Lists.newArrayList("中标", "成交");

	final static String NUM_TOP = "(?<num>[0-9,.]+).{0,5}?(?<unit>[百千万亿元￥]{0,3})";
	final static String UNIT_TOP = "(?<unit>[百千万亿元￥]{0,3}).{0,5}?(?<num>[0-9,.]+)";
	/**
	 * 匹配价格单位为后缀的正则
	 */
	final static String SUFFIX_AOMUNT_PATTERN = "(?<price>[0-9,.]+.{0,3}[千万亿元￥]*?|[千万亿元￥]*?.{0,3}[0-9,.]+)";
	final static List<String> budget_regex_list = Lists.newArrayList("[预概]算.{0,7}?" + SUFFIX_AOMUNT_PATTERN,
			"预算总?金额.{0,7}?(?<price>[千万亿元￥]元.{0,6}?[0-9,.]+)", "投资(?:额及来源)?.{0,5}?" + SUFFIX_AOMUNT_PATTERN,
			"批复金额.{0,4}?" + SUFFIX_AOMUNT_PATTERN, "控制(?:金额|价格?).{0,4}?" + SUFFIX_AOMUNT_PATTERN,
			"项目估算总投资.{0,4}?" + SUFFIX_AOMUNT_PATTERN, "招标控?制?价为?.{0,4}?" + SUFFIX_AOMUNT_PATTERN,
			"工程暂?估价.{0,4}?" + SUFFIX_AOMUNT_PATTERN, "投资总额.{0,19}?(?<price>[0-9,.]+.{0,3}?[万元]+)",
			"最高限价.{1,10}?" + SUFFIX_AOMUNT_PATTERN, "项目概算.{1,10}?" + SUFFIX_AOMUNT_PATTERN);
	final static List<String> budget_regex_list_map = Lists.newArrayList(
			"(?:预算).{0,5}?(?:金额)?.{0,7}?(?<num>[0-9,.]+)\\s*(?<unit>[千万亿元￥])",
			"(?:预算).{0,5}?(?:金额)?.{0,7}?(?<unit>[千万亿元￥]).{0,7}?(?<num>[0-9,.]+)",
			"预算金额\\s*[:：;]\\s*(?<num>[0-9,.]+).{0,5}?(?<unit>[千万亿元￥]?)", "财政批复(?:资金|金额).{0,15}?" + NUM_TOP,
			"财政批复(?:资金|金额).{0,15}?" + UNIT_TOP, "暂估价.{0,8}?" + NUM_TOP, "暂估价.{0,8}?" + UNIT_TOP,
			"控制(?:资金|金额|价格?).{0,8}?" + NUM_TOP, "控制(?:资金|金额|价格?).{0,8}?" + UNIT_TOP, "资金来源.{0,8}?" + NUM_TOP,
			"资金来源.{0,8}?" + UNIT_TOP);
	final static List<String> bid_amount_regex_list = Arrays.asList(
			"(?is)(?:中标|成交|合同).{0,5}?金额.{1,3}?\\pP.{1,7}?" + SUFFIX_AOMUNT_PATTERN,
			"(?:中标).{0,5}?总报价.{1,7}?" + SUFFIX_AOMUNT_PATTERN,
			"(?is)合同总?金额.{0,3}?[\\pP].{1,7}?(?<price>[0-9,.]+.{0,3}[万元]+?)",
			"(?:中标结果).{1,7}?(?<price>[0-9,.]+.{0,3}[万元]+?)");

	/**
	 * 直接采用捕获组名
	 */
	final static List<String> bid_amount_regex_list_map = Lists.newArrayList(
			"(?:中标|成交).{0,5}?金额.{0,7}?(?<num>[0-9,.]+).{0,7}?(?<unit>[千万亿元￥])",
			"(?:中标|成交).{0,5}?金额.{0,7}?(?<unit>[千万亿元￥]).{0,7}?(?<num>[0-9,.]+)",
			"(?:中标|成交)金额\\s*[:：;]\\s*(?<num>[0-9,.]+).{0,4}?(?<unit>[千万亿元￥]?)", "中标单?价(?:总和)?.{0,8}?" + NUM_TOP,
			"中标单?价(?:总和)?.{0,8}?" + UNIT_TOP, "最终报价.{1,7}?(?<unit>[千万亿元￥]+).{0,7}?(?<num>[0-9,.]+)",
			"最终报价.{1,7}?(?<num>[0-9,.]+).{0,7}?(?<unit>[千万亿元￥]+)", "成交结果.{1,3}?(?<num>[0-9,.]+)");
	static {
		addRegexMapTop(bid_amount_regex_list_map, "成交价");
		addRegexMapTop(bid_amount_regex_list_map, "中标总价");
		addRegexMapTop(bid_amount_regex_list_map, "总报价");
		addRegexMapTop(budget_regex_list_map, "总投资");
	}

	// 按照公告类型算的金额
	final static List<String> price_regex_list = Lists.newArrayList("小[計计].{0,5}?" + SUFFIX_AOMUNT_PATTERN,
			RegexUtils.NUM_CHAR + "{1,2}\\s*包.{0,5}?" + SUFFIX_AOMUNT_PATTERN, "项目总?金额.{0,6}?" + SUFFIX_AOMUNT_PATTERN,
			RegexUtils.NUM_CHAR + "{1,2}\\s*包[^;；]{0,20}?" + SUFFIX_AOMUNT_PATTERN);
	// final static List<String> price_regex_list_map =
	// Lists.newArrayList("(金额|总价).{0,8}?" + NUM_TOP,
	// "(金额|总价).{0,8}?" + UNIT_TOP);
	final static List<String> price_regex_list_map = Lists.newArrayList();
	static {
		// 统一修改正则表达式状态（不区分大小写，英文句号可以匹配换行）
		String top = "(?is)";
		_addRegexTop(budget_regex_list, top);
		_addRegexTop(budget_regex_list_map, top);
		_addRegexTop(bid_amount_regex_list, top);
		_addRegexTop(bid_amount_regex_list_map, top);
		_addRegexTop(price_regex_list, top);
	}

	final static void regex1(JSONObject res, String key, String context, List<String> regexList) {
		for (String regex : regexList) {
			List<String> valList = RegexUtils.regexList(regex, context, "price");
			long[] jiageArr = valList.stream().filter(txt -> StringUtils.isNotBlank(txt.replace(',', ' ')))
					.mapToLong(AmountAnalyzer::textToPrice).toArray();
			Long price = checkMax(jiageArr);
			if (null != price) {
				setPrice(res, key, price);
			} else {
				price = LongStream.of(jiageArr).filter(p -> p > 0).reduce((a, b) -> a + b).orElse(0);
				if (0 < price) {
					setPrice(res, key, price);
				}
			}
			if (LOG.isDebugEnabled()) {
				if (null != price && price > 0) {
					LOG.info("1,TYPE:{},PRICE:{},REGEX:{}", key, price, regex);
				}
			}
		}
	}

	final static void regexMap(JSONObject res, String key, String context, List<String> regexList) {
		for (String regex : regexList) {
			List<Map<String, String>> valList = RegexUtils.regexToListMap(regex, context);
			long[] jiageArr = valList.stream().mapToLong(map -> parsePrice(map.get("num"), map.get("unit"))).toArray();
			Long price = checkMax(jiageArr);
			if (null != price) {
				setPrice(res, key, price);
			} else {
				price = LongStream.of(jiageArr).reduce((a, b) -> a + b).orElse(0);
				if (0 < price) {
					setPrice(res, key, price);
				}
			}
			if (LOG.isDebugEnabled()) {
				if (null != price && price > 0) {
					LOG.info("MAP,TYPE:{},PRICE:{},REGEX:{}", key, price, regex);
				}
			}
		}
	}

	final static long textToPrice(String text) {
		// if (text.matches("[一二三四五六七八九十廿卅十百千壹贰叁肆伍陆柒捌玖拾佰仟万亿元圆角分零整]{4,}")) {
		// System.out.println("发现中文数字");
		// System.out.println(text);
		// }
		Pattern[] YuanRegexs = { Pattern.compile("(?<num>[0-9,.]+).{0,5}?(?<unit>[千万亿元￥])"),
				Pattern.compile("(?<unit>[千万亿元￥]).{0,5}?[￥元].{0,5}?(?<num>[0-9,.]+)"),
				Pattern.compile("(?<unit>￥).{0,3}?(?<num>[0-9,.]+)") };
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
			return 0;
		}
		return list.stream().mapToLong(a -> a).max().getAsLong();
	}

	/**
	 * 按照行处理预算资金
	 * 
	 * @param res
	 * @param context
	 */
	final static void readLineBudget(JSONObject res, String context) {
		context = context.replaceAll("(?<=预算(?:资金|金额))[:；：;]", "\n");
		context = context.replaceAll("\n+", "\n");
		StringReader sr = new StringReader(context);
		try {
			List<String> list = IOUtils.readLines(sr);
			boolean isBud = false;
			long price = 0;
			for (int i = 0; i < list.size(); i++) {
				String line = list.get(i);
				if (line.matches(".{0,4}预算(?:" + PRICE_WORD.stream().reduce((a, b) -> a + '|' + b) + ")?.{0,4}")) {
					isBud = true;
					continue;
				}
				if (isBud) {
					long tPrice = textToPrice(line);
					if (tPrice <= 0) {
						isBud = false;
					} else {
						price += tPrice;
					}
				}
			}
			setPrice(res, "budget", price);
		} catch (IOException e) {
			LOG.info(e.getMessage());
		}
	}

	/**
	 * 按行处理中标价格
	 * 
	 * @param res
	 * @param context
	 */
	final static void readLineBid_amount(JSONObject res, String context) {
		context = context.replaceAll("(?<=(?:中标|成交)(?:资金|金额|价格?))[:；：]", "\n");
		context = context.replaceAll("[；;]", "\n");
		context = context.replaceAll("\n+", "\n");
		StringReader sr = new StringReader(context);
		try {
			List<String> list = IOUtils.readLines(sr);
			boolean isBud = false;
			long price = 0;
			for (int i = 0; i < list.size(); i++) {
				String line = list.get(i);
				if (line.matches(
						".{0,4}(?:中标|成交)(?:" + PRICE_WORD.stream().reduce((a, b) -> a + '|' + b) + ")?.{0,4}")) {
					isBud = true;
					continue;
				}
				if (isBud) {
					long tPrice = textToPrice(line);
					if (tPrice <= 0) {
						isBud = false;
					} else {
						price += tPrice;
					}
				}
			}
			setPrice(res, "bid_amount", price);
		} catch (IOException e) {
			LOG.info(e.getMessage());
		}
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

			long num = new BigDecimal(numStr).multiply(new BigDecimal(100)).longValue();
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

	final static Long checkMax(long[] aArr) {
		if (aArr.length <= 1) {
			return null;
		}
		long max = Longs.max(aArr);
		List<Long> list = Lists.newArrayList(Longs.asList(aArr));
		list.remove(Long.valueOf(max));
		double heji = list.stream().mapToLong(a -> a).reduce((a, b) -> a + b).orElse(0);
		double bil = max / heji;
		if (bil > 0.99 && bil < 1.01) {
			return max;
		}
		return null;
	}

	@Override
	final public JSONObject analy(JSONObject obj) {
		JSONObject res = new JSONObject();
		String title = obj.getString("title");
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
		boolean isBudget = true;
		boolean isBid_amount = true;
		for (String context : contextList) {
			context = clearInterfere(context);
			LOG.debug(context);
			if (isBudget) {
				regex1(res, "budget", context, budget_regex_list);
				regexMap(res, "budget", context, budget_regex_list_map);
			}
			if (isBid_amount) {
				regex1(res, "bid_amount", context, bid_amount_regex_list);
				regexMap(res, "bid_amount", context, bid_amount_regex_list_map);
			}
			// 如果没能获取到响应的价格，判断公告类型，来决定是公告是预算还是中标金额。
			if (StringUtils.contains(title, "中标公告") || StringUtils.contains(title, "结果公告")
					|| StringUtils.contains(title, "成交公告")) {
				if (StringUtils.isBlank(res.getString("budget")) & isBid_amount
						&& StringUtils.isBlank(res.getString("bid_amount"))) {
					regex1(res, "bid_amount", context, price_regex_list);
					regexMap(res, "bid_amount", context, price_regex_list_map);
				}
			} else if (StringUtils.contains(title, "招标公告") || StringUtils.contains(title, "采购公告")) {
				if (isBudget && StringUtils.isBlank(res.getString("budget"))) {
					regex1(res, "budget", context, price_regex_list);
					regexMap(res, "budget", context, price_regex_list_map);
				}
			}
			// 如果还没能获得价格按行处理，来解析价格
			if (isBudget && StringUtils.isBlank(res.getString("budget"))) {
				readLineBudget(res, context);
			}
			if (isBid_amount && StringUtils.isBlank(res.getString("bid_amount"))) {
				readLineBid_amount(res, context);
			}
			if (StringUtils.isNotBlank(res.getString("budget"))) {
				isBudget = false;
			}
			if (StringUtils.isNotBlank(res.getString("bid_amount"))) {
				isBid_amount = false;
			}
		}
		checkErr(obj, res);
		return res;
	}

	private void checkErr(JSONObject obj, JSONObject res) {
		long budget = res.getLongValue("budget");
		long bid_amount = res.getLongValue("bid_amount");
		long price = LongStream.of(budget, bid_amount).max().getAsLong();
		ErrUtils.checkAdd(obj, bid_amount > 0 && budget > 0 && bid_amount > budget, "中标价格大于预算",
				WeightZH.PRICE_ABOVE_BUDGET);
		ErrUtils.checkAdd(obj, price > 100000000, "价格上亿", WeightZH.PRICE_1E);
		ErrUtils.checkAdd(obj, price > 10000000 && price < 100000000, "价格上千万", WeightZH.PRICE_1QW);
	}

	/**
	 * 清除干扰
	 * 
	 * @param context
	 * @return
	 */
	private String clearInterfere(String context) {
		context = context.replaceAll("[ \t]", "");
		context = context.replaceAll("[大小]写", "");
		context = context.replaceAll(":+", ":");
		return context;
	}

	/**
	 * 删除中文价格
	 * 
	 * @param text
	 * @return
	 */
	final static private String removeChPrice(String text) {
		text = text.replaceAll("[一二三四五六七八九十廿卅十百千壹贰叁肆伍陆柒捌玖拾佰仟万亿元圆角分零整]{5,}", "");
		text = text.replaceAll("[大小]写", "");
		text = text.replace("人民币", "");
		text = text.replaceAll("[:：]+", ":");
		// text = text.replaceAll("[（）(){}【】《》<>]\\s*元\\s*[（）(){}【】《》<>]", "");
		text = text.replaceAll("[（）(){}【】《》<>]", "");
		return text;
	}

	/**
	 * 处理中间带空格的单词
	 * 
	 * @param context
	 * @return
	 */
	private String wordFilter(String context) {
		context = context.replaceAll("中\\s*标\\s*金\\s*额", "中标金额");
		context = context.replaceAll("预\\s*算\\s*金\\s*额", "预算金额");
		context = context.replaceAll("金\\s*额", "金额");
		return context;
	}

	final static private void setPrice(JSONObject res, String key, long price) {
		if (price <= 10000) {
			return;
		}
		Long oldPrice = res.getLong(key);
		if (null != oldPrice && oldPrice > price) {
			return;
		}
		res.put(key, price);
	}

	private final static void _addRegexTop(List<String> regexList, String top) {
		for (int i = 0; i < regexList.size(); i++) {
			regexList.set(i, top + regexList.get(i));
		}
	}

}
