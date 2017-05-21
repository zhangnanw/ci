package org.yansou.ci.data.mining.analyzer;

/**
 * 命名实体识别接口
 **/
public interface Analysis {
	/**
	 * 识别
	 */
	AResult recognition(String text);
}
