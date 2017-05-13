package com.yansou.ci.core.model.rest.request;

import com.yansou.ci.common.page.PageCriteria;
import com.yansou.ci.core.model.system.Account;

import java.io.Serializable;

/**
 * API请求参数封装
 *
 * @author liutiejun
 * @create 2017-05-11 0:45
 */
public class RestRequest implements Serializable {

	private static final long serialVersionUID = 2050890240916910075L;

	private PageCriteria pageCriteria;

	private Account account;

	private Account[] accounts;

	public PageCriteria getPageCriteria() {
		return pageCriteria;
	}

	public void setPageCriteria(PageCriteria pageCriteria) {
		this.pageCriteria = pageCriteria;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Account[] getAccounts() {
		return accounts;
	}

	public void setAccounts(Account[] accounts) {
		this.accounts = accounts;
	}
}
