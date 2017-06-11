package org.yansou.ci.crawler.utils;

import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;


public class StrUtils {
	public static String String(String aa){
		StringReader sr = new StringReader(aa);
		StringBuffer sb = new StringBuffer();
		String str=null;
		try {
			IOUtils.readLines(sr).forEach(line -> {
				sb.append(StringUtils.trim(line)).append('\n');
			});
			str = sb.toString();
			str = str.replaceAll("\n+", "\n").replaceAll("[\t ]+", " ");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return str;
	}
	
}
