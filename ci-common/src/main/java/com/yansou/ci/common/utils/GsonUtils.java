package com.yansou.ci.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * JSON数据处理工具类
 *
 * @author liutiejun
 * @create 2017-05-05 11:40
 */
public class GsonUtils {

	public static final Gson _gson = initGson();

	private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	private static Gson initGson() {
		Gson gson = new GsonBuilder().setDateFormat(DATE_TIME_PATTERN)
				// 禁此序列化内部类
				.disableInnerClassSerialization()
				// 禁止转义html标签
				.disableHtmlEscaping()
				// 格式化输出
				.setPrettyPrinting().create();

		return gson;
	}

}
