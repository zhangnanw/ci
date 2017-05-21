package org.yansou.ci.data.mining.utils;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

public class ErrUtils {
	/**
	 * 加入消息
	 *
	 * @param obj
	 * @param msg
	 */
	private static final void add(JSONObject obj, String msg, float w) {
		String err = obj.getString("err");
		if (StringUtils.isBlank(err)) {
			err = msg;
		} else {
			err += ";" + msg;
		}
		obj.put("err", err);
		obj.put("weight", obj.getFloatValue("weight") + w);
	}

	/**
	 * 根据条件决定是否加入消息
	 *
	 * @param obj
	 * @param isAdd
	 * @param msg
	 */
	public static final void checkAdd(JSONObject obj, boolean isAdd, String msg) {
		checkAdd(obj, isAdd, msg, 0);
	}

	/**
	 * 根据条件决定是否加入消息 并加权。
	 *
	 * @param obj
	 * @param isAdd
	 * @param msg
	 * @param w
	 */
	public static final void checkAdd(JSONObject obj, boolean isAdd, String msg, double w) {
		if (isAdd) {
			add(obj, msg, (float) w);
		}
	}
}
