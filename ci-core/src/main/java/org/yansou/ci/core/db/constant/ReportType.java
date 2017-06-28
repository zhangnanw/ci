package org.yansou.ci.core.db.constant;

/**
 * 报表类型，1-月报，2-季报，3-半年报，4-年报
 *
 * @author liutiejun
 * @create 2017-06-28 16:02
 */
public enum ReportType {
	MONTHLY(1),// 1-月报
	QUARTERLY(2),// 2-季报
	SEMIYEARLY(3),// 3-半年报
	YEARLY(4);// 4-年报

	private Integer value;

	ReportType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
}
