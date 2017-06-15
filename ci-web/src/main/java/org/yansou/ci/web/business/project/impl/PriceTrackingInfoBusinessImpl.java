package org.yansou.ci.web.business.project.impl;

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
import org.yansou.ci.core.db.model.project.PriceTrackingInfo;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.CountResponse;
import org.yansou.ci.core.rest.response.IdResponse;
import org.yansou.ci.core.rest.response.project.PriceTrackingInfoArrayResponse;
import org.yansou.ci.core.rest.response.project.PriceTrackingInfoPaginationResponse;
import org.yansou.ci.core.rest.response.project.PriceTrackingInfoResponse;
import org.yansou.ci.web.business.project.PriceTrackingInfoBusiness;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liutiejun
 * @create 2017-05-13 22:58
 */
@Component("priceTrackingInfoBusiness")
public class PriceTrackingInfoBusinessImpl implements PriceTrackingInfoBusiness {

	private static final Logger LOG = LogManager.getLogger(PriceTrackingInfoBusinessImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public PriceTrackingInfo findById(Long id) {
		String requestUrl = "http://" + CI_STORAGE + "/priceTrackingInfo/find";

		PriceTrackingInfo priceTrackingInfo = new PriceTrackingInfo();
		priceTrackingInfo.setId(id);

		RestRequest restRequest = new RestRequest();
		restRequest.setPriceTrackingInfo(priceTrackingInfo);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		PriceTrackingInfoResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity,
				PriceTrackingInfoResponse.class);

		PriceTrackingInfo result = restResponse.getResult();

		return result;
	}

	@Override
	public PriceTrackingInfo[] findAll() {
		String requestUrl = "http://" + CI_STORAGE + "/priceTrackingInfo/find";

		RestRequest restRequest = new RestRequest();

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		PriceTrackingInfoArrayResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity,
				PriceTrackingInfoArrayResponse.class);

		PriceTrackingInfo[] priceTrackingInfos = restResponse.getResult();

		if (priceTrackingInfos == null) {
			priceTrackingInfos = new PriceTrackingInfo[0];
		}

		return priceTrackingInfos;
	}

	@Override
	public DataTablesOutput<PriceTrackingInfo> pagination(HttpServletRequest request) {
		String requestUrl = "http://" + CI_STORAGE + "/priceTrackingInfo/pagination";

		DataTablesInput dataTablesInput = DataTablesUtils.parseRequest(request);
		PageCriteria pageCriteria = DataTablesUtils.convert(dataTablesInput);

		LOG.info("pageCriteria: {}", pageCriteria);

		RestRequest restRequest = new RestRequest();
		restRequest.setPageCriteria(pageCriteria);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		PriceTrackingInfoPaginationResponse restResponse = null;
		try {
			restResponse = restTemplate.postForObject(requestUrl, httpEntity, PriceTrackingInfoPaginationResponse
					.class);
		} catch (RestClientException e) {
			LOG.error(e.getMessage(), e);
		}

		Pagination<PriceTrackingInfo> pagination = null;
		if (restResponse != null) {
			pagination = restResponse.getResult();
		}

		if (pagination == null) {
			pagination = new Pagination<>(0L, 10, 1, new PriceTrackingInfo[0]);
		}

		LOG.info("pagination: {}", pagination);

		DataTablesOutput<PriceTrackingInfo> dataTablesOutput = DataTablesUtils.parseResponse(pagination, pageCriteria
				.getDraw(), null);

		return dataTablesOutput;
	}

	@Override
	public IdResponse save(PriceTrackingInfo entity) {
		String requestUrl = "http://" + CI_STORAGE + "/priceTrackingInfo/save";

		RestRequest restRequest = new RestRequest();
		restRequest.setPriceTrackingInfo(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public IdResponse update(PriceTrackingInfo entity) {
		String requestUrl = "http://" + CI_STORAGE + "/priceTrackingInfo/update";

		RestRequest restRequest = new RestRequest();
		restRequest.setPriceTrackingInfo(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public CountResponse deleteById(Long[] ids) {
		String requestUrl = "http://" + CI_STORAGE + "/priceTrackingInfo/delete";

		LOG.info("删除：{}", ArrayUtils.toString(ids));

		RestRequest restRequest = new RestRequest();
		restRequest.setIds(ids);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		CountResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, CountResponse.class);

		return restResponse;
	}
}
