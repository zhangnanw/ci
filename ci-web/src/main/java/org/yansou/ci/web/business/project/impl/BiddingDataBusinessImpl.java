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
import org.yansou.ci.core.db.constant.DataType;
import org.yansou.ci.core.db.constant.ReportType;
import org.yansou.ci.core.db.model.AbstractModel;
import org.yansou.ci.core.db.model.project.BiddingData;
import org.yansou.ci.core.rest.report.ReportParameter;
import org.yansou.ci.core.rest.report.ReportRo;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.CountResponse;
import org.yansou.ci.core.rest.response.IdResponse;
import org.yansou.ci.core.rest.response.ReportResponse;
import org.yansou.ci.core.rest.response.project.BiddingDataArrayResponse;
import org.yansou.ci.core.rest.response.project.BiddingDataPaginationResponse;
import org.yansou.ci.core.rest.response.project.BiddingDataResponse;
import org.yansou.ci.web.business.project.BiddingDataBusiness;

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
	public BiddingData[] findByProjectIdentifie(String projectIdentifie) {
		if (StringUtils.isBlank(projectIdentifie)) {
			return null;
		}

		String requestUrl = "http://" + CI_STORAGE + "/biddingData/find";

		BiddingData biddingData = new BiddingData();
		biddingData.setProjectIdentifie(projectIdentifie);

		RestRequest restRequest = new RestRequest();
		restRequest.setBiddingData(biddingData);

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

		updateProjectProvince(pageCriteria);
		updateDataType(pageCriteria);
		updateProductType(pageCriteria);
		updateDeploymentType(pageCriteria);
		updatePurchasingMethod(pageCriteria);
		updateStatus(pageCriteria);
		updatePublishTime(pageCriteria, request);

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

	private void updateDataType(PageCriteria pageCriteria) {
		DataTablesUtils.updateSearchInfo(pageCriteria, "dataType", null, Integer.class.getTypeName(), SearchInfo
				.SearchOp.EQ);
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
	public CountResponse update(BiddingData[] entities) {
		String requestUrl = "http://" + CI_STORAGE + "/biddingData/update";

		RestRequest restRequest = new RestRequest();
		restRequest.setBiddingDatas(entities);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		CountResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, CountResponse.class);

		return restResponse;
	}

	@Override
	public CountResponse updateChecked(String projectIdentifie, Long[] ids) {
		if (StringUtils.isBlank(projectIdentifie) || ArrayUtils.isEmpty(ids)) {
			return null;
		}

		BiddingData[] entities = findByProjectIdentifie(projectIdentifie);
		if (ArrayUtils.isEmpty(entities)) {
			return null;
		}

		Map<Long, Integer> idMap = new HashMap<>();
		Arrays.stream(ids).forEach(id -> idMap.put(id, 0));

		Arrays.stream(entities).forEach(biddingData -> {
			if (idMap.containsKey(biddingData.getId())) {
				biddingData.setChecked(Checked.RIGHT.getValue());
			} else {
				biddingData.setChecked(Checked.WRONG.getValue());
			}
		});

		return update(entities);
	}

	@Override
	public CountResponse deleteById(Long[] ids) {
		String requestUrl = "http://" + CI_STORAGE + "/biddingData/delete";

		LOG.info("删除：{}", ArrayUtils.toString(ids));

		RestRequest restRequest = new RestRequest();
		restRequest.setIds(ids);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		CountResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, CountResponse.class);

		return restResponse;
	}

	@Override
	public ReportRo statisticsByProjectScaleAndPublishTime(Date startTime, Date endTime, DataType dataType, ReportType
			reportType) {
		String requestUrl = "http://" + CI_STORAGE + "/biddingData/statistics/projectScale/publishTime";

		ReportParameter reportParameter = new ReportParameter();
		reportParameter.setStartTime(startTime);
		reportParameter.setEndTime(endTime);
		reportParameter.setDataType(dataType.getValue());
		reportParameter.setReportType(reportType.getValue());

		RestRequest restRequest = new RestRequest();
		restRequest.setReportParameter(reportParameter);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		ReportResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, ReportResponse.class);

		return restResponse.getResult();
	}

	@Override
	public ReportRo statisticsByProjectScaleAndParentCompany(Date startTime, Date endTime, DataType dataType, ReportType reportType, Integer limit) {
		String requestUrl = "http://" + CI_STORAGE + "/biddingData/statistics/projectScale/parentCompany";

		ReportParameter reportParameter = new ReportParameter();
		reportParameter.setStartTime(startTime);
		reportParameter.setEndTime(endTime);
		reportParameter.setDataType(dataType.getValue());
		reportParameter.setReportType(reportType.getValue());
		reportParameter.setLimit(limit);

		RestRequest restRequest = new RestRequest();
		restRequest.setReportParameter(reportParameter);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		ReportResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, ReportResponse.class);

		return restResponse.getResult();
	}

	@Override
	public ReportRo statisticsByProjectScaleAndProjectProvince(Date startTime, Date endTime, DataType dataType, ReportType reportType, Integer limit) {
		String requestUrl = "http://" + CI_STORAGE + "/biddingData/statistics/projectScale/projectProvince";

		ReportParameter reportParameter = new ReportParameter();
		reportParameter.setStartTime(startTime);
		reportParameter.setEndTime(endTime);
		reportParameter.setDataType(dataType.getValue());
		reportParameter.setReportType(reportType.getValue());
		reportParameter.setLimit(limit);

		RestRequest restRequest = new RestRequest();
		restRequest.setReportParameter(reportParameter);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		ReportResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, ReportResponse.class);

		return restResponse.getResult();
	}

	@Override
	public ReportRo statisticsByCountAndProjectProvince(Date startTime, Date endTime, DataType dataType, ReportType reportType) {
		String requestUrl = "http://" + CI_STORAGE + "/biddingData/statistics/projectProvince";

		ReportParameter reportParameter = new ReportParameter();
		reportParameter.setStartTime(startTime);
		reportParameter.setEndTime(endTime);
		reportParameter.setDataType(dataType.getValue());
		reportParameter.setReportType(reportType.getValue());

		RestRequest restRequest = new RestRequest();
		restRequest.setReportParameter(reportParameter);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		ReportResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, ReportResponse.class);

		return restResponse.getResult();
	}

	@Override
	public ReportRo statisticsByCountAndDeploymentType(Date startTime, Date endTime, DataType dataType, ReportType reportType) {
		String requestUrl = "http://" + CI_STORAGE + "/biddingData/statistics/deploymentType";

		ReportParameter reportParameter = new ReportParameter();
		reportParameter.setStartTime(startTime);
		reportParameter.setEndTime(endTime);
		reportParameter.setDataType(dataType.getValue());
		reportParameter.setReportType(reportType.getValue());

		RestRequest restRequest = new RestRequest();
		restRequest.setReportParameter(reportParameter);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		ReportResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, ReportResponse.class);

		return restResponse.getResult();
	}

	@Override
	public ReportRo statisticsByCountAndProductType(Date startTime, Date endTime, DataType dataType, ReportType reportType) {
		String requestUrl = "http://" + CI_STORAGE + "/biddingData/statistics/productType";

		ReportParameter reportParameter = new ReportParameter();
		reportParameter.setStartTime(startTime);
		reportParameter.setEndTime(endTime);
		reportParameter.setDataType(dataType.getValue());
		reportParameter.setReportType(reportType.getValue());

		RestRequest restRequest = new RestRequest();
		restRequest.setReportParameter(reportParameter);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		ReportResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, ReportResponse.class);

		return restResponse.getResult();
	}

	@Override
	public ReportRo statisticsByCountAndCustomerType(Date startTime, Date endTime, DataType dataType, ReportType reportType) {
		String requestUrl = "http://" + CI_STORAGE + "/biddingData/statistics/customerType";

		ReportParameter reportParameter = new ReportParameter();
		reportParameter.setStartTime(startTime);
		reportParameter.setEndTime(endTime);
		reportParameter.setDataType(dataType.getValue());
		reportParameter.setReportType(reportType.getValue());

		RestRequest restRequest = new RestRequest();
		restRequest.setReportParameter(reportParameter);

		HttpEntity<RestRequest> httpEntity = new HttpEntity<>(restRequest);

		ReportResponse restResponse = restTemplate.postForObject(requestUrl, httpEntity, ReportResponse.class);

		return restResponse.getResult();
	}
}
