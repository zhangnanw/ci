package com.yansou.ci.core.model.rest.request;

import com.yansou.ci.core.model.system.User;

import java.io.Serializable;

/**
 * @author liutiejun
 * @create 2017-05-11 0:45
 */
public class RestRequest implements Serializable {

	private static final long serialVersionUID = 2050890240916910075L;

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
