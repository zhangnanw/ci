package org.yansou.ci.core.db.model.dict;

import org.yansou.ci.core.db.model.AbstractModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 省份字典表
 *
 * @author liutiejun
 * @create 2017-06-24 12:24
 */
@Entity
@Table(name = "ci_province_dict")
public class ProvinceDict extends AbstractModel<Long> {

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
