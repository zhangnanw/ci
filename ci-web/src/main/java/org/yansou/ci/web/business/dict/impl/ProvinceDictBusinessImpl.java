package org.yansou.ci.web.business.dict.impl;

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
import org.yansou.ci.core.db.model.dict.ProvinceDict;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.CountResponse;
import org.yansou.ci.core.rest.response.IdResponse;
import org.yansou.ci.core.rest.response.dict.ProvinceDictArrayResponse;
import org.yansou.ci.core.rest.response.dict.ProvinceDictPaginationResponse;
import org.yansou.ci.core.rest.response.dict.ProvinceDictResponse;
import org.yansou.ci.web.business.dict.ProvinceDictBusiness;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liutiejun
 * @create 2017-05-13 22:58
 */
@Component("provinceDictBusiness")
public class ProvinceDictBusinessImpl implements ProvinceDictBusiness {

	private static final Logger LOG = LogManager.getLogger(ProvinceDictBusinessImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ProvinceDict findById(Long id) {
		String requestUrl = "http://" + CI_STORAGE + "/provinceDict/find";

		ProvinceDict provinceDict = new ProvinceDict();
		provinceDict.setId(id);

		RestRequest restRequest = new RestRequest();
		restRequest.setProvinceDict(provinceDict);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		ProvinceDictResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, ProvinceDictResponse
				.class);

		ProvinceDict result = restResponse.getResult();

		return result;
	}

	@Override
	public ProvinceDict[] findAll() {
		String requestUrl = "http://" + CI_STORAGE + "/provinceDict/find";

		RestRequest restRequest = new RestRequest();

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		ProvinceDictArrayResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity,
				ProvinceDictArrayResponse.class);

		ProvinceDict[] provinceDicts = restResponse.getResult();

		if (provinceDicts == null) {
			provinceDicts = new ProvinceDict[0];
		}

		return provinceDicts;
	}

	@Override
	public DataTablesOutput<ProvinceDict> pagination(HttpServletRequest request) {
		String requestUrl = "http://" + CI_STORAGE + "/provinceDict/pagination";

		DataTablesInput dataTablesInput = DataTablesUtils.parseRequest(request);
		PageCriteria pageCriteria = DataTablesUtils.convert(dataTablesInput);

		LOG.info("pageCriteria: {}", pageCriteria);

		RestRequest restRequest = new RestRequest();
		restRequest.setPageCriteria(pageCriteria);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		ProvinceDictPaginationResponse restResponse = null;
		try {
			restResponse = restTemplate.postForObject(requestUrl, httpEntity, ProvinceDictPaginationResponse.class);
		} catch (RestClientException e) {
			LOG.error(e.getMessage(), e);
		}

		Pagination<ProvinceDict> pagination = null;
		if (restResponse != null) {
			pagination = restResponse.getResult();
		}

		if (pagination == null) {
			pagination = new Pagination<>(0L, 10, 1, new ProvinceDict[0]);
		}

		LOG.info("pagination: {}", pagination);

		DataTablesOutput<ProvinceDict> dataTablesOutput = DataTablesUtils.parseResponse(pagination, pageCriteria
				.getDraw(), null);

		return dataTablesOutput;
	}

	@Override
	public IdResponse save(ProvinceDict entity) {
		String requestUrl = "http://" + CI_STORAGE + "/provinceDict/save";

		RestRequest restRequest = new RestRequest();
		restRequest.setProvinceDict(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public IdResponse update(ProvinceDict entity) {
		String requestUrl = "http://" + CI_STORAGE + "/provinceDict/update";

		RestRequest restRequest = new RestRequest();
		restRequest.setProvinceDict(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public CountResponse update(ProvinceDict[] entities) {
		String requestUrl = "http://" + CI_STORAGE + "/provinceDict/update";

		RestRequest restRequest = new RestRequest();
		restRequest.setProvinceDicts(entities);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		CountResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, CountResponse.class);

		return restResponse;
	}

	@Override
	public CountResponse deleteById(Long[] ids) {
		String requestUrl = "http://" + CI_STORAGE + "/provinceDict/delete";

		LOG.info("删除：{}", ArrayUtils.toString(ids));

		RestRequest restRequest = new RestRequest();
		restRequest.setIds(ids);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		CountResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, CountResponse.class);

		return restResponse;
	}
}
