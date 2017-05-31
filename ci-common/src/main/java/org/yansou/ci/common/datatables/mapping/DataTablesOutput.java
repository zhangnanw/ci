package org.yansou.ci.common.datatables.mapping;

import org.yansou.ci.common.utils.GsonUtils;

/**
 * 封装表格显示数据
 *
 * @param <T>
 *
 * @author liutiejun
 * @create 2017-05-13 22:35
 */
public class DataTablesOutput<T> {

	private Integer draw;
	// Total records, before filtering (i.e. the total number of records in the database)
	private Long recordsTotal;
	// Total records, after filtering
	private Long recordsFiltered;
	// The data to be displayed in the table
	private T[] data;
	private String error;

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public Long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Long recordsFiltered) {
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

	@Override
	public String toString() {
		return GsonUtils._gson.toJson(this);
	}
}
