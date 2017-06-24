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
import org.yansou.ci.core.db.model.project.NewsData;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.CountResponse;
import org.yansou.ci.core.rest.response.IdResponse;
import org.yansou.ci.core.rest.response.project.NewsDataArrayResponse;
import org.yansou.ci.core.rest.response.project.NewsDataPaginationResponse;
import org.yansou.ci.core.rest.response.project.NewsDataResponse;
import org.yansou.ci.web.business.project.NewsDataBusiness;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liutiejun
 * @create 2017-05-13 22:58
 */
@Component("newsDataBusiness")
public class NewsDataBusinessImpl implements NewsDataBusiness {

	private static final Logger LOG = LogManager.getLogger(NewsDataBusinessImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public NewsData findById(Long id) {
		String requestUrl = "http://" + CI_STORAGE + "/newsData/find";

		NewsData newsData = new NewsData();
		newsData.setId(id);

		RestRequest restRequest = new RestRequest();
		restRequest.setNewsData(newsData);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		NewsDataResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, NewsDataResponse.class);

		NewsData result = restResponse.getResult();

		return result;
	}

	@Override
	public NewsData[] findAll() {
		String requestUrl = "http://" + CI_STORAGE + "/newsData/find";

		RestRequest restRequest = new RestRequest();

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		NewsDataArrayResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, NewsDataArrayResponse
				.class);

		NewsData[] newsDatas = restResponse.getResult();

		if (newsDatas == null) {
			newsDatas = new NewsData[0];
		}

		return newsDatas;
	}

	@Override
	public DataTablesOutput<NewsData> pagination(HttpServletRequest request) {
		String requestUrl = "http://" + CI_STORAGE + "/newsData/pagination";

		DataTablesInput dataTablesInput = DataTablesUtils.parseRequest(request);
		PageCriteria pageCriteria = DataTablesUtils.convert(dataTablesInput);

		LOG.info("pageCriteria: {}", pageCriteria);

		RestRequest restRequest = new RestRequest();
		restRequest.setPageCriteria(pageCriteria);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		NewsDataPaginationResponse restResponse = null;
		try {
			restResponse = restTemplate.postForObject(requestUrl, httpEntity, NewsDataPaginationResponse.class);
		} catch (RestClientException e) {
			LOG.error(e.getMessage(), e);
		}

		Pagination<NewsData> pagination = null;
		if (restResponse != null) {
			pagination = restResponse.getResult();
		}

		if (pagination == null) {
			pagination = new Pagination<>(0L, 10, 1, new NewsData[0]);
		}

		LOG.info("pagination: {}", pagination);

		DataTablesOutput<NewsData> dataTablesOutput = DataTablesUtils.parseResponse(pagination, pageCriteria.getDraw()
				, null);

		return dataTablesOutput;
	}

	@Override
	public IdResponse save(NewsData entity) {
		String requestUrl = "http://" + CI_STORAGE + "/newsData/save";

		RestRequest restRequest = new RestRequest();
		restRequest.setNewsData(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public IdResponse update(NewsData entity) {
		String requestUrl = "http://" + CI_STORAGE + "/newsData/update";

		RestRequest restRequest = new RestRequest();
		restRequest.setNewsData(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public CountResponse update(NewsData[] entities) {
		String requestUrl = "http://" + CI_STORAGE + "/newsData/update";

		RestRequest restRequest = new RestRequest();
		restRequest.setNewsDatas(entities);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		CountResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, CountResponse.class);

		return restResponse;
	}

	@Override
	public CountResponse deleteById(Long[] ids) {
		String requestUrl = "http://" + CI_STORAGE + "/newsData/delete";

		LOG.info("删除：{}", ArrayUtils.toString(ids));

		RestRequest restRequest = new RestRequest();
		restRequest.setIds(ids);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		CountResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, CountResponse.class);

		return restResponse;
	}
}
