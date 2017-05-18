package com.yansou.ci.core.model.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.yansou.ci.core.model.AbstractModel;
@Entity
@Table(name = "plan_build_snapshot")
public class PlanBuildSnapshot extends AbstractModel<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8040264519723859712L;
	@Column
	private String context;
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}

}
