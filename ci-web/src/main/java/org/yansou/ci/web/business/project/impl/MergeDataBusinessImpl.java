package org.yansou.ci.web.business.project.impl;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
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
import org.yansou.ci.core.db.model.project.MergeData;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.CountResponse;
import org.yansou.ci.core.rest.response.IdResponse;
import org.yansou.ci.core.rest.response.project.MergeDataArrayResponse;
import org.yansou.ci.core.rest.response.project.MergeDataPaginationResponse;
import org.yansou.ci.core.rest.response.project.MergeDataResponse;
import org.yansou.ci.web.business.project.MergeDataBusiness;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liutiejun
 * @create 2017-05-13 22:58
 */
@Component("mergeDataBusiness")
public class MergeDataBusinessImpl implements MergeDataBusiness {

	private static final Logger LOG = LogManager.getLogger(MergeDataBusinessImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public MergeData findById(Long id) {
		String requestUrl = "http://" + CI_STORAGE + "/mergeData/find";

		MergeData mergeData = new MergeData();
		mergeData.setId(id);

		RestRequest restRequest = new RestRequest();
		restRequest.setMergeData(mergeData);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		MergeDataResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, MergeDataResponse.class);

		MergeData result = restResponse.getResult();

		return result;
	}

	@Override
	public MergeData[] findByProjectIdentifie(String projectIdentifie) {
		if (StringUtils.isBlank(projectIdentifie)) {
			return null;
		}

		String requestUrl = "http://" + CI_STORAGE + "/mergeData/find";

		MergeData mergeData = new MergeData();
		mergeData.setProjectIdentifie(projectIdentifie);

		RestRequest restRequest = new RestRequest();
		restRequest.setMergeData(mergeData);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		MergeDataArrayResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity,
				MergeDataArrayResponse.class);

		MergeData[] mergeDatas = restResponse.getResult();

		if (mergeDatas == null) {
			mergeDatas = new MergeData[0];
		}

		return mergeDatas;
	}

	@Override
	public MergeData[] findAll() {
		String requestUrl = "http://" + CI_STORAGE + "/mergeData/find";

		RestRequest restRequest = new RestRequest();

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		MergeDataArrayResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity,
				MergeDataArrayResponse.class);

		MergeData[] mergeDatas = restResponse.getResult();

		if (mergeDatas == null) {
			mergeDatas = new MergeData[0];
		}

		return mergeDatas;
	}

	@Override
	public DataTablesOutput<MergeData> pagination(HttpServletRequest request) {
		String requestUrl = "http://" + CI_STORAGE + "/mergeData/pagination";

		DataTablesInput dataTablesInput = DataTablesUtils.parseRequest(request);
		PageCriteria pageCriteria = DataTablesUtils.convert(dataTablesInput);

		LOG.info("pageCriteria: {}", pageCriteria);

		RestRequest restRequest = new RestRequest();
		restRequest.setPageCriteria(pageCriteria);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		MergeDataPaginationResponse restResponse = null;
		try {
			restResponse = restTemplate.postForObject(requestUrl, httpEntity, MergeDataPaginationResponse.class);
		} catch (RestClientException e) {
			LOG.error(e.getMessage(), e);
		}

		Pagination<MergeData> pagination = null;
		if (restResponse != null) {
			pagination = restResponse.getResult();
		}

		if (pagination == null) {
			pagination = new Pagination<>(0L, 10, 1, new MergeData[0]);
		}

		LOG.info("pagination: {}", pagination);

		DataTablesOutput<MergeData> dataTablesOutput = DataTablesUtils.parseResponse(pagination, pageCriteria.getDraw
				(), null);

		return dataTablesOutput;
	}

	@Override
	public IdResponse save(MergeData entity) {
		String requestUrl = "http://" + CI_STORAGE + "/mergeData/save";

		RestRequest restRequest = new RestRequest();
		restRequest.setMergeData(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public IdResponse update(MergeData entity) {
		String requestUrl = "http://" + CI_STORAGE + "/mergeData/update";

		RestRequest restRequest = new RestRequest();
		restRequest.setMergeData(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public CountResponse deleteById(Long[] ids) {
		String requestUrl = "http://" + CI_STORAGE + "/mergeData/delete";

		LOG.info("删除：{}", ArrayUtils.toString(ids));

		RestRequest restRequest = new RestRequest();
		restRequest.setIds(ids);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		CountResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, CountResponse.class);

		return restResponse;
	}
}
