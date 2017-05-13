package com.yansou.ci.core.rest.request;

import com.yansou.ci.common.page.PageCriteria;
import com.yansou.ci.core.model.project.BiddingData;
import com.yansou.ci.core.model.project.MergeData;
import com.yansou.ci.core.model.project.PlanBuildData;
import com.yansou.ci.core.model.project.ProjectInfo;
import com.yansou.ci.core.model.project.RecordData;
import com.yansou.ci.core.model.system.Account;

import java.io.Serializable;

/**
 * API请求参数封装
 *
 * @author liutiejun
 * @create 2017-05-11 0:45
 */
public class RestRequest implements Serializable {

	private static final long serialVersionUID = 2050890240916910075L;

	private PageCriteria pageCriteria;

	private Account account;
	private Account[] accounts;

	private BiddingData biddingData;
	private BiddingData[] biddingDatas;

	private MergeData mergeData;
	private MergeData[] mergeDatas;

	private PlanBuildData planBuildData;
	private PlanBuildData[] planBuildDatas;

	private ProjectInfo projectInfo;
	private ProjectInfo[] projectInfos;

	private RecordData recordData;
	private RecordData[] recordDatas;

	public PageCriteria getPageCriteria() {
		return pageCriteria;
	}

	public void setPageCriteria(PageCriteria pageCriteria) {
		this.pageCriteria = pageCriteria;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Account[] getAccounts() {
		return accounts;
	}

	public void setAccounts(Account[] accounts) {
		this.accounts = accounts;
	}

	public BiddingData getBiddingData() {
		return biddingData;
	}

	public void setBiddingData(BiddingData biddingData) {
		this.biddingData = biddingData;
	}

	public BiddingData[] getBiddingDatas() {
		return biddingDatas;
	}

	public void setBiddingDatas(BiddingData[] biddingDatas) {
		this.biddingDatas = biddingDatas;
	}

	public MergeData getMergeData() {
		return mergeData;
	}

	public void setMergeData(MergeData mergeData) {
		this.mergeData = mergeData;
	}

	public MergeData[] getMergeDatas() {
		return mergeDatas;
	}

	public void setMergeDatas(MergeData[] mergeDatas) {
		this.mergeDatas = mergeDatas;
	}

	public PlanBuildData getPlanBuildData() {
		return planBuildData;
	}

	public void setPlanBuildData(PlanBuildData planBuildData) {
		this.planBuildData = planBuildData;
	}

	public PlanBuildData[] getPlanBuildDatas() {
		return planBuildDatas;
	}

	public void setPlanBuildDatas(PlanBuildData[] planBuildDatas) {
		this.planBuildDatas = planBuildDatas;
	}

	public ProjectInfo getProjectInfo() {
		return projectInfo;
	}

	public void setProjectInfo(ProjectInfo projectInfo) {
		this.projectInfo = projectInfo;
	}

	public ProjectInfo[] getProjectInfos() {
		return projectInfos;
	}

	public void setProjectInfos(ProjectInfo[] projectInfos) {
		this.projectInfos = projectInfos;
	}

	public RecordData getRecordData() {
		return recordData;
	}

	public void setRecordData(RecordData recordData) {
		this.recordData = recordData;
	}

	public RecordData[] getRecordDatas() {
		return recordDatas;
	}

	public void setRecordDatas(RecordData[] recordDatas) {
		this.recordDatas = recordDatas;
	}
}
