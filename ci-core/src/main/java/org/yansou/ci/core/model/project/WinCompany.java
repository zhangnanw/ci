package org.yansou.ci.core.model.project;

import org.yansou.ci.core.model.AbstractModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 中标单位信息
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
	private Double winAmount;// 中标单位-中标金额

	@Column
	private Double winPrice;// 中标单位-中标单价

	@Column
	private Double winCapacity;// 中标单位-中标容量，单位：MW（兆瓦）

	@Column
	private String companyName;// 中标单位名称

	public BiddingData getBiddingData() {
		return biddingData;
	}

	public void setBiddingData(BiddingData biddingData) {
		this.biddingData = biddingData;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
