package org.yansou.ci.web.business.project.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.yansou.ci.common.datatables.mapping.DataTablesInput;
import org.yansou.ci.common.datatables.mapping.DataTablesOutput;
import org.yansou.ci.common.datatables.utils.DataTablesUtils;
import org.yansou.ci.common.page.PageCriteria;
import org.yansou.ci.common.page.Pagination;
import org.yansou.ci.common.page.SearchInfo;
import org.yansou.ci.core.model.AbstractModel;
import org.yansou.ci.core.model.project.BiddingData;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.CountResponse;
import org.yansou.ci.core.rest.response.IdResponse;
import org.yansou.ci.core.rest.response.project.BiddingDataArrayResponse;
import org.yansou.ci.core.rest.response.project.BiddingDataPaginationResponse;
import org.yansou.ci.core.rest.response.project.BiddingDataResponse;
import org.yansou.ci.web.business.project.BiddingDataBusiness;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liutiejun
 * @create 2017-05-14 0:42
 */
@Component("biddingDataBusiness")
public class BiddingDataBusinessImpl implements BiddingDataBusiness {

	private static final Logger LOG = LogManager.getLogger(BiddingDataBusinessImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public BiddingData findById(Long id) {
		String requestUrl = "http://" + CI_STORAGE + "/biddingData/find";

		BiddingData biddingData = new BiddingData();
		biddingData.setId(id);

		RestRequest restRequest = new RestRequest();
		restRequest.setBiddingData(biddingData);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		BiddingDataResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, BiddingDataResponse
				.class);

		BiddingData result = restResponse.getResult();

		return result;
	}

	@Override
	public BiddingData[] findAll() {
		String requestUrl = "http://" + CI_STORAGE + "/biddingData/find";

		RestRequest restRequest = new RestRequest();

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		BiddingDataArrayResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity,
				BiddingDataArrayResponse.class);

		BiddingData[] biddingDatas = restResponse.getResult();

		if (biddingDatas == null) {
			biddingDatas = new BiddingData[0];
		}

		return biddingDatas;
	}

	@Override
	public DataTablesOutput<BiddingData> pagination(HttpServletRequest request) {
		String requestUrl = "http://" + CI_STORAGE + "/biddingData/pagination";

		DataTablesInput dataTablesInput = DataTablesUtils.parseRequest(request);
		LOG.info("dataTablesInput: {}", dataTablesInput);

		PageCriteria pageCriteria = DataTablesUtils.convert(dataTablesInput);
		updateProductType(pageCriteria);
		updateDeploymentType(pageCriteria);
		updatePurchasingMethod(pageCriteria);

		LOG.info("pageCriteria: {}", pageCriteria);

		RestRequest restRequest = new RestRequest();
		restRequest.setPageCriteria(pageCriteria);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		BiddingDataPaginationResponse restResponse = null;
		try {
			restResponse = restTemplate.postForObject(requestUrl, httpEntity, BiddingDataPaginationResponse.class);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		Pagination<BiddingData> pagination = null;
		if (restResponse != null) {
			pagination = restResponse.getResult();
		}

		if (pagination == null) {
			pagination = new Pagination<>(0L, 10, 1, new BiddingData[0]);
		}

		LOG.info("pagination: {}", pagination);

		DataTablesOutput<BiddingData> dataTablesOutput = DataTablesUtils.parseResponse(pagination, pageCriteria
				.getDraw(), restResponse.getErrors());

		LOG.info("dataTablesOutput: {}", dataTablesOutput);

		return dataTablesOutput;
	}

	private void updateStatus(PageCriteria pageCriteria) {
		DataTablesUtils.updateSearchInfo(pageCriteria, "status", AbstractModel.Status.DELETE.getValue().toString(),
				Integer.class.getTypeName(), SearchInfo.SearchOp.EQ);
	}

	private void updateProductType(PageCriteria pageCriteria) {
		DataTablesUtils.updateSearchInfo(pageCriteria, "productType", null, Integer.class.getTypeName(), SearchInfo
				.SearchOp.EQ);
	}

	private void updateDeploymentType(PageCriteria pageCriteria) {
		DataTablesUtils.updateSearchInfo(pageCriteria, "deploymentType", null, Integer.class.getTypeName(), SearchInfo
				.SearchOp.EQ);
	}

	private void updatePurchasingMethod(PageCriteria pageCriteria) {
		DataTablesUtils.updateSearchInfo(pageCriteria, "purchasingMethod", null, Integer.class.getTypeName(),
				SearchInfo.SearchOp.EQ);
	}

	@Override
	public IdResponse save(BiddingData entity) {
		String requestUrl = "http://" + CI_STORAGE + "/biddingData/save";

		RestRequest restRequest = new RestRequest();
		restRequest.setBiddingData(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public IdResponse update(BiddingData entity) {
		String requestUrl = "http://" + CI_STORAGE + "/biddingData/update";

		RestRequest restRequest = new RestRequest();
		restRequest.setBiddingData(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public CountResponse deleteById(Long[] ids) {
		return null;
	}
}
