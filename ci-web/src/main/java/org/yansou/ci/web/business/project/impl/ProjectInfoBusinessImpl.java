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
import org.yansou.ci.core.db.model.project.ProjectInfo;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.CountResponse;
import org.yansou.ci.core.rest.response.IdResponse;
import org.yansou.ci.core.rest.response.project.ProjectInfoArrayResponse;
import org.yansou.ci.core.rest.response.project.ProjectInfoPaginationResponse;
import org.yansou.ci.core.rest.response.project.ProjectInfoResponse;
import org.yansou.ci.web.business.project.ProjectInfoBusiness;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liutiejun
 * @create 2017-05-13 22:58
 */
@Component("projectInfoBusiness")
public class ProjectInfoBusinessImpl implements ProjectInfoBusiness {

	private static final Logger LOG = LogManager.getLogger(ProjectInfoBusinessImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ProjectInfo findById(Long id) {
		String requestUrl = "http://" + CI_STORAGE + "/projectInfo/find";

		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setId(id);

		RestRequest restRequest = new RestRequest();
		restRequest.setProjectInfo(projectInfo);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		ProjectInfoResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, ProjectInfoResponse
				.class);

		ProjectInfo result = restResponse.getResult();

		return result;
	}

	@Override
	public ProjectInfo[] findAll() {
		String requestUrl = "http://" + CI_STORAGE + "/projectInfo/find";

		RestRequest restRequest = new RestRequest();

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		ProjectInfoArrayResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity,
				ProjectInfoArrayResponse.class);

		ProjectInfo[] projectInfos = restResponse.getResult();

		if (projectInfos == null) {
			projectInfos = new ProjectInfo[0];
		}

		return projectInfos;
	}

	@Override
	public DataTablesOutput<ProjectInfo> pagination(HttpServletRequest request) {
		String requestUrl = "http://" + CI_STORAGE + "/projectInfo/pagination";

		DataTablesInput dataTablesInput = DataTablesUtils.parseRequest(request);
		PageCriteria pageCriteria = DataTablesUtils.convert(dataTablesInput);

		LOG.info("pageCriteria: {}", pageCriteria);

		RestRequest restRequest = new RestRequest();
		restRequest.setPageCriteria(pageCriteria);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		ProjectInfoPaginationResponse restResponse = null;
		try {
			restResponse = restTemplate.postForObject(requestUrl, httpEntity, ProjectInfoPaginationResponse.class);
		} catch (RestClientException e) {
			LOG.error(e.getMessage(), e);
		}

		Pagination<ProjectInfo> pagination = null;
		if (restResponse != null) {
			pagination = restResponse.getResult();
		}

		if (pagination == null) {
			pagination = new Pagination<>(0L, 10, 1, new ProjectInfo[0]);
		}

		LOG.info("pagination: {}", pagination);

		DataTablesOutput<ProjectInfo> dataTablesOutput = DataTablesUtils.parseResponse(pagination, pageCriteria
				.getDraw(), null);

		return dataTablesOutput;
	}

	@Override
	public IdResponse save(ProjectInfo entity) {
		String requestUrl = "http://" + CI_STORAGE + "/projectInfo/save";

		RestRequest restRequest = new RestRequest();
		restRequest.setProjectInfo(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public IdResponse update(ProjectInfo entity) {
		String requestUrl = "http://" + CI_STORAGE + "/projectInfo/update";

		RestRequest restRequest = new RestRequest();
		restRequest.setProjectInfo(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public CountResponse deleteById(Long[] ids) {
		String requestUrl = "http://" + CI_STORAGE + "/projectInfo/delete";

		LOG.info("删除：{}", ArrayUtils.toString(ids));

		RestRequest restRequest = new RestRequest();
		restRequest.setIds(ids);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		CountResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, CountResponse.class);

		return restResponse;
	}
}
