package org.yansou.ci.core.db.model.dict;

import org.yansou.ci.core.db.model.AbstractModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 产品部署类型
 * <ul>
 * <li>1-分布式</li>
 * <li>2-地面电站</li>
 * <li>3-未知</li>
 * </ul>
 *
 * @author liutiejun
 * @create 2017-06-25 16:47
 */
@Entity
@Table(name = "ci_deployment_type_dict")
public class DeploymentTypeDict extends AbstractModel<Long> {

	private static final long serialVersionUID = -5650971889254320914L;

	@Column
	private Integer code;

	@Column
	private String name;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
