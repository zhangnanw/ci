package org.yansou.ci.core.db.constant;

/**
 * 产品部署方式
 * <ul>
 * <li>1-分布式</li>
 * <li>2-地面电站</li>
 * <li>3-未知</li>
 * </ul>
 *
 * @author liutiejun
 * @create 2017-06-25 16:44
 */
public enum DeploymentType {
	DISTRIBUTED(1), // 1-分布式
	GROUND(2), // 2-地面电站
	UNKNOWN(3);// 3-未知

	private Integer value;

	DeploymentType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
}
