package org.yansou.ci.common.page;

/**
 * 排序条件
 *
 * @author liutiejun
 * @create 2017-05-15 15:07
 */
public class OrderInfo {

	/**
	 * 字段的排序方式
	 */
	public enum OrderOp {
		ASC("asc"), DESC("desc");

		private String value;

		OrderOp(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * 排序的字段
	 */
	private String propertyName;
	/**
	 * 字段的排序方式：ASC、DESC
	 */
	private OrderOp orderOp;

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public OrderOp getOrderOp() {
		return orderOp;
	}

	public void setOrderOp(OrderOp orderOp) {
		this.orderOp = orderOp;
	}
}
