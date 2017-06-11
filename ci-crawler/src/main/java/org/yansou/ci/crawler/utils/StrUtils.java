package org.yansou.ci.crawler.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;


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
	
	public static void main(String[] args) throws Exception {
		String str =FileUtils.readFileToString(new File("C:/Users/Administrator/Desktop/new  0.txt"));
		System.out.println(StrUtils.String(str));
	}
}
