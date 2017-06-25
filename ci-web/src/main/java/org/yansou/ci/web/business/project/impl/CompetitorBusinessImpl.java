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
import org.yansou.ci.core.db.model.project.Competitor;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.CountResponse;
import org.yansou.ci.core.rest.response.IdResponse;
import org.yansou.ci.core.rest.response.project.CompetitorArrayResponse;
import org.yansou.ci.core.rest.response.project.CompetitorPaginationResponse;
import org.yansou.ci.core.rest.response.project.CompetitorResponse;
import org.yansou.ci.web.business.project.CompetitorBusiness;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liutiejun
 * @create 2017-05-13 22:58
 */
@Component("competitorBusiness")
public class CompetitorBusinessImpl implements CompetitorBusiness {

	private static final Logger LOG = LogManager.getLogger(CompetitorBusinessImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public Competitor findById(Long id) {
		String requestUrl = "http://" + CI_STORAGE + "/competitor/find";

		Competitor competitor = new Competitor();
		competitor.setId(id);

		RestRequest restRequest = new RestRequest();
		restRequest.setCompetitor(competitor);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		CompetitorResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, CompetitorResponse.class);

		Competitor result = restResponse.getResult();

		return result;
	}

	@Override
	public Competitor[] findAll() {
		String requestUrl = "http://" + CI_STORAGE + "/competitor/find";

		RestRequest restRequest = new RestRequest();

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		CompetitorArrayResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity,
				CompetitorArrayResponse.class);

		Competitor[] competitors = restResponse.getResult();

		if (competitors == null) {
			competitors = new Competitor[0];
		}

		return competitors;
	}

	@Override
	public DataTablesOutput<Competitor> pagination(HttpServletRequest request) {
		String requestUrl = "http://" + CI_STORAGE + "/competitor/pagination";

		DataTablesInput dataTablesInput = DataTablesUtils.parseRequest(request);
		PageCriteria pageCriteria = DataTablesUtils.convert(dataTablesInput);

		LOG.info("pageCriteria: {}", pageCriteria);

		RestRequest restRequest = new RestRequest();
		restRequest.setPageCriteria(pageCriteria);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		CompetitorPaginationResponse restResponse = null;
		try {
			restResponse = restTemplate.postForObject(requestUrl, httpEntity, CompetitorPaginationResponse.class);
		} catch (RestClientException e) {
			LOG.error(e.getMessage(), e);
		}

		Pagination<Competitor> pagination = null;
		if (restResponse != null) {
			pagination = restResponse.getResult();
		}

		if (pagination == null) {
			pagination = new Pagination<>(0L, 10, 1, new Competitor[0]);
		}

		LOG.info("pagination: {}", pagination);

		DataTablesOutput<Competitor> dataTablesOutput = DataTablesUtils.parseResponse(pagination, pageCriteria.getDraw
				(), null);

		return dataTablesOutput;
	}

	@Override
	public IdResponse save(Competitor entity) {
		String requestUrl = "http://" + CI_STORAGE + "/competitor/save";

		RestRequest restRequest = new RestRequest();
		restRequest.setCompetitor(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public IdResponse update(Competitor entity) {
		String requestUrl = "http://" + CI_STORAGE + "/competitor/update";

		RestRequest restRequest = new RestRequest();
		restRequest.setCompetitor(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public CountResponse update(Competitor[] entities) {
		String requestUrl = "http://" + CI_STORAGE + "/competitor/update";

		RestRequest restRequest = new RestRequest();
		restRequest.setCompetitors(entities);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		CountResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, CountResponse.class);

		return restResponse;
	}

	@Override
	public CountResponse deleteById(Long[] ids) {
		String requestUrl = "http://" + CI_STORAGE + "/competitor/delete";

		LOG.info("删除：{}", ArrayUtils.toString(ids));

		RestRequest restRequest = new RestRequest();
		restRequest.setIds(ids);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		CountResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, CountResponse.class);

		return restResponse;
	}
}
