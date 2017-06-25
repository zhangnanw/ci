package org.yansou.ci.core.db.model.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;
import org.yansou.ci.core.db.model.AbstractModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 招标、中标信息
 *
 * @author liutiejun
 * @create 2017-05-07 16:23
 */
@Entity
@Table(name = "ci_bidding_data")
public class BiddingData extends AbstractModel<Long> {

	private static final long serialVersionUID = -8168998504697731751L;

	@Column
	private Integer dataType;// 公告类型，1-招标公告，2-中标公告，3-更正公告，4-废标公告，5-流标公告

	@Column
	private String projectName;// 项目名称（工程名称）

	@Column
	@Type(type = "org.yansou.ci.core.hibernate.usertype.StringArrayType")
	private String[] projectCodes;// 项目编码，由于备案信息、招中标信息中的项目编码可能不一致，可能有多个值

	@Column
	private String projectIdentifie;// 项目唯一标识

	@Column
	private Double projectScale;// 项目规模（总采购容量），单位：MW（兆瓦）

	@Column
	private Double projectCost;// 项目造价，单位：万元

	@Column
	private Double projectTotalInvestment;// 项目总投资，单位：万元

	@Column
	private String projectDescription;// 项目描述

	@Column
	private String projectAddress;// 项目详细地址

	@Column
	private Integer projectProvince;// 项目地址，省

	@Column
	private String projectCity;// 项目地址，市

	@Column
	private String projectDistrict;// 项目地址，区县

	@Column
	private String projcetOwner;// 甲方、项目业主、开发商、采购人、项目法人

	@Column
	private Integer ownerType;// 业主类型

	@Column
	private String parentCompany;// 项目业主、开放商、采购人的母公司

	// 采购方式，1-公开招标，2-竞争性谈判，3-单一来源，4-市场询价，5-邀请招标，6-其他
	@Column
	private Integer purchasingMethod;

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
	private Integer deploymentType;// 产品的部署类型（可能会发生变化），1-分布式、2-集中式、3-渔光、4-农光，需要乐叶确定

	@Column
	private Double biddingBudget;// 招标预算，单位：万元

	@Column
	private Double winTotalAmount;// 中标总金额，单位：万元

	@Column(columnDefinition = "text")
	private String winCompanyInfo;// 中标单位信息，只用于查询

	@Column
	private String fundSource;// 资金来源

	@Column
	private String purchasingContacts;// 采购联系人

	@Column
	private String purchasingContactPhone;// 采购联系人电话

	@Column
	private String agency;// 代理机构

	@Column
	private String agencyContacts;// 代理机构联系人

	@Column
	private String agencyContactPhone;// 代理机构联系人电话

	@Column
	private Integer customerType;// 客户类别，一类客户、二类客户、三类客户、互补企业、设计院、竞争对手，需要乐叶确定

	@Column
	private String reviewers;// 评审专家

	@Column
	private String remarks;// 备注

	@Column
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date biddingTime;// 招标时间

	@Column
	private String biddingTimeYearMonth;// 招标时间-年月

	@Column
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date winTime;// 中标时间

	@Column
	private String winTimeYearMonth;// 中标时间-年月

	@Column
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date publishTime;// 发布时间

	@Column
	private String publishTimeYearMonth;// 发布时间-年月

	@Column
	private String url;// 数据的原始地址

	@Column(columnDefinition = "mediumtext")
	private String htmlSource;// 网页源码

	@Column
	private String snapshotId;//快照id

	@Column
	private Integer checked;// 人工检查状态，0-没有检查，1-检查为识别正确的数据，2-检查为识别错误的数据

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String[] getProjectCodes() {
		return projectCodes;
	}

	public void setProjectCodes(String[] projectCodes) {
		this.projectCodes = projectCodes;
	}

	public String getProjectIdentifie() {
		return projectIdentifie;
	}

	public void setProjectIdentifie(String projectIdentifie) {
		this.projectIdentifie = projectIdentifie;
	}

	public Double getProjectScale() {
		return projectScale;
	}

	public void setProjectScale(Double projectScale) {
		this.projectScale = projectScale;
	}

	public Double getProjectCost() {
		return projectCost;
	}

