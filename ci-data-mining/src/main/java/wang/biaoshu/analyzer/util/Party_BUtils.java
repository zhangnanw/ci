package wang.biaoshu.analyzer.util;

import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Party_BUtils {
	/**
	 * 
	 * @param str
	 * @return
	 */
	final static public String strToJson(String str) {
		JSONArray arr = new JSONArray();
		if (null != str) {
			// 大小写分号冒号和竖线，分割不同组。
			for (String line : str.split("[；;:：\\|]+")) {
				JSONObject val = new JSONObject(new LinkedHashMap<>());
				// 大小写逗号句号，分割公司与价格，可以不写价格。
				String[] as = line.split("[，,。\\.]+");
				if (as.length > 0) {
					val.put("name", as[0]);
				}
				if (as.length > 1 && StringUtils.isNumeric(as[1])) {
					val.put("price", as[1]);
				}
				arr.add(val);
			}
		}
		return arr.toJSONString();
	}

	/**
	 * 
	 * @param json
	 * @return
	 */
	final static public String jsonToStr(String json) {
		JSONArray arr = JSONArray.parseArray(json);
		StringBuffer sb = new StringBuffer();
		int size = arr.size();
		for (int i = 0; i < size; i++) {
			JSONObject val = arr.getJSONObject(i);
			sb.append(val.getString("name"));
			if (StringUtils.isNumeric(val.getString("price"))) {
				sb.append(',');
				sb.append(val.getString("price"));
			}
			if (i < size - 1) {
				sb.append(';');
			}
		}
		return sb.toString();
	}
}
