package org.yansou.ci.common.datatables.mapping;

import org.yansou.ci.common.utils.GsonUtils;

/**
 * @author liutiejun
 * @create 2017-05-22 14:36
 */
public class Order {

	// Column to which ordering should be applied
	private Integer column;
	// Ordering direction for this column，desc、asc
	private String dir;

	public Integer getColumn() {
		return column;
	}

	public void setColumn(Integer column) {
		this.column = column;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	@Override
	public String toString() {
		return GsonUtils._gson.toJson(this);
	}
}
