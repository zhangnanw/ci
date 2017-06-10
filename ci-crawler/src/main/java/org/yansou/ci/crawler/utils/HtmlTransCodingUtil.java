package org.yansou.ci.crawler.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * html 中特殊字符转义工具类 
 * 目前是转换为10进制
 * 2015年4月13日下午5:04:24
 * @author luanyang
 */
public class HtmlTransCodingUtil {

	private static Pattern _x = Pattern.compile("&#(\\d+);");

	public static String decodeAscii(String str) {
		Matcher matcher = _x.matcher(str);
		int end = 0;
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			int thisStart = matcher.start(0);
			sb.append(str.substring(end, thisStart));
			end = matcher.end(0);
			String s = matcher.group(1);
			char c = (char) Integer.parseInt(s, 10);
			sb.append(c);
		}
		sb.append(str.substring(end));
		return sb.toString();
	}
	
}
