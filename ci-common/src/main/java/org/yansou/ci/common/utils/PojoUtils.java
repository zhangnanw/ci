package org.yansou.ci.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
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

	/**
	 * 所有 为null的字符串变成空白字符串
	 * 
	 * @param obj
	 */
	final static public void nullStringToEmpty(Object obj) {
		_allFieldFun(obj, String.class, Objects::isNull, val -> "");
	}

	/**
	 * 所有字段处理。
	 * 
	 * @param obj
	 * @param fieldType
	 * @param valPred
	 * @param fun
	 */
	final static void _allFieldFun(Object obj, Class<?> fieldType, Predicate<Object> valPred,
			Function<Object, Object> fun) {
		_allFieldFun(obj, f -> String.class.equals(f.getType()), valPred, fun);

	}

	/**
	 * 所有字段处理。
	 * 
	 * @param obj
	 * @param classPred
	 * @param valPred
	 * @param fun
	 */
	final static void _allFieldFun(Object obj, Predicate<Field> classPred, Predicate<Object> valPred,
			Function<Object, Object> fun) {
		Class<?> clazz = obj.getClass();
		List<Field> fieldList = Arrays.asList(clazz.getDeclaredFields()).stream()
				.filter(f -> !Modifier.isFinal(f.getModifiers())).filter(f -> !Modifier.isStatic(f.getModifiers()))
				.filter(classPred).peek(m -> m.setAccessible(true)).collect(Collectors.toList());
		for (Field field : fieldList) {
			try {
				Object val = field.get(obj);
				if (valPred.test(val)) {
					field.set(obj, fun.apply(obj));
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new IllegalStateException(e);
			}
		}
	}

}
