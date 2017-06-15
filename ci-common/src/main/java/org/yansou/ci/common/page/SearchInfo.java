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
	 * <ul>
	 * <li>1、用于EQ、NE、LIKE、GT、GE、LT、LE查询的时候，只有一个值</li>
	 * <li>2、用于BETWEEN查询的时候，包含最小值、最大值</li>
	 * <li>3、用于IN查询的时候，包含多个值</li>
	 * <li>4、用于IS_NULL、IS_NOT_NULL查询的时候，为空</li>
	 * </ul>
	 */
	private String[] values;

	/**
	 * 字段类型
	 */
	private String valueType;
	/**
	 * 字段的查询方式：EQ、NE、LIKE、GT、GE、LT、LE、BETWEEN、IN、IS_NULL、IS_NOT_NULL
	 */
	private SearchOp searchOp;

	public SearchInfo() {
	}

	public SearchInfo(String propertyName, String[] values, String valueType, SearchOp searchOp) {
		this.propertyName = propertyName;
		this.values = values;
		this.valueType = valueType;
		this.searchOp = searchOp;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
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
