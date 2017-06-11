package org.yansou.ci.core.db.model.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.yansou.ci.core.db.model.AbstractModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 注册日志
 *
 * @author liutiejun
 * @create 2017-05-11 14:44
 */
@Entity
@Table(name = "ci_sign_up_log")
public class SignUpLog extends AbstractModel<Long> {

	private static final long serialVersionUID = -522039958142564513L;

	@Column
	private String username;// 登录用户名

	@Column
	private String password;// 登录密码，保存的时候以MD5加密

	@Column
	private String nickName;// 真实姓名

	@Column
	private Integer gender;// 性别，0：未知，1：男，2：女

	@Column
	private String phone;// 手机号码

	@Column
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date signUpTime;// 注册时间

	@Column
	private String signUpIp;// 注册ip

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

	public Date getSignUpTime() {
		return signUpTime;
	}

	public void setSignUpTime(Date signUpTime) {
		this.signUpTime = signUpTime;
	}

	public String getSignUpIp() {
		return signUpIp;
	}

	public void setSignUpIp(String signUpIp) {
		this.signUpIp = signUpIp;
	}
}
