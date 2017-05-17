package wang.biaoshu.analyzer.nlpir.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;

import wang.biaoshu.analyzer.Analyzer;
/**
 * 时间
 * @author Administrator
 *
 */
public class DateAnalyzer implements Analyzer {

	@Override
	public boolean match(JSONObject obj) {
		return true;
	}

	static Pattern yearP = Pattern.compile("(?<yyyy>[0-9]{4})[-_\\.:]*(?<MM>[0-9]{2})[-_\\.:]*(?<dd>[0-9]{2})");

	@Override
	public JSONObject analy(JSONObject obj) {
		JSONObject res = new JSONObject();
		String timeStr = obj.getString("time");
		Matcher match = yearP.matcher(timeStr);
		if (match.find()) {
			String str = match.group("yyyy") + "-" + match.group("MM") + "-" + match.group("dd");
			try {
				Date date = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(str);
				res.put("pubtime", new Timestamp(date.getTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

}
