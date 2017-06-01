package org.yansou.ci.core.model.project;

import org.yansou.ci.core.model.AbstractModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 快照信息
 *
 * @author liutiejun
 * @create 2017-05-31 22:03
 */
@Entity
@Table(name = "ci_bidding_snapshot")
public class SnapshotInfo extends AbstractModel<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2129093260684297661L;

	@Column(columnDefinition = "text")
	private String context;

	@Column
	private String snapshotId;// 快照id

	@Column
	private Integer dataType;// 1-BiddingData，2-PlanBuildData

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getSnapshotId() {
		return snapshotId;
	}

	public void setSnapshotId(String snapshotId) {
		this.snapshotId = snapshotId;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}
}
