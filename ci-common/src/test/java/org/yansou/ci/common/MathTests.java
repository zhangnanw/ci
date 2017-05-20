package org.yansou.ci.common;

/**
 * @author liutiejun
 * @create 2017-05-10 23:00
 */
public class MathTests {

	public static void main(String[] args) {
		System.out.println((12.0 / 5.0) + "-->" + Math.ceil(12.0 / 5.0));
		System.out.println((12.0 / 5.0) + "-->" + Math.ceil(new Integer(12).doubleValue() / 5.0));
		System.out.println((-12.0 / 5.0) + "-->" + Math.ceil(-12.0 / 5.0));
	}
}
