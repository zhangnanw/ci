package org.yansou.ci.common.page;

/**
 * 分页查询参数
 *
 * @author liutiejun
 * @create 2017-05-15 14:59
 */
public class ColumnInfo {

	// 属性名称
	private String propertyName;
	// Flag to indicate if this column is searchable (true) or not (false)
	private Boolean searchable;
	// Flag to indicate if this column is orderable (true) or not (false)
	private Boolean orderable;
	private SearchInfo searchInfo;
	private OrderInfo orderInfo;

	public ColumnInfo() {
	}

	public ColumnInfo(String propertyName, Boolean searchable, Boolean orderable, SearchInfo searchInfo, OrderInfo
			orderInfo) {
		this.propertyName = propertyName;
		this.searchable = searchable;
		this.orderable = orderable;
		this.searchInfo = searchInfo;
		this.orderInfo = orderInfo;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
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

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}
}
