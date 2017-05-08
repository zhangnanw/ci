package com.yansou.ci.common.model.error;

/**
 * @author liutiejun
 * @create 2017-04-14 11:44
 */
public class ErrorInfo<T> {

	private Integer code;// 消息类型
	private String message;// 消息内容
	private String url;// 请求的URL
	private T data;// 请求返回的数据

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
