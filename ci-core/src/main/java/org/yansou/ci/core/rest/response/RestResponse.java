package org.yansou.ci.core.rest.response;

import org.yansou.ci.common.utils.GsonUtils;

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

	public void okT(T result) {
		setStatus(RestStatus.OK.getValue());
		setResult(result);
	}

	public void exceptionT() {
		exceptionT(RestStatus.INTERNAL_SERVER_ERROR.name());
	}

	public void exceptionT(Exception e) {
		String errors = null;
		if (e != null) {
			errors = e.getMessage();

			if (errors == null) {
				errors = e.getClass().getSimpleName();
			}
		}

		exceptionT(errors);
	}

	public void exceptionT(String errors) {
		setStatus(RestStatus.INTERNAL_SERVER_ERROR.getValue());
		setErrors(errors);
	}

	@Override
	public String toString() {
		return GsonUtils._gson.toJson(this);
	}

}
