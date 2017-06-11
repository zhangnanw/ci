package org.yansou.ci.storage.controller.project;

import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yansou.ci.common.page.PageCriteria;
import org.yansou.ci.common.page.Pagination;
import org.yansou.ci.core.db.model.project.PlanBuildData;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.SimpleRestResponse;
import org.yansou.ci.storage.service.project.PlanBuildDataService;

import java.util.List;

/**
 * @author liutiejun
 * @create 2017-05-14 0:28
 */
@RestController
@RequestMapping(value = "/planBuildData")
public class PlanBuildDataController {

	private static final Logger LOG = LogManager.getLogger(PlanBuildDataController.class);

	@Autowired
	private PlanBuildDataService planBuildDataService;

	@ApiOperation(value = "分页获取数据详细信息")
	@PostMapping(value = "/pagination")
	public SimpleRestResponse pagination(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		PageCriteria pageCriteria = restRequest.getPageCriteria();

		Pagination<PlanBuildData> pagination = planBuildDataService.pagination(pageCriteria);

		return SimpleRestResponse.ok(pagination);
	}

	@ApiOperation(value = "获取数据详细信息")
	@PostMapping(value = "/find")
	public SimpleRestResponse find(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		PlanBuildData planBuildData = restRequest.getPlanBuildData();

		if (planBuildData == null) {// 查询所有的数据
			List<PlanBuildData> planBuildDataList = planBuildDataService.findAll();

			return SimpleRestResponse.ok(planBuildDataList.toArray(new PlanBuildData[0]));
		}

		Long id = planBuildData.getId();
		if (id != null) {// 根据ID查询
			PlanBuildData otherPlanBuildData = planBuildDataService.findById(id);

			return SimpleRestResponse.ok(otherPlanBuildData);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "新增数据")
	@PostMapping(value = "/save")
	public SimpleRestResponse save(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		PlanBuildData planBuildData = restRequest.getPlanBuildData();

		LOG.info("planBuildData: {}", planBuildData);

		if (planBuildData != null) {// 单个新增
			planBuildData = planBuildDataService.save(planBuildData);

			return SimpleRestResponse.id(planBuildData.getId());
		}

		PlanBuildData[] planBuildDatas = restRequest.getPlanBuildDatas();
		if (ArrayUtils.isNotEmpty(planBuildDatas)) {// 批量新增
			planBuildDatas = planBuildDataService.save(planBuildDatas);

			return SimpleRestResponse.ok(planBuildDatas);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "更新数据")
	@PostMapping(value = "/update")
	public SimpleRestResponse update(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		PlanBuildData planBuildData = restRequest.getPlanBuildData();
		if (planBuildData != null) {// 单个更新
			planBuildDataService.updateNotNullField(planBuildData);

			return SimpleRestResponse.id(planBuildData.getId());
		}

		PlanBuildData[] planBuildDatas = restRequest.getPlanBuildDatas();
		if (ArrayUtils.isNotEmpty(planBuildDatas)) {// 批量更新
			planBuildDatas = planBuildDataService.update(planBuildDatas);

			return SimpleRestResponse.ok(planBuildDatas);
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

		int count = planBuildDataService.deleteById(ids);

		return SimpleRestResponse.ok("count", count);
	}

}
