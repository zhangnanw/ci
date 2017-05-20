package org.yansou.ci.data.mining.nlpir.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang3.StringUtils;
import org.yansou.ci.common.utils.JSONArrayHandler;
import org.yansou.ci.common.utils.JSONUtils;
import org.yansou.ci.data.mining.AResult;
import org.yansou.ci.data.mining.ATerm;
import org.yansou.ci.data.mining.Analysis;
import org.yansou.ci.data.mining.Analyzer;
import org.yansou.ci.data.mining.ansj.AnsjAnalysis;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 项目名
 * 
 * @author n.zhang
 *
 */
public class AreaAnalyzer implements Analyzer {
	private final Map<String, List<String>> cityMap = Maps.newLinkedHashMap();
	private Analysis analy;
	private final Map<String, String> quanming = Maps.newLinkedHashMap();

	public AreaAnalyzer() {
		MysqlDataSource ds = new MysqlDataSource();
		ds.setUrl("jdbc:mysql://biaoshuking.mysql.rds.aliyuncs.com:3306/hngp?useUnicode=true&characterEncoding=utf-8");
		ds.setUser("hngp");
		ds.setPassword("hngp123");
		QueryRunner qr = new QueryRunner(ds);
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
					pCityName = pCityName.replaceAll("\\s+", "");
					cityName = cityName.replaceAll("\\s+", "");
					List<String> pList = cityMap.get(cityName);
					if (null == pList) {
						pList = Lists.newArrayList(pCityName);
						cityMap.put(cityName, pList);
					} else {
						pList.add(pCityName);
					}
				}
				for (String city : Sets.newHashSet(cityMap.keySet())) {
					cityMap.put(city.replaceAll("[省市县区]$", ""), cityMap.get(city));
					quanming.put(city.replaceAll("[省市县区]$", ""), city);
				}
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}
		AnsjAnalysis analysis = new AnsjAnalysis(cityMap.keySet(), "ns");
		this.analy = analysis;
	}

	@Override
	public boolean match(JSONObject obj) {
		return true;
	}

	@Override
	public JSONObject analy(JSONObject obj) {
		JSONObject res = new JSONObject();
		String title = obj.getString("title");
		String anchorText = obj.getString("anchorText");
		String context = obj.getString("context");
		String province = obj.getString("province");
		String allText = title + anchorText + context;
		AResult result = analy.recognition(allText);
		List<ATerm> nsList = result.getTerms().stream().filter(at -> "ns".equals(at.getNature()))
				.collect(Collectors.toList());
		Map<String, List<ATerm>> nsMap = nsList.stream().collect(Collectors.groupingBy(at -> at.getWord()));
		// 第一次出现该地名的位置。
		Map<String, Integer> nsIndex = Maps.newHashMap();
		nsMap.forEach((name, atList) -> nsIndex.put(name,
				atList.stream().mapToInt(at -> at.getOffset()).min().orElse(Integer.MAX_VALUE)));
		// 区域序列，省-> 市-> 县
		List<List<String>> lineList = nsMap.keySet().stream().
		// 过滤名称不在中国地名库里的地名（比如，中国，巴黎。）
				filter(cityName -> null != cityMap.get(cityName)).map(this::getLine).flatMap(lines -> lines.stream())
				.collect(Collectors.toList());
		String topArea = null;
		int minCityNameIdx = Integer.MAX_VALUE;
		/*
		 * 没有识别到任何地名，直接采用省市信息。
		 */
		Optional.ofNullable(province).filter(p -> !lineList.isEmpty()).filter(p -> null != cityMap.get(p))
				.ifPresent(p -> res.put("area", Lists.newArrayList(p)));
		/*
		 * 如果只有唯一结果，就没啥说的了。
		 */
		if (lineList.size() == 1) {
			res.put("area", Lists.newArrayList(lineList.get(0)));
			return res;
		}
		/**
		 * 采用第一种搜索方法,找到第一個出现的市级地名,找到包含这个市级地名的序列里最长的。
		 */
		// 找到第一个出现的市级地名，选中这个地名的队列。
		for (List<String> line : lineList) {
			if (line.size() > 1) {
				String area = line.get(1);
				Integer cityNameIdx = nsIndex.get(area);
				if (null == cityNameIdx) {
					continue;
				}
				// System.out.println(area + " -> " + cityNameIdx);
				if (cityNameIdx < minCityNameIdx) {
					minCityNameIdx = cityNameIdx;
					topArea = area;
				}
			}
		}
		// 临时结果序列
		List<String> tmpResLine = new ArrayList<>();
		// 找到这个市级单位里最具体的地址。
		for (List<String> line : lineList) {
			if (line.size() > 1) {
				String area = line.get(1);
				if (StringUtils.equals(topArea, area)) {
					if (tmpResLine.size() < line.size()) {
						tmpResLine = line;
					}
				}
			}
		}

		if (!tmpResLine.isEmpty()) {
			res.put("area", tmpResLine);
			return res;
		}
		if (!res.isEmpty()) {
			@SuppressWarnings("unchecked")
			List<String> tmpList = (List<String>) res.get("area");
			ArrayList<String> _rs = new ArrayList<>();
			for (int i = 0; i < tmpList.size(); i++) {
				String qm = quanming.get(tmpList.get(i));
				if (StringUtils.isNotBlank(qm)) {
					_rs.add(qm);
				} else {
					_rs.add(tmpList.get(i));
				}
			}
			res.put("area", _rs);
		}
		return res;
	}

	/**
	 * 从地名，反推所有可能的地名序列 <BR>
	 * 比如'朝阳区'，可能是(北京市,朝阳区)也可能是(吉林省,长春市,朝阳区)
	 */
	private List<List<String>> getLine(String ns) {
		return new CityData(ns, null, 4).getLines();
	}

	/**
	 * 
	 * 用于表示地名可能关系的对象
	 *
	 */
	private class CityData {
		CityData(String name, CityData zCity, int deply) {
			this.name = name;
			this.reverseReferenceCity = zCity;

			if (deply <= 0) {
				return;
			}
			// 找到所有可能的上级地名
			List<String> list = cityMap.get(name);
			if (null != list && !list.isEmpty()) {
				for (String cityName : list) {
					CityData next = new CityData(cityName, this, deply - 1);
					if (StringUtils.isNotBlank(next.name)) {
						superCitys.add(next);
					}
				}
			}
		}

		private String name;
		// 所有可能的上级行政区域。
		private List<CityData> superCitys = new ArrayList<>();
		// 反向引用，用于方便的找回。
		private CityData reverseReferenceCity = null;

		public List<List<String>> getLines() {
			List<CityData> rootCity = Lists.newArrayList();
			addCity(this, rootCity);
			return rootCity.stream().map(this::getLine).collect(Collectors.toList());
		}

		private List<String> getLine(CityData rootCity) {
			List<String> res = Lists.newArrayList();
			while (true) {
				if (rootCity == null) {
					break;
				}
				res.add(rootCity.name);
				rootCity = rootCity.reverseReferenceCity;
			}
			return res;
		}

		private void addCity(CityData city, List<CityData> rootCityList) {
			if (city.superCitys.isEmpty()) {
				rootCityList.add(city);
			} else {
				for (CityData cd : city.superCitys) {
					addCity(cd, rootCityList);
				}
			}
		}
	}
}
