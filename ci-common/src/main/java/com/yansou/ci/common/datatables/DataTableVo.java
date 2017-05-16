package com.yansou.ci.common.datatables;

/**
 * 封装表格显示数据
 *
 * @param <T>
 *
 * @author liutiejun
 * @create 2017-05-13 22:35
 */
public class DataTableVo<T> {

	private Integer draw;
	// Total records, before filtering (i.e. the total number of records in the database)
	private Integer recordsTotal;
	// Total records, after filtering
	private Integer recordsFiltered;
	// The data to be displayed in the table
	private T[] data;
	private String error;

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public T[] getData() {
		return data;
	}

	public void setData(T[] data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
