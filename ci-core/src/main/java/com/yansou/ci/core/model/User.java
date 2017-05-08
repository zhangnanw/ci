package com.yansou.ci.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author liutiejun
 * @create 2017-04-12 11:46
 */
@Entity
@Table(name = "ci_user")
public class User extends AbstractModel<Long> {

	private static final long serialVersionUID = 4834791091759200086L;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Integer age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}


}
