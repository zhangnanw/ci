package wang.biaoshu.analyzer.nlpir.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import wang.biaoshu.analyzer.AResult;
import wang.biaoshu.analyzer.ATerm;
import wang.biaoshu.analyzer.Analyzer;
import wang.biaoshu.analyzer.ansj.AnsjAnalysis;

public class CategoryCodeAnalyzerV2 implements Analyzer {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(CategoryCodeAnalyzerV2.class);
	private static String USER_TYPE = "usertype";
	private AnsjAnalysis analy;
	final private Map<String, String> codeMap = Maps.newHashMap();

	public Map<String, String> getCodeMap() {
		return Collections.unmodifiableMap(codeMap);
	}

	public CategoryCodeAnalyzerV2() {
		// 读取数据库表
		List<Map<String, Object>> maplist = initCategory();
		// 找到所有的分类名
		Set<String> category_name_set = maplist.stream().map(m -> m.get("category_name")).map(n -> n.toString())
				.collect(Collectors.toSet());
		// 使用分类名，创建解析器，并标记一个自定义的词性 userType ;
		analy = new AnsjAnalysis(category_name_set, USER_TYPE);
		maplist.forEach(m -> {
			codeMap.put((String) m.get("category_name"), (String) m.get("category_code"));
		});
	}

	@Override
	public boolean match(JSONObject obj) {
		return true;
	}

	private List<Map<String, Object>> initCategory() {
		MysqlDataSource ds = new MysqlDataSource();
		ds.setURL("jdbc:mysql://biaoshuking.mysql.rds.aliyuncs.com:3306/hngp?useUnicode=true&characterEncoding=utf-8");
		ds.setUser("hngp");
		ds.setPassword("hngp123");
		QueryRunner qr = new QueryRunner(ds);
		String sql = "select id,category_code,category_name,keywords from tab_bidd_category";
		List<Map<String, Object>> maplist = new ArrayList<Map<String, Object>>();
		try {
			maplist = qr.query(sql, new MapListHandler());
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
		return maplist;
	}

	public JSONObject analy(JSONObject obj) {
		JSONObject res = new JSONObject();
		String category_code = null;
		List<String> txtList = Arrays.asList(obj.getString("title"), obj.getString("context")).stream()
				.filter(StringUtils::isNotBlank).collect(Collectors.toList());
		for (String txt : txtList) {
			AResult result = analy.recognition(txt).filter(t -> USER_TYPE.equals(t.getNature()));
			ATerm term = result.stream().min((a, b) -> a.getOffset() - b.getOffset()).orElse(null);
			if (null != term) {
				category_code = codeMap.get(term.getWord());
			}
		}
		if (StringUtils.isNotBlank(category_code)) {
			JSONArray json = new JSONArray();
			if (category_code.length() == 4) {
				json.add(category_code);
			}
			if (category_code.length() == 8) {
				json.add(category_code.substring(0, 4));
				json.add(category_code);
			}
			if (category_code.length() == 12) {
				json.add(category_code.substring(0, 4));
				json.add(category_code.substring(0, 8));
				json.add(category_code);
			}
			res.put("category_code_list", json);
		}
		return res;
	}
}