	public void setProjectCost(Double projectCost) {
		this.projectCost = projectCost;
	}

	public Double getProjectTotalInvestment() {
		return projectTotalInvestment;
	}

	public void setProjectTotalInvestment(Double projectTotalInvestment) {
		this.projectTotalInvestment = projectTotalInvestment;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public String getProjectAddress() {
		return projectAddress;
	}

	public void setProjectAddress(String projectAddress) {
		this.projectAddress = projectAddress;
	}

	public Integer getProjectProvince() {
		return projectProvince;
	}

	public void setProjectProvince(Integer projectProvince) {
		this.projectProvince = projectProvince;
	}

	public String getProjectCity() {
		return projectCity;
	}

	public void setProjectCity(String projectCity) {
		this.projectCity = projectCity;
	}

	public String getProjectDistrict() {
		return projectDistrict;
	}

	public void setProjectDistrict(String projectDistrict) {
		this.projectDistrict = projectDistrict;
	}

	public String getProjcetOwner() {
		return projcetOwner;
	}

	public void setProjcetOwner(String projcetOwner) {
		this.projcetOwner = projcetOwner;
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

	public Integer getPurchasingMethod() {
		return purchasingMethod;
	}

	public void setPurchasingMethod(Integer purchasingMethod) {
		this.purchasingMethod = purchasingMethod;
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

	public Integer getDeploymentType() {
		return deploymentType;
	}

	public void setDeploymentType(Integer deploymentType) {
		this.deploymentType = deploymentType;
	}

	public Double getBiddingBudget() {
		return biddingBudget;
	}

	public void setBiddingBudget(Double biddingBudget) {
		this.biddingBudget = biddingBudget;
	}

	public Double getWinTotalAmount() {
		return winTotalAmount;
	}

	public void setWinTotalAmount(Double winTotalAmount) {
		this.winTotalAmount = winTotalAmount;
	}

	public String getWinCompanyInfo() {
		return winCompanyInfo;
	}

	public void setWinCompanyInfo(String winCompanyInfo) {
		this.winCompanyInfo = winCompanyInfo;
	}

	public String getFundSource() {
		return fundSource;
	}

	public void setFundSource(String fundSource) {
		this.fundSource = fundSource;
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

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getAgencyContacts() {
		return agencyContacts;
	}

	public void setAgencyContacts(String agencyContacts) {
		this.agencyContacts = agencyContacts;
	}

	public String getAgencyContactPhone() {
		return agencyContactPhone;
	}

	public void setAgencyContactPhone(String agencyContactPhone) {
		this.agencyContactPhone = agencyContactPhone;
	}

	public Integer getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}

	public String getReviewers() {
		return reviewers;
	}

	public void setReviewers(String reviewers) {
		this.reviewers = reviewers;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getBiddingTime() {
		return biddingTime;
	}

	public void setBiddingTime(Date biddingTime) {
		this.biddingTime = biddingTime;
	}

	public String getBiddingTimeYearMonth() {
		return biddingTimeYearMonth;
	}

	public void setBiddingTimeYearMonth(String biddingTimeYearMonth) {
		this.biddingTimeYearMonth = biddingTimeYearMonth;
	}

	public Date getWinTime() {
		return winTime;
	}

	public void setWinTime(Date winTime) {
		this.winTime = winTime;
	}

	public String getWinTimeYearMonth() {
		return winTimeYearMonth;
	}

	public void setWinTimeYearMonth(String winTimeYearMonth) {
		this.winTimeYearMonth = winTimeYearMonth;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public String getPublishTimeYearMonth() {
		return publishTimeYearMonth;
	}

	public void setPublishTimeYearMonth(String publishTimeYearMonth) {
		this.publishTimeYearMonth = publishTimeYearMonth;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHtmlSource() {
		return htmlSource;
	}

	public void setHtmlSource(String htmlSource) {
		this.htmlSource = htmlSource;
	}

	public String getSnapshotId() {
		return snapshotId;
	}

	public void setSnapshotId(String snapshotId) {
		this.snapshotId = snapshotId;
	}

	public Integer getChecked() {
		return checked;
	}

	public void setChecked(Integer checked) {
		this.checked = checked;
	}
}















































