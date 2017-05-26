package org.yansou.ci.common.datatables.mapping;

import org.yansou.ci.common.utils.GsonUtils;

/**
 * @author liutiejun
 * @create 2017-05-22 14:33
 */
public class Search {

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

	@Override
	public String toString() {
		return GsonUtils._gson.toJson(this);
	}
}
