package org.yansou.ci.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Pojo类的一下工具方法。
 * 
 * @author n.zhang
 *
 */
public final class PojoUtils {
	/**
	 * trim所有String字段 <BR>
	 * 不处理父类的字段。
	 * 
	 * @param obj
	 *            待处理对象
	 */
	final static public void trimAllStringField(Object obj) {
		Class<?> clazz = obj.getClass();
		List<Field> fieldList = Arrays.asList(clazz.getDeclaredFields()).stream()
				.filter(f -> !Modifier.isFinal(f.getModifiers())).filter(f -> !Modifier.isStatic(f.getModifiers()))
				.filter(f -> String.class.equals(f.getType())).peek(m -> m.setAccessible(true))
				.collect(Collectors.toList());
		for (Field field : fieldList) {
			try {
				String str = (String) field.get(obj);
				if (null != str) {
					field.set(obj, str.trim());
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new IllegalStateException(e);
			}
		}
	}

}
