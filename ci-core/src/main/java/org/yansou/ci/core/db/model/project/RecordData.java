package org.yansou.ci.core.db.model.project;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.yansou.ci.core.db.model.AbstractModel;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 备案信息
 *
 * @author liutiejun
 * @create 2017-05-07 13:02
 */
@Entity
@Table(name = "ci_record_data")
public class RecordData extends AbstractModel<Long> {

	private static final long serialVersionUID = 2527788006643445478L;

	@Column
	private String projectName;// 项目名称（工程名称）

	@Column
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date planStartTime;// 计划开工时间

	@Column
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date recordTime;// 备案时间

	@Column
	private String contactInformation;// 联系方式

	@Column
	private String designInstitute;// 设计院

	@Column
	private String approvalNumber;// 审批文号

	@Column
	private String approvalDepartment;// 审批部门

	@Column
	private String approvalMatters;// 审批事项

	@Column
	private String approvalResult;// 审批结果

	@Column
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date approvalTime;// 审批时间

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

	public Date getPlanStartTime() {
		return planStartTime;
	}

	public void setPlanStartTime(Date planStartTime) {
		this.planStartTime = planStartTime;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public String getContactInformation() {
		return contactInformation;
	}

	public void setContactInformation(String contactInformation) {
		this.contactInformation = contactInformation;
	}

	public String getDesignInstitute() {
		return designInstitute;
	}

	public void setDesignInstitute(String designInstitute) {
		this.designInstitute = designInstitute;
	}

	public String getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}

	public String getApprovalDepartment() {
		return approvalDepartment;
	}

	public void setApprovalDepartment(String approvalDepartment) {
		this.approvalDepartment = approvalDepartment;
	}

	public String getApprovalMatters() {
		return approvalMatters;
	}

	public void setApprovalMatters(String approvalMatters) {
		this.approvalMatters = approvalMatters;
	}

	public String getApprovalResult() {
		return approvalResult;
	}

	public void setApprovalResult(String approvalResult) {
		this.approvalResult = approvalResult;
	}

	public Date getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}








































