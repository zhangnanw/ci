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
import org.yansou.ci.core.db.model.project.MergeData;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.SimpleRestResponse;
import org.yansou.ci.storage.service.project.MergeDataService;

import java.util.List;

/**
 * @author liutiejun
 * @create 2017-05-14 0:28
 */
@RestController
@RequestMapping(value = "/mergeData")
public class MergeDataController {

	private static final Logger LOG = LogManager.getLogger(MergeDataController.class);

	@Autowired
	private MergeDataService mergeDataService;

	@ApiOperation(value = "分页获取数据详细信息")
	@PostMapping(value = "/pagination")
	public SimpleRestResponse pagination(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		PageCriteria pageCriteria = restRequest.getPageCriteria();

		Pagination<MergeData> pagination = mergeDataService.pagination(pageCriteria);

		return SimpleRestResponse.ok(pagination);
	}

	@ApiOperation(value = "获取数据详细信息")
	@PostMapping(value = "/find")
	public SimpleRestResponse find(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		MergeData mergeData = restRequest.getMergeData();

		if (mergeData == null) {// 查询所有的数据
			List<MergeData> mergeDataList = mergeDataService.findAll();

			return SimpleRestResponse.ok(mergeDataList.toArray(new MergeData[0]));
		}

		Long id = mergeData.getId();
		if (id != null) {// 根据ID查询
			MergeData otherMergeData = mergeDataService.findById(id);

			return SimpleRestResponse.ok(otherMergeData);
		}

		String projectIdentifie = mergeData.getProjectIdentifie();// 项目唯一标识
		if (StringUtils.isNotBlank(projectIdentifie)) {
			List<MergeData> mergeDataList = mergeDataService.findByProjectIdentifie(projectIdentifie);

			return SimpleRestResponse.ok(mergeDataList.toArray(new MergeData[0]));
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "新增数据")
	@PostMapping(value = "/save")
	public SimpleRestResponse save(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		MergeData mergeData = restRequest.getMergeData();

		LOG.info("mergeData: {}", mergeData);

		if (mergeData != null) {// 单个新增
			mergeData = mergeDataService.save(mergeData);

			return SimpleRestResponse.id(mergeData.getId());
		}

		MergeData[] mergeDatas = restRequest.getMergeDatas();
		if (ArrayUtils.isNotEmpty(mergeDatas)) {// 批量新增
			mergeDatas = mergeDataService.save(mergeDatas);

			return SimpleRestResponse.ok(mergeDatas);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "更新数据")
	@PostMapping(value = "/update")
	public SimpleRestResponse update(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		MergeData mergeData = restRequest.getMergeData();
		if (mergeData != null) {// 单个更新
			mergeDataService.updateNotNullField(mergeData);

			return SimpleRestResponse.id(mergeData.getId());
		}

		MergeData[] mergeDatas = restRequest.getMergeDatas();
		if (ArrayUtils.isNotEmpty(mergeDatas)) {// 批量更新
			int count = mergeDataService.updateNotNullField(mergeDatas);

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

		int count = mergeDataService.deleteById(ids);

		return SimpleRestResponse.ok("count", count);
	}

}
