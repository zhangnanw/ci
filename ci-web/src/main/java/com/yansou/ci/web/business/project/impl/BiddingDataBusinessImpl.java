package com.yansou.ci.web.business.project.impl;

import com.yansou.ci.common.datatables.DataTableVo;
import com.yansou.ci.common.page.PageCriteria;
import com.yansou.ci.common.page.Pagination;
import com.yansou.ci.core.model.project.BiddingData;
import com.yansou.ci.core.rest.request.RestRequest;
import com.yansou.ci.core.rest.response.CountResponse;
import com.yansou.ci.core.rest.response.IdResponse;
import com.yansou.ci.core.rest.response.project.BiddingDataArrayResponse;
import com.yansou.ci.core.rest.response.project.BiddingDataPaginationResponse;
import com.yansou.ci.core.rest.response.project.BiddingDataResponse;
import com.yansou.ci.web.business.project.BiddingDataBusiness;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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

		HttpEntity<RestRequest> httpEntity = new HttpEntity<RestRequest>(restRequest);

		BiddingDataResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, BiddingDataResponse
				.class);

		BiddingData result = restResponse.getResult();

		return biddingData;
	}

	@Override
	public BiddingData[] findAll() {
		String requestUrl = "http://" + CI_STORAGE + "/biddingData/find";

		RestRequest restRequest = new RestRequest();

		HttpEntity<RestRequest> httpEntity = new HttpEntity<RestRequest>(restRequest);

		BiddingDataArrayResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity,
				BiddingDataArrayResponse.class);

		BiddingData[] biddingDatas = restResponse.getResult();

		if (biddingDatas == null) {
			biddingDatas = new BiddingData[0];
		}

		return biddingDatas;
	}

	@Override
	public DataTableVo<BiddingData> pagination(HttpServletRequest request) {
		String requestUrl = "http://" + CI_STORAGE + "/biddingData/pagination";

		PageCriteria pageCriteria = new PageCriteria();
		pageCriteria.setCurrentPageNo(1);
		pageCriteria.setPageSize(10);

		RestRequest restRequest = new RestRequest();
		restRequest.setPageCriteria(pageCriteria);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<RestRequest>(restRequest);

		BiddingDataPaginationResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity,
				BiddingDataPaginationResponse.class);

		Pagination<BiddingData> pagination = restResponse.getResult();

		LOG.info("pagination: {}", pagination);

		return null;
	}

	@Override
	public IdResponse save(BiddingData entity) {
		String requestUrl = "http://" + CI_STORAGE + "/biddingData/save";

		RestRequest restRequest = new RestRequest();
		restRequest.setBiddingData(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<RestRequest>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public IdResponse update(BiddingData entity) {
		String requestUrl = "http://" + CI_STORAGE + "/biddingData/update";

		RestRequest restRequest = new RestRequest();
		restRequest.setBiddingData(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<RestRequest>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public CountResponse deleteById(Long[] ids) {
		return null;
	}
}
