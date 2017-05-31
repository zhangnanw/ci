package org.yansou.ci.common.page;

import org.yansou.ci.common.utils.GsonUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询条件
 *
 * @author liutiejun
 * @create 2017-05-13 10:34
 */
public class PageCriteria implements Serializable {

	private static final long serialVersionUID = -7245988437965539677L;

	// Draw counter
	private Integer draw;

	// 当前页号，从1开始
	private Integer currentPageNo;

	// Paging first record indicator.
	// This is the start point in the current data set
	// (0 index based - i.e. 0 is the first record).
	private Integer start;

	// Number of records that the table can display in the current draw.
	private Integer pageSize;

	private List<ColumnInfo> columnInfoList;

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(Integer currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<ColumnInfo> getColumnInfoList() {
		return columnInfoList;
	}

	public void setColumnInfoList(List<ColumnInfo> columnInfoList) {
		this.columnInfoList = columnInfoList;
	}

	@Override
	public String toString() {
		return GsonUtils._gson.toJson(this);
	}

}
