package org.yansou.ci.core.model.system;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;
import org.yansou.ci.core.model.AbstractModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 登录日志
 *
 * @author liutiejun
 * @create 2017-05-11 14:50
 */
@Entity
@Table(name = "ci_sign_in_log")
public class SignInLog extends AbstractModel<Long> {

	private static final long serialVersionUID = 6772273793309361915L;

	@Column
	private String username;// 登录用户名

	@Column
	private String password;// 登录密码，保存的时候以MD5加密

	@Column
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date signInTime;// 登录时间

	@Column
	private String signInIp;// 登录ip

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

	public Date getSignInTime() {
		return signInTime;
	}

	public void setSignInTime(Date signInTime) {
		this.signInTime = signInTime;
	}

	public String getSignInIp() {
		return signInIp;
	}

	public void setSignInIp(String signInIp) {
		this.signInIp = signInIp;
	}
}
