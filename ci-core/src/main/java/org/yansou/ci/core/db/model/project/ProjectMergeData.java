package org.yansou.ci.core.db.model.project;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.yansou.ci.core.db.model.AbstractModel;

import com.alibaba.fastjson.JSON;

/**
 * 项目合并对象
 * 
 * @author
 *
 */

@Entity
@Table(name = "ci_project_merge_data")
public class ProjectMergeData extends AbstractModel<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7262550976598905588L;
	@OneToOne
	private ProjectInfo projectInfo;
	@OneToMany
	private Set<BiddingData> biddingDataSet = new HashSet<>();
	@OneToMany
	private Set<PlanBuildData> planBuildData = new HashSet<>();

	@Override
	public String toString() {
		return JSON.toJSONString(this, 4);
	}

	public ProjectInfo getProjectInfo() {
		return projectInfo;
	}

	public void setProjectInfo(ProjectInfo projectInfo) {
		this.projectInfo = projectInfo;
	}

	public Set<PlanBuildData> getPlanBuildData() {
		return planBuildData;
	}

	public void setPlanBuildData(Set<PlanBuildData> planBuildData) {
		this.planBuildData = planBuildData;
	}

	public Set<BiddingData> getBiddingDataSet() {
		return biddingDataSet;
	}

	public void setBiddingDataSet(Set<BiddingData> biddingDataSet) {
		this.biddingDataSet = biddingDataSet;
	}
}
