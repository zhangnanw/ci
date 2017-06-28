package org.yansou.ci.storage.controller.project;

import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yansou.ci.common.page.PageCriteria;
import org.yansou.ci.common.page.Pagination;
import org.yansou.ci.core.db.model.project.BiddingData;
import org.yansou.ci.core.rest.report.ReportParameter;
import org.yansou.ci.core.rest.report.ReportRo;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.SimpleRestResponse;
import org.yansou.ci.storage.service.project.BiddingDataService;

import java.util.Date;
import java.util.List;

/**
 * @author liutiejun
 * @create 2017-05-14 0:28
 */
@RestController
@RequestMapping(value = "/biddingData")
public class BiddingDataController {

	private static final Logger LOG = LogManager.getLogger(BiddingDataController.class);

	@Autowired
	private BiddingDataService biddingDataService;

	@ApiOperation(value = "分页获取数据详细信息")
	@PostMapping(value = "/pagination")
	public SimpleRestResponse pagination(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		PageCriteria pageCriteria = restRequest.getPageCriteria();

		Pagination<BiddingData> pagination = biddingDataService.pagination(pageCriteria);

		return SimpleRestResponse.ok(pagination);
	}

	@ApiOperation(value = "获取数据详细信息")
	@PostMapping(value = "/find")
	public SimpleRestResponse find(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		BiddingData biddingData = restRequest.getBiddingData();

		if (biddingData == null) {// 查询所有的数据
			List<BiddingData> biddingDataList = biddingDataService.findAll();

			return SimpleRestResponse.ok(biddingDataList.toArray(new BiddingData[0]));
		}

		Long id = biddingData.getId();
		if (id != null) {// 根据ID查询
			BiddingData otherBiddingData = biddingDataService.findById(id);

			LOG.info("biddingData: {}", otherBiddingData);

			return SimpleRestResponse.ok(otherBiddingData);
		}

		String projectIdentifie = biddingData.getProjectIdentifie();// 项目唯一标识
		if (StringUtils.isNotBlank(projectIdentifie)) {
			List<BiddingData> biddingDataList = biddingDataService.findByProjectIdentifie(projectIdentifie);

			return SimpleRestResponse.ok(biddingDataList.toArray(new BiddingData[0]));
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "新增数据")
	@PostMapping(value = "/save")
	public SimpleRestResponse save(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		BiddingData biddingData = restRequest.getBiddingData();

		LOG.info("biddingData: {}", biddingData);

		if (biddingData != null) {// 单个新增
			biddingData = biddingDataService.save(biddingData);

			return SimpleRestResponse.id(biddingData.getId());
		}

		BiddingData[] biddingDatas = restRequest.getBiddingDatas();
		if (ArrayUtils.isNotEmpty(biddingDatas)) {// 批量新增
			biddingDatas = biddingDataService.save(biddingDatas);

			return SimpleRestResponse.ok(biddingDatas);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "更新数据")
	@PostMapping(value = "/update")
	public SimpleRestResponse update(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		BiddingData biddingData = restRequest.getBiddingData();
		if (biddingData != null) {// 单个更新
			biddingDataService.updateNotNullField(biddingData);

			return SimpleRestResponse.id(biddingData.getId());
		}

		BiddingData[] biddingDatas = restRequest.getBiddingDatas();
		if (ArrayUtils.isNotEmpty(biddingDatas)) {// 批量更新
			int count = biddingDataService.updateNotNullField(biddingDatas);

			return SimpleRestResponse.ok("count", count);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "删除数据，不做物理删除，只更新对应的数据状态")
	@PostMapping(value = "/delete")
	public SimpleRestResponse delete(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		Long[] ids = restRequest.getIds();

		int count = biddingDataService.deleteById(ids);

		return SimpleRestResponse.ok("count", count);
	}

	@ApiOperation(value = "报表统计-招标总量分析")
	@PostMapping(value = "/statistics/projectScale/publishTime")
	public SimpleRestResponse statisticsByProjectScaleAndPublishTime(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		ReportParameter reportParameter = restRequest.getReportParameter();

		Date startTime = reportParameter.getStartTime();
		Date endTime = reportParameter.getEndTime();
		Integer dataType = reportParameter.getDataType();
		Integer reportType = reportParameter.getReportType();

		ReportRo ReportRo = biddingDataService.statisticsByProjectScaleAndPublishTime(startTime, endTime,
				dataType, reportType);

		return SimpleRestResponse.ok(ReportRo);
	}

	@ApiOperation(value = "报表统计-招标总量（MW）公司排名")
	@PostMapping(value = "/statistics/projectScale/parentCompany")
	public SimpleRestResponse statisticsByProjectScaleAndParentCompany(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		ReportParameter reportParameter = restRequest.getReportParameter();

		Date startTime = reportParameter.getStartTime();
		Date endTime = reportParameter.getEndTime();
		Integer dataType = reportParameter.getDataType();
		Integer reportType = reportParameter.getReportType();
		Integer limit = reportParameter.getLimit();

		ReportRo ReportRo = biddingDataService.statisticsByProjectScaleAndParentCompany(startTime, endTime,
				dataType, reportType, limit);

		return SimpleRestResponse.ok(ReportRo);
	}

	@ApiOperation(value = "报表统计-招标总量（MW）区域排名")
	@PostMapping(value = "/statistics/projectScale/projectProvince")
	public SimpleRestResponse statisticsByProjectScaleAndProjectProvince(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		ReportParameter reportParameter = restRequest.getReportParameter();

		Date startTime = reportParameter.getStartTime();
		Date endTime = reportParameter.getEndTime();
		Integer dataType = reportParameter.getDataType();
		Integer reportType = reportParameter.getReportType();
		Integer limit = reportParameter.getLimit();

		ReportRo ReportRo = biddingDataService.statisticsByProjectScaleAndProjectProvince(startTime, endTime,
				dataType, reportType, limit);

		return SimpleRestResponse.ok(ReportRo);
	}

	@ApiOperation(value = "报表统计-招标数量区域排名")
	@PostMapping(value = "/statistics/projectProvince")
	public SimpleRestResponse statisticsByCountAndProjectProvince(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		ReportParameter reportParameter = restRequest.getReportParameter();

		Date startTime = reportParameter.getStartTime();
		Date endTime = reportParameter.getEndTime();
		Integer dataType = reportParameter.getDataType();
		Integer reportType = reportParameter.getReportType();

		ReportRo ReportRo = biddingDataService.statisticsByCountAndProjectProvince(startTime, endTime,
				dataType, reportType);

		return SimpleRestResponse.ok(ReportRo);
	}

	@ApiOperation(value = "报表统计-招标产品部署类型分析")
	@PostMapping(value = "/statistics/deploymentType")
	public SimpleRestResponse statisticsByCountAndDeploymentType(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		ReportParameter reportParameter = restRequest.getReportParameter();

		Date startTime = reportParameter.getStartTime();
		Date endTime = reportParameter.getEndTime();
		Integer dataType = reportParameter.getDataType();
		Integer reportType = reportParameter.getReportType();

		ReportRo ReportRo = biddingDataService.statisticsByCountAndDeploymentType(startTime, endTime,
				dataType, reportType);

		return SimpleRestResponse.ok(ReportRo);
	}

	@ApiOperation(value = "报表统计-招标产品类型分析")
	@PostMapping(value = "/statistics/productType")
	public SimpleRestResponse statisticsByCountAndProductType(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		ReportParameter reportParameter = restRequest.getReportParameter();

		Date startTime = reportParameter.getStartTime();
		Date endTime = reportParameter.getEndTime();
		Integer dataType = reportParameter.getDataType();
		Integer reportType = reportParameter.getReportType();

		ReportRo ReportRo = biddingDataService.statisticsByCountAndProductType(startTime, endTime,
				dataType, reportType);

		return SimpleRestResponse.ok(ReportRo);
	}

	@ApiOperation(value = "报表统计-招标客户类型分析")
	@PostMapping(value = "/statistics/customerType")
	public SimpleRestResponse statisticsByCountAndCustomerType(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		ReportParameter reportParameter = restRequest.getReportParameter();

		Date startTime = reportParameter.getStartTime();
		Date endTime = reportParameter.getEndTime();
		Integer dataType = reportParameter.getDataType();
		Integer reportType = reportParameter.getReportType();

		ReportRo ReportRo = biddingDataService.statisticsByCountAndCustomerType(startTime, endTime,
				dataType, reportType);

		return SimpleRestResponse.ok(ReportRo);
	}

}
