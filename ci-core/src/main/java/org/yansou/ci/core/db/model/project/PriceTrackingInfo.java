package org.yansou.ci.core.db.model.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.yansou.ci.core.db.model.AbstractModel;

/**
 * 价格追踪信息
 *
 * @author liutiejun
 * @create 2017-06-14 22:25
 */
@Entity
@Table(name = "ci_price_tracking_info")
public class PriceTrackingInfo extends AbstractModel<Long> {

	private static final long serialVersionUID = 1414587990992246199L;

	@Column
	private String projcetOwner;// 甲方、项目业主、开发商、采购人、项目法人

	@Column
	private String purchasingContacts;// 采购联系人

	@Column
	private String purchasingContactPhone;// 采购联系人电话

	@Column
	private Integer ownerType;// 业主类型

	@Column
	private String parentCompany;// 项目业主、开放商、采购人的母公司

	@Column
	private Integer productType;// 产品类型，1-单晶硅，2-多晶硅，3-单晶硅、多晶硅，4-未知

	@Column
	@Type(type = "org.yansou.ci.core.hibernate.usertype.StringArrayType")
	private String[] monocrystallineSpecification;// 单晶硅规格，可能有多个值

	@Column
	@Type(type = "org.yansou.ci.core.hibernate.usertype.DoubleArrayType")
	private Double[] monocrystallineCapacity;// 单晶硅的采购容量，单位：MW（兆瓦）

	@Column
	private Double monocrystallineTotalCapacity;// 单晶硅的总采购容量，单位：MW（兆瓦）

	@Column
	@Type(type = "org.yansou.ci.core.hibernate.usertype.StringArrayType")
	private String[] polysiliconSpecification;// 多晶硅规格，可能有多个值

	@Column
	@Type(type = "org.yansou.ci.core.hibernate.usertype.DoubleArrayType")
	private Double[] polysiliconCapacity;// 多晶硅的采购容量，单位：MW（兆瓦）

	@Column
	private Double polysiliconTotalCapacity;// 多晶硅的总采购容量，单位：MW（兆瓦）

	@Column
	@Type(type = "org.yansou.ci.core.hibernate.usertype.StringArrayType")
	private String[] bidCompanys;// 投标企业

	@Column
	@Type(type = "org.yansou.ci.core.hibernate.usertype.DoubleArrayType")
	private Double[] quotations;// 投标报价，单位：万元

	@Column
	@Type(type = "org.yansou.ci.core.hibernate.usertype.DoubleArrayType")
	private Double[] winAmounts;// 中标金额，单位：万元

	@Column
	@Type(type = "org.yansou.ci.core.hibernate.usertype.DoubleArrayType")
	private Double[] winPrices;// 中标单价 = 中标金额 / 中标容量，单位：元每瓦

	@Column
	@Type(type = "org.yansou.ci.core.hibernate.usertype.DoubleArrayType")
	private Double[] winCapacitys;// 中标容量，单位：MW（兆瓦）

	@Column
	private String remarks;// 备注

	public String getProjcetOwner() {
		return projcetOwner;
	}

	public void setProjcetOwner(String projcetOwner) {
		this.projcetOwner = projcetOwner;
	}

	public String getPurchasingContacts() {
		return purchasingContacts;
	}

	public void setPurchasingContacts(String purchasingContacts) {
		this.purchasingContacts = purchasingContacts;
	}

	public String getPurchasingContactPhone() {
		return purchasingContactPhone;
	}

	public void setPurchasingContactPhone(String purchasingContactPhone) {
		this.purchasingContactPhone = purchasingContactPhone;
	}

	public Integer getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(Integer ownerType) {
		this.ownerType = ownerType;
	}

	public String getParentCompany() {
		return parentCompany;
	}

	public void setParentCompany(String parentCompany) {
		this.parentCompany = parentCompany;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public String[] getMonocrystallineSpecification() {
		return monocrystallineSpecification;
	}

	public void setMonocrystallineSpecification(String[] monocrystallineSpecification) {
		this.monocrystallineSpecification = monocrystallineSpecification;
	}

	public Double[] getMonocrystallineCapacity() {
		return monocrystallineCapacity;
	}

	public void setMonocrystallineCapacity(Double[] monocrystallineCapacity) {
		this.monocrystallineCapacity = monocrystallineCapacity;
	}

	public Double getMonocrystallineTotalCapacity() {
		return monocrystallineTotalCapacity;
	}

	public void setMonocrystallineTotalCapacity(Double monocrystallineTotalCapacity) {
		this.monocrystallineTotalCapacity = monocrystallineTotalCapacity;
	}

	public String[] getPolysiliconSpecification() {
		return polysiliconSpecification;
	}

	public void setPolysiliconSpecification(String[] polysiliconSpecification) {
		this.polysiliconSpecification = polysiliconSpecification;
	}

	public Double[] getPolysiliconCapacity() {
		return polysiliconCapacity;
	}

	public void setPolysiliconCapacity(Double[] polysiliconCapacity) {
		this.polysiliconCapacity = polysiliconCapacity;
	}

	public Double getPolysiliconTotalCapacity() {
		return polysiliconTotalCapacity;
	}

	public void setPolysiliconTotalCapacity(Double polysiliconTotalCapacity) {
		this.polysiliconTotalCapacity = polysiliconTotalCapacity;
	}

	public String[] getBidCompanys() {
		return bidCompanys;
	}

	public void setBidCompanys(String[] bidCompanys) {
		this.bidCompanys = bidCompanys;
	}

	public Double[] getQuotations() {
		return quotations;
	}

	public void setQuotations(Double[] quotations) {
		this.quotations = quotations;
	}

	public Double[] getWinAmounts() {
		return winAmounts;
	}

	public void setWinAmounts(Double[] winAmounts) {
		this.winAmounts = winAmounts;
	}

	public Double[] getWinPrices() {
		return winPrices;
	}

	public void setWinPrices(Double[] winPrices) {
		this.winPrices = winPrices;
	}

	public Double[] getWinCapacitys() {
		return winCapacitys;
	}

	public void setWinCapacitys(Double[] winCapacitys) {
		this.winCapacitys = winCapacitys;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
