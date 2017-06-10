package org.yansou.ci.crawler.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 描述信息：
 * 
 * @author: liuqingchao - liuqingchao@pyc.com.cn
 * @data: 2015年3月20日
 */
public class RegexUtils {

	private static Pattern pa = null;
	private static Matcher ma = null;

	/**
	 * 
	 * 描述信息：匹配唯一值情况
	 * 
	 * @author: liuqingchao - liuqingchao@pyc.com.cn
	 * @data: 2015年3月20日
	 */
	public static String getOneAttributeValue(String content, String regex) {
		pa = Pattern.compile(regex, Pattern.DOTALL);
		ma = pa.matcher(content);
		if (ma.find()) {
			return ma.group();
		}
		return null;
	}

	/**
	 * 
	 * 描述信息：匹配唯一值情况
	 * 
	 * @author: liuqingchao - liuqingchao@pyc.com.cn
	 * @data: 2015年3月20日
	 */
	public static String[] getOneAttributeValue(String content, String regex,
			int... groups) {
		pa = Pattern.compile(regex, Pattern.DOTALL);
		ma = pa.matcher(content);
		ArrayList<String> list = new ArrayList<String>();
		if (ma.find()) {
			for (int i = 0; i < groups.length; i++) {
				list.add(ma.group(groups[i]));
			}
		}
		if (list.isEmpty()) {
			return null;
		}
		return list.toArray(new String[list.size()]);
	}

	/**
	 * 
	 * 描述信息：匹配唯一值情况
	 * 
	 * @author: liuqingchao - liuqingchao@pyc.com.cn
	 * @data: 2015年3月20日
	 */
	public static String getOneAttributeValue(String content, String regex,
			int group) {
		pa = Pattern.compile(regex, Pattern.DOTALL);
		ma = pa.matcher(content);
		if (ma.find()) {
			return ma.group(group);
		}
		return null;
	}

	/**
	 * 
	 * 描述信息：获取一组属性值，主要针对快照页链接url
	 * 
	 * @author: liuqingchao - liuqingchao@pyc.com.cn
	 * @data: 2015年3月20日
	 */
	public static List<String> getListAttributeValues(String content,
			String regex) {
		pa = Pattern.compile(regex, Pattern.DOTALL);
		ma = pa.matcher(content);
		List<String> list = new ArrayList<String>();
		while (ma.find()) {
			list.add(ma.group(1));
		}
		return list;
	}

	public static String getPatternUrl(String url){
		String str =null;
		Pattern pattern =Pattern.compile("http.*;\\)");
		Matcher matcher =pattern.matcher(url);
		if(matcher.find()){
			str=matcher.group(0);
			if(str.endsWith("&#39;)")){
				str=str.replace("&#39;)", "").trim();
			}
			try {
				str=URLDecoder.decode(str, "utf8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return str;
	}
	
}
