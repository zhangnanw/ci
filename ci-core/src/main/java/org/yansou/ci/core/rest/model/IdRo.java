package org.yansou.ci.core.rest.model;

import java.io.Serializable;

/**
 * @author liutiejun
 * @create 2017-05-13 22:42
 */
public class IdRo implements Serializable {

	private static final long serialVersionUID = -7137522172364409467L;

	private Long id;// 新增、更新数据对应的id
	private String url;// 新增、更新数据成功后，页面跳转对应的请求URL

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
