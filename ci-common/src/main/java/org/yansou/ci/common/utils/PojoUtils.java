package org.yansou.ci.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

/**
 * Pojo类的一下工具方法。
 * 
 * @author n.zhang
 *
 */
public final class PojoUtils {
	/**
	 * trim所有字符串
	 * 
	 * @param obj
	 */
	final static public void trimAllString(Object obj) {
		fieldBatch(obj, Class.class, val -> null != val, val -> StringUtils.trim((String) val));
	}

	/**
	 * 所有 为null的字符串变成空白字符串
	 * 
	 * @param obj
	 */
	final static public void nullStringToEmpty(Object obj) {
		fieldBatch(obj, String.class, Objects::isNull, val -> "");
	}

	/**
	 * 所有字段处理。
	 * 
	 * @param pojo
	 * @param fieldType
	 * @param valPred
	 * @param fun
	 */
	@SuppressWarnings({ "rawtypes" })
	public final static void fieldBatch(Object pojo, Class fieldType, Predicate valPred, Function fun) {
		fieldBatch(pojo, f -> fieldType.equals(f.getType()), valPred, fun);
	}

	/**
	 * 所有字段处理。
	 * 
	 * @param pojo
	 * @param typeFilter
	 * @param valueFilter
	 * @param change
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public final static void fieldBatch(Object pojo, Predicate<Field> typeFilter, Predicate valueFilter,
			Function change) {
		Class<?> clazz = pojo.getClass();
		List<Field> fieldList = Arrays.asList(clazz.getDeclaredFields()).stream()
				.filter(f -> !Modifier.isFinal(f.getModifiers())).filter(f -> !Modifier.isStatic(f.getModifiers()))
				.filter(typeFilter).peek(m -> m.setAccessible(true)).collect(Collectors.toList());
		for (Field field : fieldList) {
			try {
				Object val = field.get(pojo);
				if (valueFilter.test(val)) {
					field.set(pojo, change.apply(val));
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new IllegalStateException(e);
			}
		}
	}

}
