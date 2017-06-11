package org.yansou.ci.crawler.utils;

import org.jsoup.nodes.Document;


/**
 * 根据标签内容 获取相关的属性
 * 2015年3月19日上午10:29:26
 * @author luanyang
 */
public class JsoupUtil {
	/**
	 * 获取标签的内容
	 * @param doc
	 * @param divcssPath
	 * @return
	 */
	public static String getText(Document doc,String divcssPath){
		String result = null;
		result =doc.select(divcssPath).text();
		return result;
	}
	/**
	 * 根据标签的属性获取属性的值
	 * @param node
	 * @param xpath
	 * @param attr
	 * @return
	 */
	public static String getAttributeByName(Document doc,String divcssPath,String attr){
		String result = null;
		result =doc.select(divcssPath).attr(attr);
		return result;
	}
	
}