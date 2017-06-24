package org.yansou.ci.core.rest.report;

/**
 * 图表上需要显示的值
 *
 * @author liutiejun
 * @create 2017-06-20 21:17
 */
public class ReportRo {

	private Axis[] xAxis;// X轴
	private Axis[] yAxis;// Y轴
	private Serie[] series;// 图表上显示的值

	public ReportRo() {
	}

	public ReportRo(Axis[] xAxis, Axis[] yAxis, Serie[] series) {
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.series = series;
	}

	public Axis[] getxAxis() {
		return xAxis;
	}

	public void setxAxis(Axis[] xAxis) {
		this.xAxis = xAxis;
	}

	public Axis[] getyAxis() {
		return yAxis;
	}

	public void setyAxis(Axis[] yAxis) {
		this.yAxis = yAxis;
	}

	public Serie[] getSeries() {
		return series;
	}

	public void setSeries(Serie[] series) {
		this.series = series;
	}
}
