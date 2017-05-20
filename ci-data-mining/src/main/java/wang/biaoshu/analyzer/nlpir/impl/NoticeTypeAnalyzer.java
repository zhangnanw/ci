package wang.biaoshu.analyzer.nlpir.impl;

import com.alibaba.fastjson.JSONObject;

import wang.biaoshu.analyzer.Analyzer;

public class NoticeTypeAnalyzer implements Analyzer {

	@Override
	public JSONObject analy(JSONObject obj) {
		JSONObject res = new JSONObject();
		RecordTypeAnalyzer analyzerr = new RecordTypeAnalyzer();
		JSONObject typ = analyzerr.analy(obj);
		if (typ.toString().equals("{}")) { 
			res.put("notice_type", "其他公告");
		} else {
			String val = typ.getString("record_type");
			if (val.equals("公开招标") || val.equals("招标") || val.equals("公 开 招 标") || val.equals("流拍")
					|| val.equals("采购公示")||val.equals("国内公开招标")||val.equals("招 标 公 告")||val.equals("招标公告")
					||val.equals("公开")||val.equals("开标")||val.equals("竞价")||val.equals("竞争性公开招标")||val.equals("公开招标采购")||val.contains("公开招标")
					||val.equals("电子反拍")||val.equals("反拍")) {
				res.put("notice_type", "招标公告");
			}
			if (val.equals("询价") || val.equals("询价公告")||val.equals("询价采购")) {
				res.put("notice_type", "询价公告");
			}
			if (val.equals("竞争性谈判") || val.equals("竞争性谈判公告")||val.equals("谈判")) {
				res.put("notice_type", "竞争性谈判");
			}
			if (val.equals("单一来源")||val.equals("单一来源采购")) {
				res.put("notice_type", "单一来源");
			}
			if (val.equals("资格预审") || val.equals("资格预审结果") || val.equals("资格预审合格公示") || val.equals("资格预审公告")) {
				res.put("notice_type", "资格预审");
			}
			if (val.equals("邀标公告") || val.equals("邀标")||val.equals("邀请招标")) {
				res.put("notice_type", "邀标公告");
			}
			if (val.equals("中标公告")||val.equals("中标")) {
				res.put("notice_type", "中标公告");
			}
			if (val.equals("中标公示") || val.equals("中标结果公示") || val.equals("采购结果公示") || val.equals("结果公示")) {
				res.put("notice_type", "中标公示");
			}
			if (val.equals("更正公告") || val.equals("更正") || val.equals("变更") || val.equals("变更公告") || val.equals("延期")) {
				res.put("notice_type", "更正公告");
			}
			if (val.equals("竞争性磋商")||val.equals("磋商")||val.equals("竞争性磋商公告")) {
				res.put("notice_type", "竞争性磋商");
			}
			if (val.equals("成交公告") || val.equals("成交") || val.equals("成 交 公 告") || val.equals("成交公示") || val.equals("成交结果公示 ")
					|| val.equals("成交结果公告 ") || val.equals("验收")) {
				res.put("notice_type", "成交公告");
			}
			if (val.equals("废标公告") || val.equals("废标") || val.equals("流标") || val.equals("终止公告") || val.equals("终止")) {
				res.put("notice_type", "废标流标");
			}
			if (val.equals("其他公告") || val.equals("其他") || val.equals("合同") || val.equals("采购公告") || val.equals("澄清")) {
				res.put("notice_type", "其他公告");
			}
			if (val.equals("")) {
				res.put("notice_type", "");
			}
		}
		return res;
	}

	@Override
	public boolean match(JSONObject obj) {
		return true;
	}

}
