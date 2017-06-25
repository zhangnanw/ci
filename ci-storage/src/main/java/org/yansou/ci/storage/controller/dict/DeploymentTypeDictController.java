package org.yansou.ci.storage.controller.dict;

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
import org.yansou.ci.core.db.model.AbstractModel;
import org.yansou.ci.core.db.model.dict.DeploymentTypeDict;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.SimpleRestResponse;
import org.yansou.ci.storage.service.dict.DeploymentTypeDictService;

import java.util.List;

@RestController
@RequestMapping("/deploymentTypeDict")
public class DeploymentTypeDictController {

	private static final Logger LOG = LogManager.getLogger(DeploymentTypeDictController.class);

	@Autowired
	private DeploymentTypeDictService deploymentTypeDictService;

	@ApiOperation(value = "分页获取数据详细信息")
	@PostMapping(value = "/pagination")
	public SimpleRestResponse pagination(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		PageCriteria pageCriteria = restRequest.getPageCriteria();

		Pagination<DeploymentTypeDict> pagination = deploymentTypeDictService.pagination(pageCriteria);

		return SimpleRestResponse.ok(pagination);
	}

	@ApiOperation(value = "获取数据详细信息")
	@PostMapping(value = "/find")
	public SimpleRestResponse find(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		DeploymentTypeDict deploymentTypeDict = restRequest.getDeploymentTypeDict();

		if (deploymentTypeDict == null) {// 查询所有的数据
			List<DeploymentTypeDict> deploymentTypeDictList = deploymentTypeDictService.findAll();

			return SimpleRestResponse.ok(deploymentTypeDictList.toArray(new DeploymentTypeDict[0]));
		}

		Long id = deploymentTypeDict.getId();
		if (id != null) {// 根据ID查询
			DeploymentTypeDict otherDeploymentTypeDict = deploymentTypeDictService.findById(id);

			return SimpleRestResponse.ok(otherDeploymentTypeDict);
		}

		Integer code = deploymentTypeDict.getCode();
		if (code != null) {// 根据code查询
			DeploymentTypeDict otherDeploymentTypeDict = deploymentTypeDictService.findByCode(code);

			return SimpleRestResponse.ok(otherDeploymentTypeDict);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "新增数据")
	@PostMapping(value = "/save")
	public SimpleRestResponse save(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		DeploymentTypeDict deploymentTypeDict = restRequest.getDeploymentTypeDict();

		LOG.info("deploymentTypeDict: {}", deploymentTypeDict);

		if (deploymentTypeDict != null) {// 单个新增
			deploymentTypeDict = deploymentTypeDictService.save(deploymentTypeDict);

			return SimpleRestResponse.id(deploymentTypeDict.getId());
		}

		DeploymentTypeDict[] deploymentTypeDicts = restRequest.getDeploymentTypeDicts();
		if (ArrayUtils.isNotEmpty(deploymentTypeDicts)) {// 批量新增
			deploymentTypeDicts = deploymentTypeDictService.save(deploymentTypeDicts);

			return SimpleRestResponse.ok(deploymentTypeDicts);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "更新数据")
	@PostMapping(value = "/update")
	public SimpleRestResponse update(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		DeploymentTypeDict deploymentTypeDict = restRequest.getDeploymentTypeDict();
		if (deploymentTypeDict != null) {// 单个更新
			deploymentTypeDictService.updateNotNullField(deploymentTypeDict);

			return SimpleRestResponse.id(deploymentTypeDict.getId());
		}

		DeploymentTypeDict[] deploymentTypeDicts = restRequest.getDeploymentTypeDicts();
		if (ArrayUtils.isNotEmpty(deploymentTypeDicts)) {// 批量更新
			deploymentTypeDicts = deploymentTypeDictService.update(deploymentTypeDicts);

			return SimpleRestResponse.ok(deploymentTypeDicts);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "删除数据，不做物理删除，只更新对应的数据状态")
	@PostMapping(value = "/delete")
	public SimpleRestResponse delete(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		DeploymentTypeDict deploymentTypeDict = restRequest.getDeploymentTypeDict();
		Long id = deploymentTypeDict.getId();

		deploymentTypeDictService.updateStatus(AbstractModel.Status.DELETE.getValue(), id);

		return SimpleRestResponse.ok();
	}

}
