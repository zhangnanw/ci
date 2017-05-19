package com.yansou.ci.core.model.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.yansou.ci.core.model.AbstractModel;

@Entity
@Table(name = "ci_bidding_snapshot")
public class BiddingSnapshot extends AbstractModel<Long> {
	private static final long serialVersionUID = 592516927867411382L;
	@Column(length = Integer.MAX_VALUE)
	private String context;
	@Column
	private String snapshotId;// 快照id

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
