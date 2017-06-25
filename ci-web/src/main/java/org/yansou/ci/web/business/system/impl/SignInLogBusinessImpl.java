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
import org.yansou.ci.core.db.model.system.SignInLog;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.CountResponse;
import org.yansou.ci.core.rest.response.IdResponse;
import org.yansou.ci.core.rest.response.system.SignInLogArrayResponse;
import org.yansou.ci.core.rest.response.system.SignInLogPaginationResponse;
import org.yansou.ci.core.rest.response.system.SignInLogResponse;
import org.yansou.ci.web.business.system.SignInLogBusiness;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liutiejun
 * @create 2017-05-13 22:58
 */
@Component("signInLogBusiness")
public class SignInLogBusinessImpl implements SignInLogBusiness {

	private static final Logger LOG = LogManager.getLogger(SignInLogBusinessImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public SignInLog findById(Long id) {
		String requestUrl = "http://" + CI_STORAGE + "/signInLog/find";

		SignInLog signInLog = new SignInLog();
		signInLog.setId(id);

		RestRequest restRequest = new RestRequest();
		restRequest.setSignInLog(signInLog);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		SignInLogResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, SignInLogResponse
				.class);

		SignInLog result = restResponse.getResult();

		return result;
	}

	@Override
	public SignInLog[] findAll() {
		String requestUrl = "http://" + CI_STORAGE + "/signInLog/find";

		RestRequest restRequest = new RestRequest();

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		SignInLogArrayResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity,
				SignInLogArrayResponse.class);

		SignInLog[] signInLogs = restResponse.getResult();

		if (signInLogs == null) {
			signInLogs = new SignInLog[0];
		}

		return signInLogs;
	}

	@Override
	public DataTablesOutput<SignInLog> pagination(HttpServletRequest request) {
		String requestUrl = "http://" + CI_STORAGE + "/signInLog/pagination";

		DataTablesInput dataTablesInput = DataTablesUtils.parseRequest(request);
		PageCriteria pageCriteria = DataTablesUtils.convert(dataTablesInput);

		LOG.info("pageCriteria: {}", pageCriteria);

		RestRequest restRequest = new RestRequest();
		restRequest.setPageCriteria(pageCriteria);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		SignInLogPaginationResponse restResponse = null;
		try {
			restResponse = restTemplate.postForObject(requestUrl, httpEntity, SignInLogPaginationResponse.class);
		} catch (RestClientException e) {
			LOG.error(e.getMessage(), e);
		}

		Pagination<SignInLog> pagination = null;
		if (restResponse != null) {
			pagination = restResponse.getResult();
		}

		if (pagination == null) {
			pagination = new Pagination<>(0L, 10, 1, new SignInLog[0]);
		}

		LOG.info("pagination: {}", pagination);

		DataTablesOutput<SignInLog> dataTablesOutput = DataTablesUtils.parseResponse(pagination, pageCriteria
				.getDraw(), null);

		return dataTablesOutput;
	}

	@Override
	public IdResponse save(SignInLog entity) {
		String requestUrl = "http://" + CI_STORAGE + "/signInLog/save";

		RestRequest restRequest = new RestRequest();
		restRequest.setSignInLog(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public IdResponse update(SignInLog entity) {
		String requestUrl = "http://" + CI_STORAGE + "/signInLog/update";

		RestRequest restRequest = new RestRequest();
		restRequest.setSignInLog(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public CountResponse update(SignInLog[] entities) {
		String requestUrl = "http://" + CI_STORAGE + "/signInLog/update";

		RestRequest restRequest = new RestRequest();
		restRequest.setSignInLogs(entities);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		CountResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, CountResponse.class);

		return restResponse;
	}

	@Override
	public CountResponse deleteById(Long[] ids) {
		String requestUrl = "http://" + CI_STORAGE + "/signInLog/delete";

		LOG.info("删除：{}", ArrayUtils.toString(ids));

		RestRequest restRequest = new RestRequest();
		restRequest.setIds(ids);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		CountResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, CountResponse.class);

		return restResponse;
	}
}
