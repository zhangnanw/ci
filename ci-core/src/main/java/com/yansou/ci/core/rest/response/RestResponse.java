package com.yansou.ci.core.rest.response;

import com.yansou.ci.common.utils.GsonUtils;

import java.io.Serializable;

/**
 * API请求返回数据封装
 *
 * @param <T>
 */
public class RestResponse<T> implements Serializable {

	private static final long serialVersionUID = 8694878353724315950L;

	private Integer status;// 状态值

	private String errors;// 错误信息

	private T result;// 请求返回的数据

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getErrors() {
		return errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

//	public static RestResponse<String> ok() {
//		return ok(RestStatus.OK.name());
//	}
//
//	public static <R> RestResponse<R> ok(R result) {
//		RestResponse<R> restResponse = new RestResponse<>();
//
//		restResponse.setStatus(RestStatus.OK.getValue());
//		restResponse.setResult(result);
//
//		return restResponse;
//	}
//
//	public static RestResponse<String> exception() {
//		return exception(RestStatus.INTERNAL_SERVER_ERROR.name());
//	}
//
//	public static RestResponse<String> exception(Exception e) {
//		String errors = null;
//		if (e != null) {
//			errors = e.getMessage();
//
//			if (errors == null) {
//				errors = e.getClass().getSimpleName();
//			}
//		}
//
//		return exception(errors);
//	}
//
//	public static RestResponse<String> exception(String errors) {
//		RestResponse<String> restResponse = new RestResponse<>();
//
//		restResponse.setStatus(RestStatus.INTERNAL_SERVER_ERROR.getValue());
//		restResponse.setErrors(errors);
//		restResponse.setResult(errors);
//
//		return restResponse;
//	}

	@Override
	public String toString() {
		return GsonUtils._gson.toJson(this);
	}

}
