package wang.biaoshu.analyzer.nlpir.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import wang.biaoshu.analyzer.Analyzer;
import wang.biaoshu.analyzer.util.RegexUtils;
import wang.biaoshu.analyzer.util.TextTools;
/**
 * 甲方联系人
 * @author zhang
 *
 */
public class Biding_contact implements Analyzer {

	private static final Logger LOG = LoggerFactory.getLogger(Biding_contact.class);

	@Override
	public boolean match(JSONObject obj) {
		return true;
	}

	@Override
	public JSONObject analy(JSONObject obj) {
		JSONObject res = new JSONObject();
		String text = obj.getString("context");
		text = TextTools.normalize(text);
		LOG.debug(text);
		String bid_con = null;
		bid_con = RegexUtils.regex("(?:项目联系人|项目负责人):([\u4e00-\u9fa5]+)", text, 1);
		if (StringUtils.isBlank(bid_con))
			bid_con = RegexUtils.regex("(?:采购人联系方式):([\u4e00-\u9fa5]+)", text, 1);
		if (StringUtils.isBlank(bid_con))
			bid_con = RegexUtils.regex("(?:联系人):([\u4e00-\u9fa5 ]+)", text, 1);

		// String bid_con =
		// RegexUtils.regex("(?:招标人|采购人|招标单位名称|采购单位名称|采购人名称|采购单位|采购机构名称|委托单位|申请单位):([0-9\u4e00-\u9fa5]+)",
		// text, 1);
		if (StringUtils.length(bid_con) <= 5)
			res.put("Biding_contact", bid_con);
		return res;
	}

}
