package org.yansou.ci.common.page;

/**
 * 查询条件
 *
 * @author liutiejun
 * @create 2017-05-15 15:09
 */
public class SearchInfo {

	// search value.
	// To be applied to all columns which have searchable as true.
	private String value;
	// true if the global filter should be treated as a regular expression for advanced searching
	// false otherwise.
	private Boolean regex;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean getRegex() {
		return regex;
	}

	public void setRegex(Boolean regex) {
		this.regex = regex;
	}
}
