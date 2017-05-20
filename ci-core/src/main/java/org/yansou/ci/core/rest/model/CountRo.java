package org.yansou.ci.core.rest.model;

import java.io.Serializable;

/**
 * @author liutiejun
 * @create 2017-05-13 22:47
 */
public class CountRo implements Serializable {

	private static final long serialVersionUID = 345355153421062855L;

	private int count;// 更新或者删除了多少条数据
	private String url;// 新增、更新数据成功后，页面跳转对应的请求URL

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
