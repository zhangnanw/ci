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
import org.yansou.ci.core.db.model.project.ProjectInfo;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.SimpleRestResponse;
import org.yansou.ci.storage.service.project.ProjectInfoService;

import java.util.List;

/**
 * @author liutiejun
 * @create 2017-05-14 0:28
 */
@RestController
@RequestMapping(value = "/projectInfo")
public class ProjectInfoController {

	private static final Logger LOG = LogManager.getLogger(ProjectInfoController.class);

	@Autowired
	private ProjectInfoService projectInfoService;

	@ApiOperation(value = "分页获取数据详细信息")
	@PostMapping(value = "/pagination")
	public SimpleRestResponse pagination(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		PageCriteria pageCriteria = restRequest.getPageCriteria();

		Pagination<ProjectInfo> pagination = projectInfoService.pagination(pageCriteria);

		return SimpleRestResponse.ok(pagination);
	}

	@ApiOperation(value = "获取数据详细信息")
	@PostMapping(value = "/find")
	public SimpleRestResponse find(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		ProjectInfo projectInfo = restRequest.getProjectInfo();

		if (projectInfo == null) {// 查询所有的数据
			List<ProjectInfo> projectInfoList = projectInfoService.findAll();

			return SimpleRestResponse.ok(projectInfoList.toArray(new ProjectInfo[0]));
		}

		Long id = projectInfo.getId();
		if (id != null) {// 根据ID查询
			ProjectInfo otherProjectInfo = projectInfoService.findById(id);

			return SimpleRestResponse.ok(otherProjectInfo);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "新增数据")
	@PostMapping(value = "/save")
	public SimpleRestResponse save(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		ProjectInfo projectInfo = restRequest.getProjectInfo();

		LOG.info("projectInfo: {}", projectInfo);

		if (projectInfo != null) {// 单个新增
			projectInfo = projectInfoService.save(projectInfo);

			return SimpleRestResponse.id(projectInfo.getId());
		}

		ProjectInfo[] projectInfos = restRequest.getProjectInfos();
		if (ArrayUtils.isNotEmpty(projectInfos)) {// 批量新增
			projectInfos = projectInfoService.save(projectInfos);

			return SimpleRestResponse.ok(projectInfos);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "更新数据")
	@PostMapping(value = "/update")
	public SimpleRestResponse update(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		ProjectInfo projectInfo = restRequest.getProjectInfo();
		if (projectInfo != null) {// 单个更新
			projectInfoService.updateNotNullField(projectInfo);

			return SimpleRestResponse.id(projectInfo.getId());
		}

		ProjectInfo[] projectInfos = restRequest.getProjectInfos();
		if (ArrayUtils.isNotEmpty(projectInfos)) {// 批量更新
			projectInfos = projectInfoService.update(projectInfos);

			return SimpleRestResponse.ok(projectInfos);
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

		int count = projectInfoService.deleteById(ids);

		return SimpleRestResponse.ok("count", count);
	}

}
