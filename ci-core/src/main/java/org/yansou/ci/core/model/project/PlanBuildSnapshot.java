package org.yansou.ci.core.model.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.yansou.ci.core.model.AbstractModel;

@Entity
@Table(name = "ci_plan_build_snapshot")
public class PlanBuildSnapshot extends AbstractModel<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8040264519723859712L;
	@Column
	private String snapshotId;// 快照id
	@Column(length = Integer.MAX_VALUE)
	private String context;

	public String getSnapshotId() {
		return snapshotId;
	}

	public void setSnapshotId(String snapshotId) {
		this.snapshotId = snapshotId;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

}
