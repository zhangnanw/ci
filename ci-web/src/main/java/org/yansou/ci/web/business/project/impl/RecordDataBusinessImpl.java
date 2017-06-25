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
import org.yansou.ci.core.db.constant.Checked;
import org.yansou.ci.core.db.model.project.RecordData;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.CountResponse;
import org.yansou.ci.core.rest.response.IdResponse;
import org.yansou.ci.core.rest.response.project.RecordDataArrayResponse;
import org.yansou.ci.core.rest.response.project.RecordDataPaginationResponse;
import org.yansou.ci.core.rest.response.project.RecordDataResponse;
import org.yansou.ci.web.business.project.RecordDataBusiness;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liutiejun
 * @create 2017-05-13 22:58
 */
@Component("recordDataBusiness")
public class RecordDataBusinessImpl implements RecordDataBusiness {

	private static final Logger LOG = LogManager.getLogger(RecordDataBusinessImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public RecordData findById(Long id) {
		String requestUrl = "http://" + CI_STORAGE + "/recordData/find";

		RecordData recordData = new RecordData();
		recordData.setId(id);

		RestRequest restRequest = new RestRequest();
		restRequest.setRecordData(recordData);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		RecordDataResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, RecordDataResponse.class);

		RecordData result = restResponse.getResult();

		return result;
	}

	@Override
	public RecordData[] findByProjectIdentifie(String projectIdentifie) {
		if (StringUtils.isBlank(projectIdentifie)) {
			return null;
		}

		String requestUrl = "http://" + CI_STORAGE + "/recordData/find";

		RecordData recordData = new RecordData();
		recordData.setProjectIdentifie(projectIdentifie);

		RestRequest restRequest = new RestRequest();
		restRequest.setRecordData(recordData);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		RecordDataArrayResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity,
				RecordDataArrayResponse.class);

		RecordData[] recordDatas = restResponse.getResult();

		if (recordDatas == null) {
			recordDatas = new RecordData[0];
		}

		return recordDatas;
	}

	@Override
	public RecordData[] findAll() {
		String requestUrl = "http://" + CI_STORAGE + "/recordData/find";

		RestRequest restRequest = new RestRequest();

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		RecordDataArrayResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity,
				RecordDataArrayResponse.class);

		RecordData[] recordDatas = restResponse.getResult();

		if (recordDatas == null) {
			recordDatas = new RecordData[0];
		}

		return recordDatas;
	}

	@Override
	public DataTablesOutput<RecordData> pagination(HttpServletRequest request) {
		String requestUrl = "http://" + CI_STORAGE + "/recordData/pagination";

		DataTablesInput dataTablesInput = DataTablesUtils.parseRequest(request);
		PageCriteria pageCriteria = DataTablesUtils.convert(dataTablesInput);

		LOG.info("pageCriteria: {}", pageCriteria);

		RestRequest restRequest = new RestRequest();
		restRequest.setPageCriteria(pageCriteria);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		RecordDataPaginationResponse restResponse = null;
		try {
			restResponse = restTemplate.postForObject(requestUrl, httpEntity, RecordDataPaginationResponse.class);
		} catch (RestClientException e) {
			LOG.error(e.getMessage(), e);
		}

		Pagination<RecordData> pagination = null;
		if (restResponse != null) {
			pagination = restResponse.getResult();
		}

		if (pagination == null) {
			pagination = new Pagination<>(0L, 10, 1, new RecordData[0]);
		}

		LOG.info("pagination: {}", pagination);

		DataTablesOutput<RecordData> dataTablesOutput = DataTablesUtils.parseResponse(pagination, pageCriteria.getDraw
				(), null);

		return dataTablesOutput;
	}

	@Override
	public IdResponse save(RecordData entity) {
		String requestUrl = "http://" + CI_STORAGE + "/recordData/save";

		RestRequest restRequest = new RestRequest();
		restRequest.setRecordData(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public IdResponse update(RecordData entity) {
		String requestUrl = "http://" + CI_STORAGE + "/recordData/update";

		RestRequest restRequest = new RestRequest();
		restRequest.setRecordData(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public CountResponse update(RecordData[] entities) {
		String requestUrl = "http://" + CI_STORAGE + "/recordData/update";

		RestRequest restRequest = new RestRequest();
		restRequest.setRecordDatas(entities);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		CountResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, CountResponse.class);

		return restResponse;
	}

	@Override
	public CountResponse updateChecked(String projectIdentifie, Long[] ids) {
		if (StringUtils.isBlank(projectIdentifie) || ArrayUtils.isEmpty(ids)) {
			return null;
		}

		RecordData[] entities = findByProjectIdentifie(projectIdentifie);
		if (ArrayUtils.isEmpty(entities)) {
			return null;
		}

		Map<Long, Integer> idMap = new HashMap<>();
		Arrays.stream(ids).forEach(id -> idMap.put(id, 0));

		Arrays.stream(entities).forEach(recordData -> {
			if (idMap.containsKey(recordData.getId())) {
				recordData.setChecked(Checked.RIGHT.getValue());
			} else {
				recordData.setChecked(Checked.WRONG.getValue());
			}
		});

		return update(entities);
	}

	@Override
	public CountResponse deleteById(Long[] ids) {
		String requestUrl = "http://" + CI_STORAGE + "/recordData/delete";

		LOG.info("删除：{}", ArrayUtils.toString(ids));

		RestRequest restRequest = new RestRequest();
		restRequest.setIds(ids);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		CountResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, CountResponse.class);

		return restResponse;
	}
}
