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
import org.yansou.ci.core.db.model.project.RecordData;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.SimpleRestResponse;
import org.yansou.ci.storage.service.project.RecordDataService;

import java.util.List;

/**
 * @author liutiejun
 * @create 2017-05-14 0:28
 */
@RestController
@RequestMapping(value = "/recordData")
public class RecordDataController {

	private static final Logger LOG = LogManager.getLogger(RecordDataController.class);

	@Autowired
	private RecordDataService recordDataService;

	@ApiOperation(value = "分页获取数据详细信息")
	@PostMapping(value = "/pagination")
	public SimpleRestResponse pagination(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		PageCriteria pageCriteria = restRequest.getPageCriteria();

		Pagination<RecordData> pagination = recordDataService.pagination(pageCriteria);

		return SimpleRestResponse.ok(pagination);
	}

	@ApiOperation(value = "获取数据详细信息")
	@PostMapping(value = "/find")
	public SimpleRestResponse find(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		RecordData recordData = restRequest.getRecordData();

		if (recordData == null) {// 查询所有的数据
			List<RecordData> recordDataList = recordDataService.findAll();

			return SimpleRestResponse.ok(recordDataList.toArray(new RecordData[0]));
		}

		Long id = recordData.getId();
		if (id != null) {// 根据ID查询
			RecordData otherRecordData = recordDataService.findById(id);

			return SimpleRestResponse.ok(otherRecordData);
		}

		String projectIdentifie = recordData.getProjectIdentifie();// 项目唯一标识
		if (StringUtils.isNotBlank(projectIdentifie)) {
			List<RecordData> recordDataList = recordDataService.findByProjectIdentifie(projectIdentifie);

			return SimpleRestResponse.ok(recordDataList);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "新增数据")
	@PostMapping(value = "/save")
	public SimpleRestResponse save(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		RecordData recordData = restRequest.getRecordData();

		LOG.info("recordData: {}", recordData);

		if (recordData != null) {// 单个新增
			recordData = recordDataService.save(recordData);

			return SimpleRestResponse.id(recordData.getId());
		}

		RecordData[] recordDatas = restRequest.getRecordDatas();
		if (ArrayUtils.isNotEmpty(recordDatas)) {// 批量新增
			recordDatas = recordDataService.save(recordDatas);

			return SimpleRestResponse.ok(recordDatas);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "更新数据")
	@PostMapping(value = "/update")
	public SimpleRestResponse update(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		RecordData recordData = restRequest.getRecordData();
		if (recordData != null) {// 单个更新
			recordDataService.updateNotNullField(recordData);

			return SimpleRestResponse.id(recordData.getId());
		}

		RecordData[] recordDatas = restRequest.getRecordDatas();
		if (ArrayUtils.isNotEmpty(recordDatas)) {// 批量更新
			recordDatas = recordDataService.update(recordDatas);

			return SimpleRestResponse.ok(recordDatas);
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

		int count = recordDataService.deleteById(ids);

		return SimpleRestResponse.ok("count", count);
	}

}
