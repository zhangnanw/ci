package org.yansou.ci.data.mining.nlpir.impl;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.yansou.ci.data.mining.Analyzer;
import org.yansou.ci.data.mining.util.ErrUtils;

import com.alibaba.fastjson.JSONObject;
/**
 * 错误权重
 * @author Administrator
 *
 */
public class ErrWeightAnalyzer implements Analyzer {

	@Override
	public JSONObject analy(JSONObject obj) {
		JSONObject res = new JSONObject();
		String context = obj.getString("context");
		ErrUtils.checkAdd(obj, isRC(context), "跨行跨列表格", WeightZH.CROSS_COLUMN);
		String errVal = obj.getString("err");
		if (StringUtils.isNotBlank(errVal)) {
			res.put("err", errVal);
		}
		res.put("weight", obj.getFloatValue("weight"));
		return res;
	}

	public boolean isRC(String html) {
		Document document = Jsoup.parse(html);
		Elements row = document.select("[rowspan]");
		Elements col = document.select("[colspan]");
		if (!row.isEmpty()) {
			return true;
		} else if (!col.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean match(JSONObject obj) {
		return true;
	}

}
