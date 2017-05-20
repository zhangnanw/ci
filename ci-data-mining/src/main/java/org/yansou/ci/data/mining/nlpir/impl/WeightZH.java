package org.yansou.ci.data.mining.nlpir.impl;

public class WeightZH {
	/**
	 * 中标价格大于预算
	 */
	public static float PRICE_ABOVE_BUDGET = 2;
	/**
	 * 跨行跨列表格
	 */
	public static float CROSS_COLUMN = 8;
	/**
	 * 价格大于1亿
	 */
	public static float PRICE_1E = 0.00002f;
	/**
	 * 价格上千万
	 */
	public static float PRICE_1QW = 0.00008f;

	/**
	 * 标题不完整
	 */
	public static float INCOMPLETE_TITLE = 0.00032f;
	/**
	 * 专家偶数
	 */
	public static float EVEN_EXPERTS = 0.00128f;
	/**
	 * 不到三个专家
	 */
	public static float LESS_THAN_3_EXPERTS = 0.00512f;

}
