package org.yansou.ci.web.business.project.impl;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
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
import org.yansou.ci.common.utils.DateFormater;
import org.yansou.ci.common.web.RequestUtils;
import org.yansou.ci.core.db.constant.Checked;
import org.yansou.ci.core.db.model.AbstractModel;
import org.yansou.ci.core.db.model.project.PlanBuildData;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.CountResponse;
import org.yansou.ci.core.rest.response.IdResponse;
import org.yansou.ci.core.rest.response.project.PlanBuildDataArrayResponse;
import org.yansou.ci.core.rest.response.project.PlanBuildDataPaginationResponse;
import org.yansou.ci.core.rest.response.project.PlanBuildDataResponse;
import org.yansou.ci.web.business.project.PlanBuildDataBusiness;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.yansou.ci.common.utils.DateFormater.STANDARD_DATE_PATTERN;

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
	public PlanBuildData[] findByProjectIdentifie(String projectIdentifie) {
		if (StringUtils.isBlank(projectIdentifie)) {
			return null;
		}

		String requestUrl = "http://" + CI_STORAGE + "/planBuildData/find";

		PlanBuildData planBuildData = new PlanBuildData();
		planBuildData.setProjectIdentifie(projectIdentifie);

		RestRequest restRequest = new RestRequest();
		restRequest.setPlanBuildData(planBuildData);

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
	public DataTablesOutput<PlanBuildData> pagination(HttpServletRequest request) {
		String requestUrl = "http://" + CI_STORAGE + "/planBuildData/pagination";

		DataTablesInput dataTablesInput = DataTablesUtils.parseRequest(request);
		PageCriteria pageCriteria = DataTablesUtils.convert(dataTablesInput);

		updateProjectProvince(pageCriteria);
		updateStatus(pageCriteria);
		updatePublishTime(pageCriteria, request);

		LOG.info("pageCriteria: {}", pageCriteria);

		RestRequest restRequest = new RestRequest();
		restRequest.setPageCriteria(pageCriteria);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		PlanBuildDataPaginationResponse restResponse = null;
		try {
			restResponse = restTemplate.postForObject(requestUrl, httpEntity, PlanBuildDataPaginationResponse.class);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		Pagination<PlanBuildData> pagination = null;
		if (restResponse != null) {
			pagination = restResponse.getResult();
		}

		if (pagination == null) {
			pagination = new Pagination<>(0L, 10, 1, new PlanBuildData[0]);
		}

		LOG.info("pagination: {}", pagination);

		DataTablesOutput<PlanBuildData> dataTablesOutput = DataTablesUtils.parseResponse(pagination, pageCriteria
				.getDraw(), null);

		LOG.info("dataTablesOutput: {}", dataTablesOutput);

		return dataTablesOutput;
	}

	private void updateStatus(PageCriteria pageCriteria) {
		String[] values = new String[]{AbstractModel.Status.DELETE.getValue().toString()};

		DataTablesUtils.updateSearchInfo(pageCriteria, "status", values, Integer.class.getTypeName(), SearchInfo
				.SearchOp.NE);
	}

	private void updateProjectProvince(PageCriteria pageCriteria) {
		DataTablesUtils.updateSearchInfo(pageCriteria, "projectProvince", null, Integer.class.getTypeName(),
				SearchInfo.SearchOp.EQ);
	}

	private void updatePublishTime(PageCriteria pageCriteria, HttpServletRequest request) {
		String publishStartTime = RequestUtils.getStringParameter(request, "publishStartTime");
		String publishEndTime = RequestUtils.getStringParameter(request, "publishEndTime");

		if (StringUtils.isBlank(publishStartTime) && StringUtils.isBlank(publishEndTime)) {
			return;
		}

		if (StringUtils.isBlank(publishStartTime)) {
			publishStartTime = "1970-01-01";
		}

		if (StringUtils.isBlank(publishEndTime)) {
			publishEndTime = DateFormater.format(new Date(), STANDARD_DATE_PATTERN);
		}

		String[] values = new String[]{publishStartTime, publishEndTime};

		DataTablesUtils.updateSearchInfo(pageCriteria, "publishTime", values, Date.class.getTypeName(), SearchInfo
				.SearchOp.BETWEEN);
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
	public CountResponse update(PlanBuildData[] entities) {
		String requestUrl = "http://" + CI_STORAGE + "/planBuildData/update";

		RestRequest restRequest = new RestRequest();
		restRequest.setPlanBuildDatas(entities);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		CountResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, CountResponse.class);

		return restResponse;
	}

	@Override
	public CountResponse updateChecked(String projectIdentifie, Long[] ids) {
		if (StringUtils.isBlank(projectIdentifie) || ArrayUtils.isEmpty(ids)) {
			return null;
		}

		PlanBuildData[] entities = findByProjectIdentifie(projectIdentifie);
		if (ArrayUtils.isEmpty(entities)) {
			return null;
		}

		Map<Long, Integer> idMap = new HashMap<>();
		Arrays.stream(ids).forEach(id -> idMap.put(id, 0));

		Arrays.stream(entities).forEach(planBuildData -> {
			if (idMap.containsKey(planBuildData.getId())) {
				planBuildData.setChecked(Checked.RIGHT.getValue());
			} else {
				planBuildData.setChecked(Checked.WRONG.getValue());
			}
		});

		return update(entities);
	}

	@Override
	public CountResponse deleteById(Long[] ids) {
		String requestUrl = "http://" + CI_STORAGE + "/planBuildData/delete";

		LOG.info("删除：{}", ArrayUtils.toString(ids));

		RestRequest restRequest = new RestRequest();
		restRequest.setIds(ids);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		CountResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, CountResponse.class);

		return restResponse;
	}
}
