package org.yansou.ci.core.db.constant;

/**
 * 人工检查状态，0-没有检查，1-检查为识别正确的数据，2-检查为识别错误的数据
 *
 * @author liutiejun
 * @create 2017-06-25 14:55
 */
public enum Checked {
	NONE(0),// 0-没有检查
	RIGHT(1),// 1-检查为识别正确的数据
	WRONG(2);// 2-检查为识别错误的数据

	private Integer value;

	Checked(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
}
