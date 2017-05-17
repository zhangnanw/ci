package wang.biaoshu.analyzer.nlpir.impl;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

import wang.biaoshu.analyzer.Analyzer;
/**
 * 项目名
 * @author Administrator
 *
 */
public class ItemNameAnalyzer implements Analyzer {
	@Override
	public boolean match(JSONObject obj) {
		return true;
	}

	@Override
	public JSONObject analy(JSONObject obj) {
		JSONObject res = new JSONObject();
		String title = obj.getString("title");
		String anchorText = obj.getString("anchorText");
		String pro_name = null;
		if (StringUtils.isNotBlank(title)) {
			pro_name = title;
		} else {
			title = "";
		}
		if (StringUtils.isNotBlank(anchorText)) {
			pro_name = anchorText;
		} else {
			anchorText = "";
		}
		if (StringUtils.endsWith(pro_name, "..")) {
			pro_name = title;
		}
		if (StringUtils.isNotBlank(pro_name)) {
			pro_name = pro_name.replaceAll("\\s*", "");
			pro_name = pro_name.replaceAll(
					"(项目|招标|成交公[告示]|公开招标|单一来源|施工招标|合同公[告示]|征求意见公[告示]|[竟竞]争性谈判|[竟竞]争性磋商|预?中标人?公[告示]|采购公[告示]|中标结果公[告示]|中标候选人公[告示]|废标公[告示]|采购公[告示]|变更公[告示]|资格预审|开标时间继续延期公告|更正公[告示]|更新公[告示]|流标公[告示]|结果公[告示]).*?$",
					"");
			pro_name = pro_name.replaceAll("\\pP$", "");
			if (StringUtils.isNotBlank(pro_name)) {
				res.put("item_name", pro_name);
			}
		}
		return res;
	}
}
