package org.yansou.ci.core.rest.report;

import java.util.Date;

/**
 * 统计报表查询参数
 *
 * @author liutiejun
 * @create 2017-06-20 19:51
 */
public class ReportParameter {

	private Date startTime;// 开始时间
	private Date endTime;// 结束时间
	private int limit;// 结果数限制

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
}
