package wang.biaoshu.analyzer.nlpir.impl;

import com.alibaba.fastjson.JSONObject;

import wang.biaoshu.analyzer.Analyzer;

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
