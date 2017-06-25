package org.yansou.ci.core.db.model.project;

import org.hibernate.annotations.Type;
import org.yansou.ci.core.db.model.AbstractModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 竞争对手
 *
 * @author liutiejun
 * @create 2017-06-13 20:33
 */
@Entity
@Table(name = "ci_competitor")
public class Competitor extends AbstractModel<Long> {

	private static final long serialVersionUID = 6860634717598077718L;

	@Column
	private String companyName;// 公司名称

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
	private String remarks;// 备注

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
