package org.yansou.ci.data.mining.nlpir.impl;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;
import static org.apache.commons.lang3.StringUtils.trim;

import org.apache.commons.lang3.StringUtils;
import org.yansou.ci.data.mining.Analyzer;
import org.yansou.ci.data.mining.util.ErrUtils;

import com.alibaba.fastjson.JSONObject;

public class TitleAndTypeAnalyzer implements Analyzer {

	@Override
	public boolean match(JSONObject obj) {
		return true;
	}

	@Override
	public JSONObject analy(JSONObject obj) {
		JSONObject res = new JSONObject();
		String title = obj.getString("title");
		String anchorText = obj.getString("anchorText");
		if (null == anchorText) {
			anchorText = "";
		}
		title = trim(normalizeSpace(title));
		title = title.replaceAll("\\s+", "");
		anchorText = trim(normalizeSpace(anchorText));
		anchorText = anchorText.replaceAll("\\s+", "");
		if (isNotBlank(title)) {
			res.put("title", title);
		}
		if (isNotBlank(anchorText) && (!anchorText.endsWith("...") || isNotBlank(title))) {
			res.put("title", anchorText);
		}
		checkTitle(obj, res);
		return res;
	}

	private void checkTitle(JSONObject obj, JSONObject res) {
		ErrUtils.checkAdd(obj, StringUtils.endsWith(res.getString("title"), "..."), "标题结尾省略",
				WeightZH.INCOMPLETE_TITLE);
	}

}
