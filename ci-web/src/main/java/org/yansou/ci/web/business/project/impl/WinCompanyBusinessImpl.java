package org.yansou.ci.web.business.project.impl;

import org.apache.commons.lang3.ArrayUtils;
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
import org.yansou.ci.common.web.RequestUtils;
import org.yansou.ci.core.db.model.AbstractModel;
import org.yansou.ci.core.db.model.project.BiddingData;
import org.yansou.ci.core.db.model.project.WinCompany;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.CountResponse;
import org.yansou.ci.core.rest.response.IdResponse;
import org.yansou.ci.core.rest.response.project.WinCompanyArrayResponse;
import org.yansou.ci.core.rest.response.project.WinCompanyPaginationResponse;
import org.yansou.ci.core.rest.response.project.WinCompanyResponse;
import org.yansou.ci.web.business.project.WinCompanyBusiness;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liutiejun
 * @create 2017-05-14 0:42
 */
@Component("winCompanyBusiness")
public class WinCompanyBusinessImpl implements WinCompanyBusiness {

	private static final Logger LOG = LogManager.getLogger(WinCompanyBusinessImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public WinCompany findById(Long id) {
		String requestUrl = "http://" + CI_STORAGE + "/winCompany/find";

		WinCompany winCompany = new WinCompany();
		winCompany.setId(id);

		RestRequest restRequest = new RestRequest();
		restRequest.setWinCompany(winCompany);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		WinCompanyResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, WinCompanyResponse.class);

		WinCompany result = restResponse.getResult();

		return result;
	}

	@Override
	public WinCompany[] findAll() {
		String requestUrl = "http://" + CI_STORAGE + "/winCompany/find";

		RestRequest restRequest = new RestRequest();

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		WinCompanyArrayResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity,
				WinCompanyArrayResponse.class);

		WinCompany[] winCompanys = restResponse.getResult();

		if (winCompanys == null) {
			winCompanys = new WinCompany[0];
		}

		return winCompanys;
	}

	@Override
	public DataTablesOutput<WinCompany> pagination(HttpServletRequest request) {
		String requestUrl = "http://" + CI_STORAGE + "/winCompany/pagination";

		DataTablesInput dataTablesInput = DataTablesUtils.parseRequest(request);
		PageCriteria pageCriteria = DataTablesUtils.convert(dataTablesInput);

		updateBiddingDataId(pageCriteria, request);
		updateStatus(pageCriteria);

		LOG.info("pageCriteria: {}", pageCriteria);

		RestRequest restRequest = new RestRequest();
		restRequest.setPageCriteria(pageCriteria);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		WinCompanyPaginationResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity,
				WinCompanyPaginationResponse.class);

		Pagination<WinCompany> pagination = restResponse.getResult();

		LOG.info("pagination: {}", pagination);

		DataTablesOutput<WinCompany> dataTablesOutput = DataTablesUtils.parseResponse(pagination, pageCriteria.getDraw
				(), null);

		LOG.info("dataTableVo: {}", dataTablesOutput);

		return dataTablesOutput;
	}

	private void updateStatus(PageCriteria pageCriteria) {
		String[] values = new String[]{AbstractModel.Status.DELETE.getValue().toString()};

		DataTablesUtils.updateSearchInfo(pageCriteria, "status", values, Integer.class.getTypeName(), SearchInfo
				.SearchOp.NE);
	}

	private void updateBiddingDataId(PageCriteria pageCriteria, HttpServletRequest request) {
		String propertyName = "biddingData.id";

		String value = RequestUtils.getStringParameter(request, propertyName);
		String[] values = new String[]{value};

		DataTablesUtils.updateSearchInfo(pageCriteria, propertyName, values, Long.class.getTypeName(), SearchInfo
				.SearchOp.EQ);
	}

	@Override
	public IdResponse save(WinCompany entity) {
		String requestUrl = "http://" + CI_STORAGE + "/winCompany/save";

		RestRequest restRequest = new RestRequest();
		restRequest.setWinCompany(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public IdResponse save(Long biddingDataId, WinCompany entity) {
		if (biddingDataId == null) {
			IdResponse response = new IdResponse();
			response.exceptionT();

			return response;
		}

		BiddingData biddingData = new BiddingData();
		biddingData.setId(biddingDataId);

		entity.setBiddingData(biddingData);

		return save(entity);
	}

	@Override
	public IdResponse update(WinCompany entity) {
		String requestUrl = "http://" + CI_STORAGE + "/winCompany/update";

		RestRequest restRequest = new RestRequest();
		restRequest.setWinCompany(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public CountResponse update(WinCompany[] entities) {
		return null;
	}

	@Override
	public IdResponse update(Long biddingDataId, WinCompany entity) {
		if (biddingDataId == null) {
			IdResponse response = new IdResponse();
			response.exceptionT();

			return response;
		}

		BiddingData biddingData = new BiddingData();
		biddingData.setId(biddingDataId);

		entity.setBiddingData(biddingData);

		return update(entity);
	}

	@Override
	public CountResponse deleteById(Long[] ids) {
		String requestUrl = "http://" + CI_STORAGE + "/winCompany/delete";

		LOG.info("删除：{}", ArrayUtils.toString(ids));

		RestRequest restRequest = new RestRequest();
		restRequest.setIds(ids);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		CountResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, CountResponse.class);

		return restResponse;
	}
}
