package org.yansou.ci.core.db.constant;

/**
 * 产品类型，1-单晶硅，2-多晶硅，3-单晶硅、多晶硅，4-未知
 *
 * @author liutiejun
 * @create 2017-06-25 14:56
 */
public enum ProductType {

	MON(1),// 1-单晶硅
	POL(2),// 2-多晶硅
	MON_POL(3),// 3-单晶硅、多晶硅
	UNKNOWN(4);// 4-未知

	private Integer value;

	ProductType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
}
