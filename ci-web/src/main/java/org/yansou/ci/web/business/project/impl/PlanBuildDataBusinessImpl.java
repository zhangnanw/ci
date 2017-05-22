package org.yansou.ci.web.business.project.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.yansou.ci.common.datatables.DataTableUtils;
import org.yansou.ci.common.datatables.DataTableVo;
import org.yansou.ci.common.page.PageCriteria;
import org.yansou.ci.common.page.Pagination;
import org.yansou.ci.common.utils.GsonUtils;
import org.yansou.ci.core.model.project.PlanBuildData;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.CountResponse;
import org.yansou.ci.core.rest.response.IdResponse;
import org.yansou.ci.core.rest.response.project.PlanBuildDataArrayResponse;
import org.yansou.ci.core.rest.response.project.PlanBuildDataPaginationResponse;
import org.yansou.ci.core.rest.response.project.PlanBuildDataResponse;
import org.yansou.ci.web.business.project.PlanBuildDataBusiness;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liutiejun
 * @create 2017-05-14 0:42
 */
@Component("planBuildDataBusiness")
public class PlanBuildDataBusinessImpl implements PlanBuildDataBusiness {

	private static final Logger LOG = LogManager.getLogger(PlanBuildDataBusinessImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public PlanBuildData findById(Long id) {
		String requestUrl = "http://" + CI_STORAGE + "/planBuildData/find";

		PlanBuildData planBuildData = new PlanBuildData();
		planBuildData.setId(id);

		RestRequest restRequest = new RestRequest();
		restRequest.setPlanBuildData(planBuildData);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		PlanBuildDataResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, PlanBuildDataResponse
				.class);

		PlanBuildData result = restResponse.getResult();

		return result;
	}

	@Override
	public PlanBuildData[] findAll() {
		String requestUrl = "http://" + CI_STORAGE + "/planBuildData/find";

		RestRequest restRequest = new RestRequest();

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		PlanBuildDataArrayResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity,
				PlanBuildDataArrayResponse.class);

		PlanBuildData[] planBuildDatas = restResponse.getResult();

		if (planBuildDatas == null) {
			planBuildDatas = new PlanBuildData[0];
		}

		return planBuildDatas;
	}

	@Override
	public DataTableVo<PlanBuildData> pagination(HttpServletRequest request) {
		String requestUrl = "http://" + CI_STORAGE + "/planBuildData/pagination";

		PageCriteria pageCriteria = DataTableUtils.parseRequest(request);
		LOG.info("pageCriteria: {}", GsonUtils._gson.toJson(pageCriteria));

		RestRequest restRequest = new RestRequest();
		restRequest.setPageCriteria(pageCriteria);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		PlanBuildDataPaginationResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity,
				PlanBuildDataPaginationResponse.class);

		Pagination<PlanBuildData> pagination = restResponse.getResult();

		LOG.info("pagination: {}", pagination);

		DataTableVo<PlanBuildData> dataTableVo = DataTableUtils.parseResponse(pagination, pageCriteria.getDraw(),
				restResponse.getErrors());

		LOG.info("dataTableVo: {}", dataTableVo);

		return dataTableVo;
	}

	@Override
	public IdResponse save(PlanBuildData entity) {
		String requestUrl = "http://" + CI_STORAGE + "/planBuildData/save";

		RestRequest restRequest = new RestRequest();
		restRequest.setPlanBuildData(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public IdResponse update(PlanBuildData entity) {
		String requestUrl = "http://" + CI_STORAGE + "/planBuildData/update";

		RestRequest restRequest = new RestRequest();
		restRequest.setPlanBuildData(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public CountResponse deleteById(Long[] ids) {
		return null;
	}
}
