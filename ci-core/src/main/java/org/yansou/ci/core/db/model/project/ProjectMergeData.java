package org.yansou.ci.core.db.model.project;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
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
	@OneToMany
	private Set<ProjectInfo> projects = new HashSet<>();

	public Set<ProjectInfo> getProjectInfo() {
		return projects;
	}

	public void setProjectInfo(Set<ProjectInfo> projects) {
		this.projects = projects;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this, 4);
	}
}
