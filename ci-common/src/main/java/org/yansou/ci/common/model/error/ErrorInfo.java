package org.yansou.ci.common.model.error;

/**
 * @author liutiejun
 * @create 2017-04-14 11:44
 */
public class ErrorInfo<T> {

	private Long timestamp;// 时间
	private Integer status;// 消息状态
	private String error;// 消息状态的内容
	private String exception;// 异常
	private String message;// 消息内容
	private String path;// 请求的URL
	private T data;// 请求返回的数据

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
