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
	@Column(length = 2000)
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
	private Integer deploymentType;// 产品的部署方式（可能会发生变化）
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date planStartTime;// 计划开工时间

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date approvalTime;// 审批时间

    @Column
    private String remarks;// 备注

    @Column
    private String url;// 数据的原始地址
	@Column(columnDefinition = "mediumtext")
	private String htmlSource;// 网页源码


 

    @Column
    private Integer checked;// 人工检查状态，0-没有检查，1-检查为识别正确的数据，2-检查为识别错误的数据

    @Column
    private String snapshotId;// 快照ID

	public final String getProjectName() {
		return projectName;
	}

	public final void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public final String[] getProjectCodes() {
		return projectCodes;
	}

	public final void setProjectCodes(String[] projectCodes) {
		this.projectCodes = projectCodes;
	}

	public final String getProjectIdentifie() {
		return projectIdentifie;
	}

	public final void setProjectIdentifie(String projectIdentifie) {
		this.projectIdentifie = projectIdentifie;
	}

	public final Double getProjectScale() {
		return projectScale;
	}

	public final void setProjectScale(Double projectScale) {
		this.projectScale = projectScale;
	}

	public final Double getProjectCost() {
		return projectCost;
	}

	public final void setProjectCost(Double projectCost) {
		this.projectCost = projectCost;
	}

	public final Double getProjectTotalInvestment() {
		return projectTotalInvestment;
	}

	public final void setProjectTotalInvestment(Double projectTotalInvestment) {
		this.projectTotalInvestment = projectTotalInvestment;
	}

	public final String getProjectDescription() {
		return projectDescription;
	}

	public final void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public final String getProjectAddress() {
		return projectAddress;
	}

	public final void setProjectAddress(String projectAddress) {
		this.projectAddress = projectAddress;
	}

	public final Integer getProjectProvince() {
		return projectProvince;
	}

	public final void setProjectProvince(Integer projectProvince) {
		this.projectProvince = projectProvince;
	}

	public final String getProjectCity() {
		return projectCity;
	}

	public final void setProjectCity(String projectCity) {
		this.projectCity = projectCity;
	}

	public final String getProjectDistrict() {
		return projectDistrict;
	}

	public final void setProjectDistrict(String projectDistrict) {
		this.projectDistrict = projectDistrict;
	}

	public final String getProjcetOwner() {
		return projcetOwner;
	}

	public final void setProjcetOwner(String projcetOwner) {
		this.projcetOwner = projcetOwner;
	}

	public final Integer getOwnerType() {
		return ownerType;
	}

	public final void setOwnerType(Integer ownerType) {
		this.ownerType = ownerType;
	}

	public final String getParentCompany() {
		return parentCompany;
	}

	public final void setParentCompany(String parentCompany) {
		this.parentCompany = parentCompany;
	}

	public final Integer getProductType() {
		return productType;
	}

	public final void setProductType(Integer productType) {
		this.productType = productType;
	}

	public final String[] getMonocrystallineSpecification() {
		return monocrystallineSpecification;
	}

	public final void setMonocrystallineSpecification(String[] monocrystallineSpecification) {
		this.monocrystallineSpecification = monocrystallineSpecification;
	}

	public final Double[] getMonocrystallineCapacity() {
		return monocrystallineCapacity;
	}

	public final void setMonocrystallineCapacity(Double[] monocrystallineCapacity) {
		this.monocrystallineCapacity = monocrystallineCapacity;
	}

	public final Double getMonocrystallineTotalCapacity() {
		return monocrystallineTotalCapacity;
	}

	public final void setMonocrystallineTotalCapacity(Double monocrystallineTotalCapacity) {
		this.monocrystallineTotalCapacity = monocrystallineTotalCapacity;
	}

	public final String[] getPolysiliconSpecification() {
		return polysiliconSpecification;
	}

	public final void setPolysiliconSpecification(String[] polysiliconSpecification) {
		this.polysiliconSpecification = polysiliconSpecification;
	}

	public final Double[] getPolysiliconCapacity() {
		return polysiliconCapacity;
	}

	public final void setPolysiliconCapacity(Double[] polysiliconCapacity) {
		this.polysiliconCapacity = polysiliconCapacity;
	}

	public final Double getPolysiliconTotalCapacity() {
		return polysiliconTotalCapacity;
	}

	public final void setPolysiliconTotalCapacity(Double polysiliconTotalCapacity) {
		this.polysiliconTotalCapacity = polysiliconTotalCapacity;
	}

	public final Integer getDeploymentType() {
		return deploymentType;
	}

	public final void setDeploymentType(Integer deploymentType) {
		this.deploymentType = deploymentType;
	}

	public final Date getPlanStartTime() {
		return planStartTime;
	}

	public final void setPlanStartTime(Date planStartTime) {
		this.planStartTime = planStartTime;
	}

	public final Date getRecordTime() {
		return recordTime;
	}

	public final void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public final String getContactInformation() {
		return contactInformation;
	}

	public final void setContactInformation(String contactInformation) {
		this.contactInformation = contactInformation;
	}

	public final String getDesignInstitute() {
		return designInstitute;
	}

	public final void setDesignInstitute(String designInstitute) {
		this.designInstitute = designInstitute;
	}

	public final String getApprovalNumber() {
		return approvalNumber;
	}

	public final void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}

	public final String getApprovalDepartment() {
		return approvalDepartment;
	}

	public final void setApprovalDepartment(String approvalDepartment) {
		this.approvalDepartment = approvalDepartment;
	}

	public final String getApprovalMatters() {
		return approvalMatters;
	}

	public final void setApprovalMatters(String approvalMatters) {
		this.approvalMatters = approvalMatters;
	}

	public final String getApprovalResult() {
		return approvalResult;
	}

	public final void setApprovalResult(String approvalResult) {
		this.approvalResult = approvalResult;
	}

	public final Date getApprovalTime() {
		return approvalTime;
	}

	public final void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}

	public final String getRemarks() {
		return remarks;
	}

	public final void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public final String getUrl() {
		return url;
	}

	public final void setUrl(String url) {
		this.url = url;
	}

	public final String getHtmlSource() {
		return htmlSource;
	}

	public final void setHtmlSource(String htmlSource) {
		this.htmlSource = htmlSource;
	}

	public final Integer getChecked() {
		return checked;
	}

	public final void setChecked(Integer checked) {
		this.checked = checked;
	}

	public final String getSnapshotId() {
		return snapshotId;
	}

	public final void setSnapshotId(String snapshotId) {
		this.snapshotId = snapshotId;
	}
    
    
}








































