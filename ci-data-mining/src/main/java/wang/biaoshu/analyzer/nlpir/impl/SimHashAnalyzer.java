package wang.biaoshu.analyzer.nlpir.impl;

import org.nlpcn.commons.lang.finger.SimHashService;

import com.alibaba.fastjson.JSONObject;

import wang.biaoshu.analyzer.Analyzer;

public class SimHashAnalyzer implements Analyzer {

	@Override
	public JSONObject analy(JSONObject obj) {
		JSONObject res = new JSONObject();
		SimHashService sim = new SimHashService();
		String text = obj.getString("context");
		long simhash = sim.fingerprint(text);
		res.put("shash", simhash);
		String str = Long.toHexString(simhash);
		System.out.println(str);
		return res;
	}

	@Override
	public boolean match(JSONObject obj) {
		return true;
	}

}
