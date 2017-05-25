package org.yansou.ci.storage.ciimp;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.yansou.ci.common.utils.JSONArrayHandler;
import org.yansou.ci.common.utils.JSONUtils;
import org.yansou.ci.common.utils.RegexUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class RCCReport implements Runnable {
	final static private Logger LOG = Logger.getLogger(RCCReport.class.getName());

	final static private String[] provs = { "北京市", "天津市", "上海市", "重庆市", "河北省", "河南省", "云南省", "辽宁省", "黑龙江省", "湖南省",
			"安徽省", "山东省", "新疆维吾尔", "江苏省", "浙江省", "江西省", "湖北省", "广西壮族", "甘肃省", "山西省", "内蒙古", "陕西省", "吉林省", "福建省", "贵州省",
			"广东省", "青海省", "西藏", "四川省", "宁夏回族", "海南省", "台湾省", "香港特别行政区", "澳门特别行政区" };
	final static private Set<String> provSet = Sets.newHashSet(provs);
	static private String WHERE = " where pub_time>='2017-04-01' and pub_time<='2017-04-30' and project_name like '%光伏%'";
	final private QueryRunner qr;
	private int rowIndex = 1;

	public RCCReport(DataSource ds) {
		this.qr = new QueryRunner(ds);
	}

	private final Map<String, String> cityMap = Maps.newLinkedHashMap();

	String getContext(String rowkey) {
		try {
			return Optional
					.ofNullable(qr.query("select rowkey,page_source from tab_rcc_source where rowkey=?",
							new MapHandler(), rowkey))
					.filter(Objects::nonNull).map(m -> (String) m.get("page_source")).orElse("");
		} catch (SQLException e) {
			LOG.log(Level.FINE, e.getMessage(), e);
		}
		return "";
	}

	@Override
	public void run() {
		try {
			JSONArray arr = qr
					.query("SELECT rcc.id,rcc.rowkey,rcc.project_name,rcc.investment_amounts,rcc.project_number,rcc.project_type,rcc.project_stage,rcc.proprietor,rcc.designing_institute,rcc.unit_construction,rcc.electrical_engineer,rcc.url FROM tab_rcc_project AS rcc "
							+ WHERE, new JSONArrayHandler());
			System.out.println("读取完毕，总条数：" + arr.size());
			Map<Object, List<JSONObject>> by = arr.stream().map(obj -> ((JSONObject) obj))
					.collect(Collectors.groupingBy(o -> ((JSONObject) o).getLong("project_number")));
			by.values().stream().map(this::toInfo).forEach(System.out::println);

		} catch (SQLException e) {
			LOG.log(Level.INFO, e.getMessage(), e);
		}
	}

	private String[] toInfo(List<JSONObject> objs) {
		List<JSONObject> list = objs.stream().sorted((a, b) -> b.getString("rowkey").compareTo(a.getString("rowkey")))
				.collect(Collectors.toList());
		JSONObject obj = list.get(0);
		JSONObject lastObj = new JSONObject();
		if (objs.size() > 1) {
			lastObj = list.get(1);
		}
		String ctx = getContext(obj.getString("rowkey"));
		// 项目名
		String project_name = obj.getString("project_name");
		// 那个省份的
		String province = "";
		province = getProvince(obj);
		if (StringUtils.isBlank(province)) {
			System.out.println("province in context");
			province = getProvinceInContext(ctx);
		}
		// 规模，多少兆瓦
		String scale = getMW(obj, ctx);
		// 项目阶段
		String phase = obj.getString("project_stage");
		// 项目购置情况
		String purchasing = RegexUtils.regex("设备购置情况[:：](.{5,80})", ctx.replaceAll("<[^>]*>", ""), 1);
		// 业主
		String owner = getCellValue(obj.getString("proprietor"));
		// 设计师
		String designer = getCellValue(obj.getString("designing_institute"));
		String lostphase = lastObj.getString("project_stage");
		// 状态及更新情况
		String statusAndUpdate = "没有更新";
		String time = obj.getString("rowkey").replaceAll("^\\d+:", "");
		String lastTime = null;

		if (Objects.nonNull(lastObj) && !lastObj.isEmpty()) {
			lastTime = lastObj.getString("rowkey").replaceAll("^\\d+::", "");
		}
		if (StringUtils.isNoneBlank(phase, lostphase) && !StringUtils.equals(phase, lostphase)) {
			statusAndUpdate = lostphase + " 更新为 " + phase + "\n";
			statusAndUpdate += lastTime + "->" + time;
		}

		return new String[] { project_name, time, province, scale, phase, purchasing, owner, designer,
				statusAndUpdate };
	}

	private String getMW(JSONObject obj, String ctx) {
		String regex = "(?is)([0-9,.]+)\\s*[M兆][瓦W]";
		String peoject_name = obj.getString("project_name");
		String mw = RegexUtils.regex(regex, peoject_name, 1);

		if (StringUtils.isNotBlank(mw)) {
			return mw.replaceAll(",", "");
		}
		System.out.println("scale in context");
		return RegexUtils.regex(regex, ctx, 1);
	}

	private String getProvince(JSONObject obj) {
		if (cityMap.isEmpty()) {
			try {
				List<JSONObject> cityList = JSONUtils
						.streamJSONObject(qr.query("select * from tab_city where style=0", new JSONArrayHandler()))
						.collect(Collectors.toList());
				for (JSONObject city : cityList) {
					String cityName = city.getString("name");
					long pid = city.getLongValue("pid");
					String pCityName = cityList.stream().filter(c -> c.getLongValue("id") == pid)
							.map(c -> c.getString("name")).findFirst().orElse("");
					cityMap.put(cityName.replaceAll("\\s+", "").replaceAll("[省市县区]", ""),
							pCityName.replaceAll("\\s+", ""));
				}
				// 一共六个特殊情况，循环引用。手动指定到省。
				cityMap.put("大理", "云南省");
				cityMap.put("昌都", "西藏");
				cityMap.put("日喀则", "西藏");
				cityMap.put("那曲", "西藏");
				cityMap.put("林芝", "西藏");
				cityMap.put("钓鱼岛", "台湾省");

				for (String key : cityMap.keySet()) {
					if (StringUtils.equals(key, cityMap.get(key))) {
						System.out.println("键值相同:" + key);
					}
				}
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}
		String props[] = { obj.getString("project_name") };
		for (String prop : props) {
			for (String cityName : cityMap.keySet()) {
				if (StringUtils.contains(prop, cityName)) {
					for (String prov : provs) {
						if (StringUtils.contains(prov, cityName)) {
							return prov;
						}
					}
					while (true) {
						cityName = cityMap.get(cityName);
						if (StringUtils.isBlank(cityName)) {
							break;
						}
						if (provSet.contains(cityName)) {
							return cityName;
						}

					}

				}
			}
		}
		return "";
	}

	private String getProvinceInContext(String ctx) {
		String res = "";
		int maxCount = 0;
		int zzIndex = 0;
		for (String prov : provs) {
			int count = StringUtils.countMatches(ctx, prov);
			int index = StringUtils.indexOf(ctx, prov);
			if (count > maxCount) {
				res = prov;
				maxCount = count;
			} else if (index < zzIndex) {
				res = prov;
				zzIndex = index;
			}
		}

		return res;
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

	public static void main(String[] args) throws IOException {
		if (args.length > 0) {
			File whereFile = new File(args[0]);
			System.out.println(whereFile.getCanonicalPath());
			WHERE = FileUtils.readFileToString(whereFile, "GBK");
			System.out.println("查询条件:" + WHERE);
		} else {
			WHERE = "";
			System.out.println("没有查询条件");
		}
		MysqlDataSource ds = new MysqlDataSource();
		ds.setURL(
				"jdbc:mysql://biaoshuking.mysql.rds.aliyuncs.com:3306/hngp?useUnicode=true&rewriteBatchedStatements=true&characterEncoding=utf-8");
		ds.setUser("hngp");
		ds.setPassword("hngp123");
		new RCCReport(ds).run();
	}
}
