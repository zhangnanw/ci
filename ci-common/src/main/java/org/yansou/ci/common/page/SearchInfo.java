package org.yansou.ci.common.page;

/**
 * 查询条件
 *
 * @author liutiejun
 * @create 2017-05-15 15:09
 */
public class SearchInfo {

	/**
	 * 字段的查询方式
	 */
	public enum SearchOp {
		EQ, NE, LIKE, GT, GE, LT, LE, BETWEEN, IN, IS_NULL, IS_NOT_NULL;
	}

	/**
	 * 查询的字段
	 */
	private String propertyName;
	/**
	 * 查询的字段值
	 */
	private String value;
	/**
	 * 字段类型
	 */
	private String valueType;
	/**
	 * 字段的查询方式：EQ、NE、LIKE、GT、GE、LT、LE、BETWEEN、IN、IS_NULL、IS_NOT_NULL
	 */
	private SearchOp searchOp;

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public SearchOp getSearchOp() {
		return searchOp;
	}

	public void setSearchOp(SearchOp searchOp) {
		this.searchOp = searchOp;
	}
}
