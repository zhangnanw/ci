package org.yansou.ci.core.db.model.system;

import org.yansou.ci.core.db.model.AbstractModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 账号信息
 *
 * @author liutiejun
 * @create 2017-04-12 11:46
 */
@Entity
@Table(name = "ci_account")
public class Account extends AbstractModel<Long> {

	private static final long serialVersionUID = 4834791091759200086L;

	@Column
	private String username;// 登录用户名，唯一

	@Column
	private String password;// 登录密码，保存的时候以MD5加密

	@Column
	private String nickName;// 真实姓名

	@Column
	private Integer gender;// 性别，0：未知，1：男，2：女

	@Column
	private String phone;// 手机号码

	@Column
	private Boolean disabled;// true：账号禁用，false：账号可用

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
}
