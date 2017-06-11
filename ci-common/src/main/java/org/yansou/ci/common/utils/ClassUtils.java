package org.yansou.ci.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @author liutiejun
 * @create 2017-05-25 23:48
 */
public class ClassUtils {

	public static Class<?> getClass(String typeName) {
		if (StringUtils.isBlank(typeName)) {
			return null;
		}

		if (typeName.equals("int") || typeName.equals("java.lang.Integer")) {
			return Integer.class;
		}

		if (typeName.equals("long") || typeName.equals("java.lang.Long")) {
			return Long.class;
		}

		if (typeName.equals("double") || typeName.equals("java.lang.Double")) {
			return Double.class;
		}

		if (typeName.equals("float") || typeName.equals("java.lang.Float")) {
			return Float.class;
		}

		if (typeName.equals("java.util.Date")) {
			return Date.class;
		}

		return String.class;
	}

	public static Object getValue(Object value, String valueType) {
		if (value == null) {
			return null;
		}

		String valueStr = value.toString();

		if (StringUtils.isBlank(valueStr)) {
			return null;
		}

		if (valueType.equals("int") || valueType.equals("java.lang.Integer")) {
			return Integer.parseInt(valueStr);
		}

		if (valueType.equals("long") || valueType.equals("java.lang.Long")) {
			return Long.parseLong(valueStr);
		}

		if (valueType.equals("double") || valueType.equals("java.lang.Double")) {
			return Double.parseDouble(valueStr);
		}

		if (valueType.equals("float") || valueType.equals("java.lang.Float")) {
			return Float.parseFloat(valueStr);
		}

		if (valueType.equals("java.util.Date")) {
			return DateFormater.parse(valueStr);
		}

		return value;
	}
}
