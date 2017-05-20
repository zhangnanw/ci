package org.yansou.ci.data.mining.nlpir.impl;

import org.yansou.ci.data.mining.Analyzer;

import com.alibaba.fastjson.JSONObject;

public class URLAnalyzer implements Analyzer {

	@Override
	public boolean match(JSONObject obj) {
		return true;
	}

	@Override
	public JSONObject analy(JSONObject obj) {
		JSONObject res=new JSONObject();
		res.put("urls", obj.get("url"));
		return res;
	}

}
