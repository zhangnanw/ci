package org.yansou.ci.common.page;

/**
 * 分页查询参数
 *
 * @author liutiejun
 * @create 2017-05-15 14:59
 */
public class ColumnInfo {

	// Column's data source
	private String data;
	// Column's name
	private String name;
	// Flag to indicate if this column is searchable (true) or not (false)
	private Boolean searchable;
	// Flag to indicate if this column is orderable (true) or not (false)
	private Boolean orderable;
	private SearchInfo searchInfo;

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

	public SearchInfo getSearchInfo() {
		return searchInfo;
	}

	public void setSearchInfo(SearchInfo searchInfo) {
		this.searchInfo = searchInfo;
	}
}
