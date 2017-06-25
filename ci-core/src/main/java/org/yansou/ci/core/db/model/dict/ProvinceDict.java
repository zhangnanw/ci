package org.yansou.ci.core.db.model.dict;

import org.yansou.ci.core.db.model.AbstractModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 省份字典表
 * <ul>
 * <li>1-北京市</li>
 * <li>2-天津市</li>
 * <li>3-上海市</li>
 * <li>4-重庆市</li>
 * <li>5-安徽省</li>
 * <li>6-福建省</li>
 * <li>7-甘肃省</li>
 * <li>8-广东省</li>
 * <li>9-贵州省</li>
 * <li>10-海南省</li>
 * <li>11-河北省</li>
 * <li>12-河南省</li>
 * <li>13-湖北省</li>
 * <li>14-湖南省</li>
 * <li>15-吉林省</li>
 * <li>16-江苏省</li>
 * <li>17-江西省</li>
 * <li>18-辽宁省</li>
 * <li>19-青海省</li>
 * <li>20-山东省</li>
 * <li>21-山西省</li>
 * <li>22-陕西省</li>
 * <li>23-四川省</li>
 * <li>24-云南省</li>
 * <li>25-浙江省</li>
 * <li>26-台湾省</li>
 * <li>27-黑龙江省</li>
 * <li>28-西藏自治区</li>
 * <li>29-内蒙古自治区</li>
 * <li>30-宁夏回族自治区</li>
 * <li>31-广西壮族自治区</li>
 * <li>32-新疆维吾尔自治区</li>
 * <li>33-香港特别行政区</li>
 * <li>34-澳门特别行政区</li>
 * </ul>
 *
 * @author liutiejun
 * @create 2017-06-24 12:24
 */
@Entity
@Table(name = "ci_province_dict")
public class ProvinceDict extends AbstractModel<Long> {

	private static final long serialVersionUID = -3221357699491661981L;

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
