package com.yansou.ci.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yansou.ci.common.utils.GsonUtils;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * @author liutiejun
 * @create 2017-05-05 9:46
 */
@MappedSuperclass
public abstract class AbstractModel<ID extends Serializable> implements Serializable {

	private static final long serialVersionUID = -6775115139445614046L;

	public enum Status {
		NORMAL(0), DELETE(1);

		private Integer value;

		Status(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

	@Id
	@GeneratedValue
	private ID id;// 主键

	@Column
	@ColumnDefault("0")
	private Integer status;// 状态，0-正常，1-删除

	@Column
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;// 数据创建时间

	@Column
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;// 数据更新时间

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return GsonUtils._gson.toJson(this);
	}

}
