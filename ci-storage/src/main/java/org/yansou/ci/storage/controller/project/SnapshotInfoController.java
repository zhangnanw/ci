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
import org.yansou.ci.core.model.AbstractModel;
import org.yansou.ci.core.model.project.SnapshotInfo;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.SimpleRestResponse;
import org.yansou.ci.storage.service.project.SnapshotInfoService;

import java.util.List;

/**
 * 快照读写
 *
 * @author n.zhang
 */
@RestController
@RequestMapping("/snapshotInfo")
public class SnapshotInfoController {

	private static final Logger LOG = LogManager.getLogger(SnapshotInfoController.class);

	@Autowired
	private SnapshotInfoService snapshotInfoService;

	@ApiOperation(value = "分页获取数据详细信息")
	@PostMapping(value = "/pagination")
	public SimpleRestResponse pagination(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		PageCriteria pageCriteria = restRequest.getPageCriteria();

		Pagination<SnapshotInfo> pagination = snapshotInfoService.pagination(pageCriteria);

		return SimpleRestResponse.ok(pagination);
	}

	@ApiOperation(value = "获取数据详细信息")
	@PostMapping(value = "/find")
	public SimpleRestResponse find(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		SnapshotInfo snapshotInfo = restRequest.getSnapshotInfo();

		if (snapshotInfo == null) {// 查询所有的数据
			List<SnapshotInfo> snapshotInfoList = snapshotInfoService.findAll();

			return SimpleRestResponse.ok(snapshotInfoList.toArray(new SnapshotInfo[0]));
		}

		Long id = snapshotInfo.getId();
		if (id != null) {// 根据ID查询
			SnapshotInfo otherSnapshotInfo = snapshotInfoService.findById(id);

			LOG.info("snapshotInfo: {}", otherSnapshotInfo);

			return SimpleRestResponse.ok(otherSnapshotInfo);
		}

		String snapshotId = snapshotInfo.getSnapshotId();
		if (StringUtils.isNotBlank(snapshotId)) {// 根据snapshotId查询
			SnapshotInfo otherSnapshotInfo = snapshotInfoService.findBySnapshotId(snapshotId);

			LOG.info("snapshotInfo: {}", otherSnapshotInfo);

			return SimpleRestResponse.ok(otherSnapshotInfo);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "新增数据")
	@PostMapping(value = "/save")
	public SimpleRestResponse save(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		SnapshotInfo snapshotInfo = restRequest.getSnapshotInfo();

		LOG.info("snapshotInfo: {}", snapshotInfo);

		if (snapshotInfo != null) {// 单个新增
			snapshotInfo = snapshotInfoService.save(snapshotInfo);

			return SimpleRestResponse.id(snapshotInfo.getId());
		}

		SnapshotInfo[] snapshotInfos = restRequest.getSnapshotInfos();
		if (ArrayUtils.isNotEmpty(snapshotInfos)) {// 批量新增
			snapshotInfos = snapshotInfoService.save(snapshotInfos);

			return SimpleRestResponse.ok(snapshotInfos);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "更新数据")
	@PostMapping(value = "/update")
	public SimpleRestResponse update(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		SnapshotInfo snapshotInfo = restRequest.getSnapshotInfo();
		if (snapshotInfo != null) {// 单个更新
			snapshotInfoService.updateNotNullField(snapshotInfo);

			return SimpleRestResponse.id(snapshotInfo.getId());
		}

		SnapshotInfo[] snapshotInfos = restRequest.getSnapshotInfos();
		if (ArrayUtils.isNotEmpty(snapshotInfos)) {// 批量更新
			snapshotInfos = snapshotInfoService.update(snapshotInfos);

			return SimpleRestResponse.ok(snapshotInfos);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "删除数据，不做物理删除，只更新对应的数据状态")
	@PostMapping(value = "/delete")
	public SimpleRestResponse delete(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		SnapshotInfo snapshotInfo = restRequest.getSnapshotInfo();
		Long id = snapshotInfo.getId();

		snapshotInfoService.updateStatus(AbstractModel.Status.DELETE.getValue(), id);

		return SimpleRestResponse.ok();
	}

}
