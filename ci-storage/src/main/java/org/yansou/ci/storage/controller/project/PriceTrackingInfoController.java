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
import org.yansou.ci.core.db.model.project.PriceTrackingInfo;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.SimpleRestResponse;
import org.yansou.ci.storage.service.project.PriceTrackingInfoService;

import java.util.List;

/**
 * @author liutiejun
 * @create 2017-05-14 0:28
 */
@RestController
@RequestMapping(value = "/priceTrackingInfo")
public class PriceTrackingInfoController {

	private static final Logger LOG = LogManager.getLogger(PriceTrackingInfoController.class);

	@Autowired
	private PriceTrackingInfoService priceTrackingInfoService;

	@ApiOperation(value = "分页获取数据详细信息")
	@PostMapping(value = "/pagination")
	public SimpleRestResponse pagination(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		PageCriteria pageCriteria = restRequest.getPageCriteria();

		Pagination<PriceTrackingInfo> pagination = priceTrackingInfoService.pagination(pageCriteria);

		return SimpleRestResponse.ok(pagination);
	}

	@ApiOperation(value = "获取数据详细信息")
	@PostMapping(value = "/find")
	public SimpleRestResponse find(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		PriceTrackingInfo priceTrackingInfo = restRequest.getPriceTrackingInfo();

		if (priceTrackingInfo == null) {// 查询所有的数据
			List<PriceTrackingInfo> priceTrackingInfoList = priceTrackingInfoService.findAll();

			return SimpleRestResponse.ok(priceTrackingInfoList.toArray(new PriceTrackingInfo[0]));
		}

		Long id = priceTrackingInfo.getId();
		if (id != null) {// 根据ID查询
			PriceTrackingInfo otherPriceTrackingInfo = priceTrackingInfoService.findById(id);

			return SimpleRestResponse.ok(otherPriceTrackingInfo);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "新增数据")
	@PostMapping(value = "/save")
	public SimpleRestResponse save(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		PriceTrackingInfo priceTrackingInfo = restRequest.getPriceTrackingInfo();

		LOG.info("priceTrackingInfo: {}", priceTrackingInfo);

		if (priceTrackingInfo != null) {// 单个新增
			priceTrackingInfo = priceTrackingInfoService.save(priceTrackingInfo);

			return SimpleRestResponse.id(priceTrackingInfo.getId());
		}

		PriceTrackingInfo[] priceTrackingInfos = restRequest.getPriceTrackingInfos();
		if (ArrayUtils.isNotEmpty(priceTrackingInfos)) {// 批量新增
			priceTrackingInfos = priceTrackingInfoService.save(priceTrackingInfos);

			return SimpleRestResponse.ok(priceTrackingInfos);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "更新数据")
	@PostMapping(value = "/update")
	public SimpleRestResponse update(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		PriceTrackingInfo priceTrackingInfo = restRequest.getPriceTrackingInfo();
		if (priceTrackingInfo != null) {// 单个更新
			priceTrackingInfoService.updateNotNullField(priceTrackingInfo);

			return SimpleRestResponse.id(priceTrackingInfo.getId());
		}

		PriceTrackingInfo[] priceTrackingInfos = restRequest.getPriceTrackingInfos();
		if (ArrayUtils.isNotEmpty(priceTrackingInfos)) {// 批量更新
			priceTrackingInfos = priceTrackingInfoService.update(priceTrackingInfos);

			return SimpleRestResponse.ok(priceTrackingInfos);
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

		int count = priceTrackingInfoService.deleteById(ids);

		return SimpleRestResponse.ok("count", count);
	}

}
