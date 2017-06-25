package org.yansou.ci.core.db.model.dict;

import org.yansou.ci.core.db.model.AbstractModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 产品类型
 * <ul>
 * <li>1-单晶硅</li>
 * <li>2-多晶硅</li>
 * <li>3-单晶硅、多晶硅</li>
 * <li>4-未知</li>
 * </ul>
 *
 * @author liutiejun
 * @create 2017-06-25 14:23
 */
@Entity
@Table(name = "ci_product_type_dict")
public class ProductTypeDict extends AbstractModel<Long> {

	private static final long serialVersionUID = -4708502104252902637L;

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
