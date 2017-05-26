package org.yansou.ci.common.datatables.mapping;

import org.yansou.ci.common.utils.GsonUtils;

/**
 * @author liutiejun
 * @create 2017-05-22 14:33
 */
public class Column {

	// Column's data source
	private String data;
	// Column's name
	private String name;
	// Flag to indicate if this column is searchable (true) or not (false)
	private Boolean searchable;
	// Flag to indicate if this column is orderable (true) or not (false)
	private Boolean orderable;
	private Search search;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getSearchable() {
		return searchable;
	}

	public void setSearchable(Boolean searchable) {
		this.searchable = searchable;
	}

	public Boolean getOrderable() {
		return orderable;
	}

	public void setOrderable(Boolean orderable) {
		this.orderable = orderable;
	}

	public Search getSearch() {
		return search;
	}

	public void setSearch(Search search) {
		this.search = search;
	}

	@Override
	public String toString() {
		return GsonUtils._gson.toJson(this);
	}
}
