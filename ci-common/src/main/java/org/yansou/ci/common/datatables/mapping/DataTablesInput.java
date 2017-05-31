package org.yansou.ci.common.datatables.mapping;

import org.yansou.ci.common.utils.GsonUtils;

/**
 * @author liutiejun
 * @create 2017-05-22 14:32
 */
public class DataTablesInput {

	/**
	 * Draw counter. This is used by DataTables to ensure that the Ajax returns from server-side
	 * processing requests are drawn in sequence by DataTables (Ajax requests are asynchronous and
	 * thus can return out of sequence). This is used as part of the draw return parameter (see
	 * below).
	 */
	private Integer draw;

	/**
	 * Paging first record indicator. This is the start point in the current data set (0 index based -
	 * i.e. 0 is the first record).
	 */
	private Integer start;

	/**
	 * Number of records that the table can display in the current draw. It is expected that the
	 * number of records returned will be equal to this number, unless the server has fewer records to
	 * return. Note that this can be -1 to indicate that all records should be returned (although that
	 * negates any benefits of server-side processing!)
	 */
	private Integer length;

	/**
	 * Global search parameter.
	 */
	private Search search;

	/**
	 * Order parameter
	 */
	private Order[] order;

	/**
	 * Per-column search parameter
	 */
	private Column[] columns;

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Search getSearch() {
		return search;
	}

	public void setSearch(Search search) {
		this.search = search;
	}

	public Order[] getOrder() {
		return order;
	}

	public void setOrder(Order[] order) {
		this.order = order;
	}

	public Column[] getColumns() {
		return columns;
	}

	public void setColumns(Column[] columns) {
		this.columns = columns;
	}

	@Override
	public String toString() {
		return GsonUtils._gson.toJson(this);
	}
}
