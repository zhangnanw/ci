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
 * 本类里的所有方法，都只操作对象本身的字段。 <BR>
 * 对象父类字段无视。
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

	/**
	 * 无视Pojo类型，拷贝Pojo对象内同名同类型字段
	 * 
	 * @param srcPojo
	 * @param destPojo
	 * @return destPojo
	 */
	@SuppressWarnings("unchecked")
	public static <T> T copyTo(Object srcPojo, Object destPojo) {
		// 找到源类型和目标类型的所有成员变量
		Class<?> srcClazz = srcPojo.getClass();
		List<Field> srcFieldList = Arrays.asList(srcClazz.getDeclaredFields()).stream()
				.filter(f -> !Modifier.isFinal(f.getModifiers())).filter(f -> !Modifier.isStatic(f.getModifiers()))
				.peek(m -> m.setAccessible(true)).collect(Collectors.toList());
		Class<?> destClazz = destPojo.getClass();
		List<Field> destFieldList = Arrays.asList(destClazz.getDeclaredFields()).stream()
				.filter(f -> !Modifier.isFinal(f.getModifiers())).filter(f -> !Modifier.isStatic(f.getModifiers()))
				.peek(m -> m.setAccessible(true)).collect(Collectors.toList());
		// 找到名称和类型都相同的字段进行拷贝。
		for (Field srcField : srcFieldList) {
			Field destField = destFieldList.stream()
					.filter(df -> srcField.getType().equals(df.getType()) && srcField.getName().equals(df.getName()))
					.findFirst().orElse(null);
			if (null != destField) {
				try {
					Object val = srcField.get(srcPojo);
					if(null!=val) {
						destField.set(destPojo, val);
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new IllegalStateException(e);
				}
			}
		}
		return (T) destPojo;
	}

}
