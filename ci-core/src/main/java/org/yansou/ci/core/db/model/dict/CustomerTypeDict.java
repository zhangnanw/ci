package org.yansou.ci.core.db.model.dict;

import org.yansou.ci.core.db.model.AbstractModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 客户类别
 * <ul>
 * <li>1-大客户</li>
 * <li>2-央企</li>
 * <li>3-地方国企</li>
 * <li>4-竞争对手</li>
 * <li>5-其他</li>
 * </ul>
 *
 * @author liutiejun
 * @create 2017-06-25 17:01
 */
@Entity
@Table(name = "ci_customer_type_dict")
public class CustomerTypeDict extends AbstractModel<Long> {

	private static final long serialVersionUID = -6288038991139516197L;

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
