package org.yansou.ci.common.page;

/**
 * 排序条件
 *
 * @author liutiejun
 * @create 2017-05-15 15:07
 */
public class OrderInfo {

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
}
