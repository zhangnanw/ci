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
import org.yansou.ci.core.model.project.SnapshotInfo;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.CountResponse;
import org.yansou.ci.core.rest.response.IdResponse;
import org.yansou.ci.core.rest.response.project.SnapshotInfoArrayResponse;
import org.yansou.ci.core.rest.response.project.SnapshotInfoPaginationResponse;
import org.yansou.ci.core.rest.response.project.SnapshotInfoResponse;
import org.yansou.ci.web.business.project.SnapshotInfoBusiness;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liutiejun
 * @create 2017-05-31 21:52
 */
@Component("snapshotInfoBusiness")
public class SnapshotInfoBusinessImpl implements SnapshotInfoBusiness {

	private static final Logger LOG = LogManager.getLogger(SnapshotInfoBusinessImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public SnapshotInfo findById(Long id) {
		String requestUrl = "http://" + CI_STORAGE + "/snapshotInfo/find";

		SnapshotInfo snapshotInfo = new SnapshotInfo();
		snapshotInfo.setId(id);

		RestRequest restRequest = new RestRequest();
		restRequest.setSnapshotInfo(snapshotInfo);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		SnapshotInfoResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, SnapshotInfoResponse
				.class);

		SnapshotInfo result = restResponse.getResult();

		return result;
	}

	@Override
	public SnapshotInfo findBySnapshotId(String snapshotId) {
		String requestUrl = "http://" + CI_STORAGE + "/snapshotInfo/find";

		SnapshotInfo snapshotInfo = new SnapshotInfo();
		snapshotInfo.setSnapshotId(snapshotId);

		RestRequest restRequest = new RestRequest();
		restRequest.setSnapshotInfo(snapshotInfo);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		SnapshotInfoResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, SnapshotInfoResponse
				.class);

		SnapshotInfo result = restResponse.getResult();

		return result;
	}

	@Override
	public SnapshotInfo[] findAll() {
		String requestUrl = "http://" + CI_STORAGE + "/snapshotInfo/find";

		RestRequest restRequest = new RestRequest();

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		SnapshotInfoArrayResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity,
				SnapshotInfoArrayResponse.class);

		SnapshotInfo[] snapshotInfos = restResponse.getResult();

		if (snapshotInfos == null) {
			snapshotInfos = new SnapshotInfo[0];
		}

		return snapshotInfos;
	}

	@Override
	public DataTablesOutput<SnapshotInfo> pagination(HttpServletRequest request) {
		String requestUrl = "http://" + CI_STORAGE + "/snapshotInfo/pagination";

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

		SnapshotInfoPaginationResponse restResponse = null;
		try {
			restResponse = restTemplate.postForObject(requestUrl, httpEntity, SnapshotInfoPaginationResponse.class);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		Pagination<SnapshotInfo> pagination = null;
		if (restResponse != null) {
			pagination = restResponse.getResult();
		}

		if (pagination == null) {
			pagination = new Pagination<>(0L, 10, 1, new SnapshotInfo[0]);
		}

		LOG.info("pagination: {}", pagination);

		DataTablesOutput<SnapshotInfo> dataTablesOutput = DataTablesUtils.parseResponse(pagination, pageCriteria.getDraw(), null);

		LOG.info("dataTablesOutput: {}", dataTablesOutput);

		return dataTablesOutput;
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
	public IdResponse save(SnapshotInfo entity) {
		String requestUrl = "http://" + CI_STORAGE + "/snapshotInfo/save";

		RestRequest restRequest = new RestRequest();
		restRequest.setSnapshotInfo(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public IdResponse update(SnapshotInfo entity) {
		String requestUrl = "http://" + CI_STORAGE + "/snapshotInfo/update";

		RestRequest restRequest = new RestRequest();
		restRequest.setSnapshotInfo(entity);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		IdResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, IdResponse.class);

		return restResponse;
	}

	@Override
	public CountResponse deleteById(Long[] ids) {
		return null;
	}
}
