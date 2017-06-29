package com.yansou.ci.core.model.chart;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yansou.ci.core.model.AbstractModel;

/**
 * 统计图表信息
 * 
 * @author zhangyingying
 *
 */

@Entity
@Table(name = "orm_chart")
public class ChartData extends AbstractModel<Long>{
	
	private static final long serialVersionUID = -658096801078953806L;
	
	@Column(name = "bidd_increment")
	private String biddIncrement; // bidd表增量
	@Column(name = "bidd_count")
	private String biddCount; // bidd表总量
	@Column(name = "bidd_mg_increment")
	private String biddMgIncrement; // merge表增量
	@Column(name = "bidd_mg_count")
	private String biddMgCount; // merge表总量

	public String getBiddIncrement() {
		return biddIncrement;
	}

	public void setBiddIncrement(String biddIncrement) {
		this.biddIncrement = biddIncrement;
	}

	public String getBiddCount() {
		return biddCount;
	}

	public void setBiddCount(String biddCount) {
		this.biddCount = biddCount;
	}

	public String getBiddMgIncrement() {
		return biddMgIncrement;
	}

	public void setBiddMgIncrement(String biddMgIncrement) {
		this.biddMgIncrement = biddMgIncrement;
	}

	public String getBiddMgCount() {
		return biddMgCount;
	}

	public void setBiddMgCount(String biddMgCount) {
		this.biddMgCount = biddMgCount;
	}

}
