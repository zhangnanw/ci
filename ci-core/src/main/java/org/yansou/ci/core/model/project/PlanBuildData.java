package org.yansou.ci.core.model.project;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;
import org.yansou.ci.core.model.AbstractModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

/**
 * 拟在建信息
 *
 * @author liutiejun
 * @create 2017-05-07 15:36
 */
@Entity
@Table(name = "ci_plan_build_data", indexes = { @Index(unique = true, columnList = "projectIdentifie") })
public class PlanBuildData extends AbstractModel<Long> {

	private static final long serialVersionUID = -5536829706290917695L;

	@Column
	private String projectName;// 项目名称（工程名称）

	@Column(unique = true)
	private String rowkey;// 源数据的唯一标识

	@Column
	private String[] projectCodes;// 项目编码，由于备案信息、招中标信息中的项目编码可能不一致，可能有多个值

	@Column(unique = true)
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
	private Integer planBuildStatus;// 拟在建项目阶段，由乐叶提供

	@Column
	private String purchaseSituation;// 设备购置情况，直接从RCC中获取

	@Column
	private String designer;// 设计师

	@Column
	private String statusUpdate;// 状态更新

	@Column
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date planStartTime;// 计划开工时间

	@Column
	private String url;// 数据的原始地址

	@Column
	private String snapshotId;// 快照id

	public String getSnapshotId() {
		return snapshotId;
	}

	public void setSnapshotId(String snapshotId) {
		this.snapshotId = snapshotId;
	}

	public String getRowkey() {
		return rowkey;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
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

	public Integer getPlanBuildStatus() {
		return planBuildStatus;
	}

	public void setPlanBuildStatus(Integer planBuildStatus) {
		this.planBuildStatus = planBuildStatus;
	}

	public String getPurchaseSituation() {
		return purchaseSituation;
	}

	public void setPurchaseSituation(String purchaseSituation) {
		this.purchaseSituation = purchaseSituation;
	}

	public String getDesigner() {
		return designer;
	}

	public void setDesigner(String designer) {
		this.designer = designer;
	}

	public String getStatusUpdate() {
		return statusUpdate;
	}

	public void setStatusUpdate(String statusUpdate) {
		this.statusUpdate = statusUpdate;
	}

	public Date getPlanStartTime() {
		return planStartTime;
	}

	public void setPlanStartTime(Date planStartTime) {
		this.planStartTime = planStartTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
