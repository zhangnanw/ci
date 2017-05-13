package com.yansou.ci.common.page;

import java.io.Serializable;

/**
 * 分页查询条件
 *
 * @author liutiejun
 * @create 2017-05-13 10:34
 */
public class PageCriteria implements Serializable {

	private static final long serialVersionUID = -7245988437965539677L;

	/**
	 * 当前页号，从1开始
	 */
	private Integer currentPageNo;

	/**
	 * 每页显示的最多记录数
	 */
	private Integer pageSize;

	public Integer getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(Integer currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
