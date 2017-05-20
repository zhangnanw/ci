package org.yansou.ci.common.utils;

import java.lang.reflect.Array;

/**
 * 数组工具类
 *
 * @author liutiejun
 * @create 2017-05-10 22:37
 */
public class SimpleArrayUtils {

	/**
	 * 根据数组类型的class创建对应类型的数组
	 *
	 * @param <T> 目标类型
	 * @param clazz
	 * @param length 数组长度
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] newArrayByArrayClass(Class<T[]> clazz, int length) {
		return (T[]) Array.newInstance(clazz.getComponentType(), length);
	}

	/**
	 * 根据普通类型的class创建数组
	 *
	 * @param <T> 目标类型
	 * @param clazz
	 * @param length 数组长度
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] newArrayByClass(Class<T> clazz, int length) {
		return (T[]) Array.newInstance(clazz, length);
	}

}
