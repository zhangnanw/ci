package com.yansou.ci.storage.controller.project;

import com.yansou.ci.common.page.PageCriteria;
import com.yansou.ci.common.page.Pagination;
import com.yansou.ci.core.model.AbstractModel;
import com.yansou.ci.core.model.project.BiddingData;
import com.yansou.ci.core.rest.request.RestRequest;
import com.yansou.ci.core.rest.response.SimpleRestResponse;
import com.yansou.ci.storage.service.project.BiddingDataService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

			return SimpleRestResponse.ok(otherBiddingData);
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
			biddingData = biddingDataService.update(biddingData);

			return SimpleRestResponse.id(biddingData.getId());
		}

		BiddingData[] biddingDatas = restRequest.getBiddingDatas();
		if (ArrayUtils.isNotEmpty(biddingDatas)) {// 批量更新
			biddingDatas = biddingDataService.update(biddingDatas);

			return SimpleRestResponse.ok(biddingDatas);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "删除数据，不做物理删除，只更新对应的数据状态")
	@PostMapping(value = "/delete")
	public SimpleRestResponse delete(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		BiddingData biddingData = restRequest.getBiddingData();
		Long id = biddingData.getId();

		biddingDataService.updateStatus(AbstractModel.Status.DELETE.getValue(), id);

		return SimpleRestResponse.ok();
	}

}
