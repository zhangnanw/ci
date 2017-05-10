package com.yansou.ci.core.model.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yansou.ci.core.model.AbstractModel;
import org.springframework.format.annotation.DateTimeFormat;

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
@Table(name = "ci_bidding_data_info")
public class BiddingDataInfo extends AbstractModel<Long> {

	private static final long serialVersionUID = -8168998504697731751L;

	@Column
	private String projectName;// 项目名称（工程名称）

	@Column
	private String[] projectCodes;// 项目编码，由于备案信息、招中标信息中的项目编码可能不一致，可能有多个值

	@Column
	private String projectIdentifie;// 项目唯一标识

	@Column
	private Double projectScale;// 项目规模（总采购容量），单位：MW（兆瓦）

	@Column
	private Long projectCost;// 项目造价，单位：元

	@Column
	private Long projectTotalInvestment;// 项目总投资，单位：元

	@Column
	private String projectDescription;// 项目描述

	@Column
	private String projectAddress;// 项目详细地址

	@Column
	private String projectProvince;// 项目地址，省

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

	@Column
	private Integer purchasingMethod;// 采购方式，公开招标、竞争性谈判、市场询价、单一来源、其它

	@Column
	private String monocrystallineSpecification;// 单晶硅规格

	@Column
	private Double monocrystallineCapacity;// 单晶硅的采购容量，单位：MW（兆瓦）

	@Column
	private String polysiliconSpecification;// 多晶硅规格

	@Column
	private Double polysiliconCapacity;// 多晶硅的采购容量，单位：MW（兆瓦）

	@Column
	private Integer deploymentType;// 产品的部署类型，分布式、集中式、渔光、农光，需要乐叶确定

	@Column
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date biddingTime;// 招标时间

	@Column
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date winTime;// 中标时间

	@Column
	private Double biddingBudget;// 招标预算

	@Column
	private Double winAmount;// 中标金额

	@Column
	private Double winPrice;// 中标单价

	@Column
	private Double winCapacity;// 中标容量，单位：MW（兆瓦）

	@Column
	private String winCompany;// 中标单位

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
	private String remarks;// 备注

	@Column
	private String url;// 数据的原始地址

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

	public Long getProjectCost() {
		return projectCost;
	}

	public void setProjectCost(Long projectCost) {
		this.projectCost = projectCost;
	}

	public Long getProjectTotalInvestment() {
		return projectTotalInvestment;
	}

	public void setProjectTotalInvestment(Long projectTotalInvestment) {
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

	public String getProjectProvince() {
		return projectProvince;
	}

	public void setProjectProvince(String projectProvince) {
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

	public String getMonocrystallineSpecification() {
		return monocrystallineSpecification;
	}

	public void setMonocrystallineSpecification(String monocrystallineSpecification) {
		this.monocrystallineSpecification = monocrystallineSpecification;
	}

	public Double getMonocrystallineCapacity() {
		return monocrystallineCapacity;
	}

	public void setMonocrystallineCapacity(Double monocrystallineCapacity) {
		this.monocrystallineCapacity = monocrystallineCapacity;
	}

	public String getPolysiliconSpecification() {
		return polysiliconSpecification;
	}

	public void setPolysiliconSpecification(String polysiliconSpecification) {
		this.polysiliconSpecification = polysiliconSpecification;
	}

	public Double getPolysiliconCapacity() {
		return polysiliconCapacity;
	}

	public void setPolysiliconCapacity(Double polysiliconCapacity) {
		this.polysiliconCapacity = polysiliconCapacity;
	}

	public Integer getDeploymentType() {
		return deploymentType;
	}

	public void setDeploymentType(Integer deploymentType) {
		this.deploymentType = deploymentType;
	}

	public Date getBiddingTime() {
		return biddingTime;
	}

	public void setBiddingTime(Date biddingTime) {
		this.biddingTime = biddingTime;
	}

	public Date getWinTime() {
		return winTime;
	}

	public void setWinTime(Date winTime) {
		this.winTime = winTime;
	}

	public Double getBiddingBudget() {
		return biddingBudget;
	}

	public void setBiddingBudget(Double biddingBudget) {
		this.biddingBudget = biddingBudget;
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

	public String getWinCompany() {
		return winCompany;
	}

	public void setWinCompany(String winCompany) {
		this.winCompany = winCompany;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}















































