package org.yansou.ci.core.rest.report;

/**
 * @author liutiejun
 * @create 2017-06-20 21:20
 */
public class Serie {

	private String name;
	private double[] data;

	public Serie() {
	}

	public Serie(String name, double[] data) {
		this.name = name;
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double[] getData() {
		return data;
	}

	public void setData(double[] data) {
		this.data = data;
	}
}
