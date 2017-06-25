package org.yansou.ci.core.rest.request;

import org.yansou.ci.common.page.PageCriteria;
import org.yansou.ci.core.db.model.project.BiddingData;
import org.yansou.ci.core.db.model.project.Competitor;
import org.yansou.ci.core.db.model.project.MergeData;
import org.yansou.ci.core.db.model.project.NewsData;
import org.yansou.ci.core.db.model.project.PlanBuildData;
import org.yansou.ci.core.db.model.project.PriceTrackingInfo;
import org.yansou.ci.core.db.model.project.ProjectInfo;
import org.yansou.ci.core.db.model.project.RecordData;
import org.yansou.ci.core.db.model.project.SnapshotInfo;
import org.yansou.ci.core.db.model.project.WinCompany;
import org.yansou.ci.core.db.model.system.Account;
import org.yansou.ci.core.rest.report.ReportParameter;

import java.io.Serializable;

/**
 * API请求参数封装
 *
 * @author liutiejun
 * @create 2017-05-11 0:45
 */
public class RestRequest implements Serializable {

	private static final long serialVersionUID = 2050890240916910075L;

	private Long[] ids;// 用于数据的删除

	private PageCriteria pageCriteria;

	private ReportParameter reportParameter;

	private Account account;
	private Account[] accounts;

	private BiddingData biddingData;
	private BiddingData[] biddingDatas;

	private Competitor competitor;
	private Competitor[] competitors;

	private MergeData mergeData;
	private MergeData[] mergeDatas;

	private NewsData newsData;
	private NewsData[] newsDatas;

	private PlanBuildData planBuildData;
	private PlanBuildData[] planBuildDatas;

	private PriceTrackingInfo priceTrackingInfo;
	private PriceTrackingInfo[] priceTrackingInfos;

	private ProjectInfo projectInfo;
	private ProjectInfo[] projectInfos;

	private RecordData recordData;
	private RecordData[] recordDatas;

	private WinCompany winCompany;
	private WinCompany[] winCompanies;

	private SnapshotInfo snapshotInfo;
	private SnapshotInfo[] snapshotInfos;

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public PageCriteria getPageCriteria() {
		return pageCriteria;
	}

	public void setPageCriteria(PageCriteria pageCriteria) {
		this.pageCriteria = pageCriteria;
	}

	public ReportParameter getReportParameter() {
		return reportParameter;
	}

	public void setReportParameter(ReportParameter reportParameter) {
		this.reportParameter = reportParameter;
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

	public Competitor getCompetitor() {
		return competitor;
	}

	public void setCompetitor(Competitor competitor) {
		this.competitor = competitor;
	}

	public Competitor[] getCompetitors() {
		return competitors;
	}

	public void setCompetitors(Competitor[] competitors) {
		this.competitors = competitors;
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

	public NewsData getNewsData() {
		return newsData;
	}

	public void setNewsData(NewsData newsData) {
		this.newsData = newsData;
	}

	public NewsData[] getNewsDatas() {
		return newsDatas;
	}

	public void setNewsDatas(NewsData[] newsDatas) {
		this.newsDatas = newsDatas;
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

	public PriceTrackingInfo getPriceTrackingInfo() {
		return priceTrackingInfo;
	}

	public void setPriceTrackingInfo(PriceTrackingInfo priceTrackingInfo) {
		this.priceTrackingInfo = priceTrackingInfo;
	}

	public PriceTrackingInfo[] getPriceTrackingInfos() {
		return priceTrackingInfos;
	}

	public void setPriceTrackingInfos(PriceTrackingInfo[] priceTrackingInfos) {
		this.priceTrackingInfos = priceTrackingInfos;
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

	public WinCompany getWinCompany() {
		return winCompany;
	}

	public void setWinCompany(WinCompany winCompany) {
		this.winCompany = winCompany;
	}

	public WinCompany[] getWinCompanies() {
		return winCompanies;
	}

	public void setWinCompanies(WinCompany[] winCompanies) {
		this.winCompanies = winCompanies;
	}

	public SnapshotInfo getSnapshotInfo() {
		return snapshotInfo;
	}

	public void setSnapshotInfo(SnapshotInfo snapshotInfo) {
		this.snapshotInfo = snapshotInfo;
	}

	public SnapshotInfo[] getSnapshotInfos() {
		return snapshotInfos;
	}

	public void setSnapshotInfos(SnapshotInfo[] snapshotInfos) {
		this.snapshotInfos = snapshotInfos;
	}
}
