package org.yansou.ci.core.db.model.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.yansou.ci.core.db.model.AbstractModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * 中标、投标单位信息
 *
 * @author liutiejun
 * @create 2017-05-14 11:25
 */
@Entity
@Table(name = "ci_win_company")
public class WinCompany extends AbstractModel<Long> {

	private static final long serialVersionUID = 7411998194685782325L;

	@ManyToOne
	@JoinColumn(name = "bidding_data_id")
	private BiddingData biddingData;

	@Column
	private String companyName;// 单位名称

	@Column
	private Double quotation;// 投标报价，单位：万元

	@Column
	private Double winAmount;// 中标单位-中标金额，单位：万元

	@Column
	private Double winPrice;// 中标单位-中标单价 = 中标金额 / 中标容量，单位：元每瓦

	@Column
	private Double winCapacity;// 中标单位-中标容量，单位：MW（兆瓦）

	@Column
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date winTime;// 中标时间

	public BiddingData getBiddingData() {
		return biddingData;
	}

	public void setBiddingData(BiddingData biddingData) {
		this.biddingData = biddingData;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Double getQuotation() {
		return quotation;
	}

	public void setQuotation(Double quotation) {
		this.quotation = quotation;
	}

	public Double getWinAmount() {
		return winAmount;
	}

	public void setWinAmount(Double winAmount) {
		this.winAmount = winAmount;
	}

	public Double getWinPrice() {
		return winPrice;
	}

	public void setWinPrice(Double winPrice) {
		this.winPrice = winPrice;
	}

	public Double getWinCapacity() {
		return winCapacity;
	}

	public void setWinCapacity(Double winCapacity) {
		this.winCapacity = winCapacity;
	}

	public Date getWinTime() {
		return winTime;
	}

	public void setWinTime(Date winTime) {
		this.winTime = winTime;
	}
}
