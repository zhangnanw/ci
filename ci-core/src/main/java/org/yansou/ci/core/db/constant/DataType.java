package org.yansou.ci.core.db.constant;

/**
 * 数据类型
 *
 * @author liutiejun
 * @create 2017-06-25 14:57
 */
public enum DataType {

	BIDDING(1), // 1-招标公告
	WIN(2), // 2-中标公告
	CORRECT(3), // 3-更正公告
	ABANDON(4), // 4-废标公告
	FAILURE(5);// 5-流标公告

	private Integer value;

	DataType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

}
