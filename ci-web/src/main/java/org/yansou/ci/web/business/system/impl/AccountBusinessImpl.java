package org.yansou.ci.web.business.system.impl;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.yansou.ci.common.datatables.mapping.DataTablesInput;
import org.yansou.ci.common.datatables.mapping.DataTablesOutput;
import org.yansou.ci.common.datatables.utils.DataTablesUtils;
import org.yansou.ci.common.page.PageCriteria;
import org.yansou.ci.common.page.Pagination;
import org.yansou.ci.core.db.model.system.Account;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.CountResponse;
import org.yansou.ci.core.rest.response.IdResponse;
import org.yansou.ci.core.rest.response.system.AccountArrayResponse;
import org.yansou.ci.core.rest.response.system.AccountPaginationResponse;
import org.yansou.ci.core.rest.response.system.AccountResponse;
import org.yansou.ci.web.business.system.AccountBusiness;

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

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		AccountResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, AccountResponse.class);

		Account result = restResponse.getResult();

		return result;
	}

	@Override
	public Account[] findAll() {
		String requestUrl = "http://" + CI_STORAGE + "/account/find";

		RestRequest restRequest = new RestRequest();

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		AccountArrayResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, AccountArrayResponse
				.class);

		Account[] accounts = restResponse.getResult();

		if (accounts == null) {
			accounts = new Account[0];
		}

		return accounts;
	}

	@Override
	public DataTablesOutput<Account> pagination(HttpServletRequest request) {
		String requestUrl = "http://" + CI_STORAGE + "/account/pagination";

		DataTablesInput dataTablesInput = DataTablesUtils.parseRequest(request);
		PageCriteria pageCriteria = DataTablesUtils.convert(dataTablesInput);

		LOG.info("pageCriteria: {}", pageCriteria);

		RestRequest restRequest = new RestRequest();
		restRequest.setPageCriteria(pageCriteria);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		AccountPaginationResponse restResponse = null;
		try {
			restResponse = restTemplate.postForObject(requestUrl, httpEntity, AccountPaginationResponse.class);
		} catch (RestClientException e) {
			LOG.error(e.getMessage(), e);
		}

		Pagination<Account> pagination = null;
		if (restResponse != null) {
			pagination = restResponse.getResult();
		}

		if (pagination == null) {
			pagination = new Pagination<>(0L, 10, 1, new Account[0]);
		}

		LOG.info("pagination: {}", pagination);

		DataTablesOutput<Account> dataTablesOutput = DataTablesUtils.parseResponse(pagination, pageCriteria.getDraw(), null);

		return dataTablesOutput;
	}

	@Override
	public IdResponse save(Account entity) {
		String requestUrl = "http://" + CI_STORAGE + "/account/save";

		RestRequest restRequest = new RestRequest();
		restRequest.setAccount(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public IdResponse update(Account entity) {
		String requestUrl = "http://" + CI_STORAGE + "/account/update";

		RestRequest restRequest = new RestRequest();
		restRequest.setAccount(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public CountResponse update(Account[] entities) {
		return null;
	}

	@Override
	public CountResponse deleteById(Long[] ids) {
		String requestUrl = "http://" + CI_STORAGE + "/account/delete";

		LOG.info("删除：{}", ArrayUtils.toString(ids));

		RestRequest restRequest = new RestRequest();
		restRequest.setIds(ids);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		CountResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, CountResponse.class);

		return restResponse;
	}
}
