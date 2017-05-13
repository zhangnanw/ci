package com.yansou.ci.web.business.system.impl;

import com.yansou.ci.common.datatables.DataTableVo;
import com.yansou.ci.common.page.PageCriteria;
import com.yansou.ci.common.page.Pagination;
import com.yansou.ci.core.model.system.Account;
import com.yansou.ci.core.rest.request.RestRequest;
import com.yansou.ci.core.rest.response.CountResponse;
import com.yansou.ci.core.rest.response.IdResponse;
import com.yansou.ci.core.rest.response.system.AccountArrayResponse;
import com.yansou.ci.core.rest.response.system.AccountPaginationResponse;
import com.yansou.ci.core.rest.response.system.AccountResponse;
import com.yansou.ci.web.business.system.AccountBusiness;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liutiejun
 * @create 2017-05-13 22:58
 */
@Component("accountBusiness")
public class AccountBusinessImpl implements AccountBusiness {

	private static final Logger LOG = LogManager.getLogger(AccountBusinessImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public Account findById(Long id) {
		String requestUrl = "http://" + CI_STORAGE + "/account/find";

		Account account = new Account();
		account.setId(id);

		RestRequest restRequest = new RestRequest();
		restRequest.setAccount(account);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<RestRequest>(restRequest);

		AccountResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, AccountResponse.class);

		Account result = restResponse.getResult();

		return account;
	}

	@Override
	public Account[] findAll() {
		String requestUrl = "http://" + CI_STORAGE + "/account/find";

		RestRequest restRequest = new RestRequest();

		HttpEntity<RestRequest> httpEntity = new HttpEntity<RestRequest>(restRequest);

		AccountArrayResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, AccountArrayResponse
				.class);

		Account[] accounts = restResponse.getResult();

		if (accounts == null) {
			accounts = new Account[0];
		}

		return accounts;
	}

	@Override
	public DataTableVo<Account> pagination(HttpServletRequest request) {
		String requestUrl = "http://" + CI_STORAGE + "/account/pagination";

		PageCriteria pageCriteria = new PageCriteria();
		pageCriteria.setCurrentPageNo(1);
		pageCriteria.setPageSize(10);

		RestRequest restRequest = new RestRequest();
		restRequest.setPageCriteria(pageCriteria);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<RestRequest>(restRequest);

		AccountPaginationResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity,
				AccountPaginationResponse.class);

		Pagination<Account> pagination = restResponse.getResult();

		LOG.info("pagination: {}", pagination);

		return null;
	}

	@Override
	public IdResponse save(Account entity) {
		String requestUrl = "http://" + CI_STORAGE + "/account/save";

		RestRequest restRequest = new RestRequest();
		restRequest.setAccount(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<RestRequest>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public IdResponse update(Account entity) {
		String requestUrl = "http://" + CI_STORAGE + "/account/update";

		RestRequest restRequest = new RestRequest();
		restRequest.setAccount(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<RestRequest>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public CountResponse deleteById(Long[] ids) {
		return null;
	}
}
