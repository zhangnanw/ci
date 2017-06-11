package org.yansou.ci.crawler.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 创建对象使用，可以调用私有构造方法。
 * 
 * @author zhangnan1
 *
 */
public class CreateUtils {
	private CreateUtils() {

	}

	@SuppressWarnings("unchecked")
	public final static <T> Constructor<T> getConstructor(Class<T> clazz,
			Object... args) {
		ArrayList<Constructor<?>> resList = Lists.newArrayList();
		Class<?>[] argsClass = ClassUtils.toClass(args);
		Arrays.asList(clazz.getDeclaredConstructors()).forEach(cons -> {
			if (isAvailability0(cons, argsClass)) {
				resList.add((Constructor<T>) cons);
			}
		});
		if (resList.size() == 1) {
			return (Constructor<T>) resList.get(0);
		}
		if (resList.size() == 0) {
			throw new IllegalArgumentException("未能找到构造方法:");
		}
		throw new IllegalArgumentException("不能确定唯一的构造方法:" + resList);
	}

	final private static boolean isAvailability0(Constructor<?> constructor,
			Class<?>[] argsClass) {
		if (constructor.isVarArgs()) {
			throw new IllegalArgumentException("暂不支持可变参数的构造方法.");
		}
		Class<?>[] consClass = constructor.getParameterTypes();
		if (argsClass.length == consClass.length) {
			int leng = argsClass.length;
			for (int i = 0; i < leng; i++) {
				if (argsClass[i] == null)
					continue;
				Class<?> conClass = consClass[i];
				Class<?> argClass = argsClass[i];
				if (conClass.isPrimitive()) {
					conClass = ClassUtils.primitiveToWrapper(conClass);
				}
				if (!conClass.isAssignableFrom(argClass))
					return false;
			}
			return true;
		}
		return false;
	}

	public final static <T> T create(Class<T> clazz, Object... args) {
		try {
			Constructor<T> constructor = getConstructor(clazz, args);
			constructor.setAccessible(true);
			return constructor.newInstance(args);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| SecurityException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
