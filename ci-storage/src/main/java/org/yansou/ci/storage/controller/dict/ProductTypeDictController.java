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
import org.yansou.ci.core.db.model.dict.ProductTypeDict;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.SimpleRestResponse;
import org.yansou.ci.storage.service.dict.ProductTypeDictService;

import java.util.List;

@RestController
@RequestMapping("/productTypeDict")
public class ProductTypeDictController {

	private static final Logger LOG = LogManager.getLogger(ProductTypeDictController.class);

	@Autowired
	private ProductTypeDictService productTypeDictService;

	@ApiOperation(value = "分页获取数据详细信息")
	@PostMapping(value = "/pagination")
	public SimpleRestResponse pagination(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		PageCriteria pageCriteria = restRequest.getPageCriteria();

		Pagination<ProductTypeDict> pagination = productTypeDictService.pagination(pageCriteria);

		return SimpleRestResponse.ok(pagination);
	}

	@ApiOperation(value = "获取数据详细信息")
	@PostMapping(value = "/find")
	public SimpleRestResponse find(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		ProductTypeDict productTypeDict = restRequest.getProductTypeDict();

		if (productTypeDict == null) {// 查询所有的数据
			List<ProductTypeDict> productTypeDictList = productTypeDictService.findAll();

			return SimpleRestResponse.ok(productTypeDictList.toArray(new ProductTypeDict[0]));
		}

		Long id = productTypeDict.getId();
		if (id != null) {// 根据ID查询
			ProductTypeDict otherProductTypeDict = productTypeDictService.findById(id);

			return SimpleRestResponse.ok(otherProductTypeDict);
		}

		Integer code = productTypeDict.getCode();
		if (code != null) {// 根据code查询
			ProductTypeDict otherProductTypeDict = productTypeDictService.findByCode(code);

			return SimpleRestResponse.ok(otherProductTypeDict);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "新增数据")
	@PostMapping(value = "/save")
	public SimpleRestResponse save(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		ProductTypeDict productTypeDict = restRequest.getProductTypeDict();

		LOG.info("productTypeDict: {}", productTypeDict);

		if (productTypeDict != null) {// 单个新增
			productTypeDict = productTypeDictService.save(productTypeDict);

			return SimpleRestResponse.id(productTypeDict.getId());
		}

		ProductTypeDict[] productTypeDicts = restRequest.getProductTypeDicts();
		if (ArrayUtils.isNotEmpty(productTypeDicts)) {// 批量新增
			productTypeDicts = productTypeDictService.save(productTypeDicts);

			return SimpleRestResponse.ok(productTypeDicts);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "更新数据")
	@PostMapping(value = "/update")
	public SimpleRestResponse update(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		ProductTypeDict productTypeDict = restRequest.getProductTypeDict();
		if (productTypeDict != null) {// 单个更新
			productTypeDictService.updateNotNullField(productTypeDict);

			return SimpleRestResponse.id(productTypeDict.getId());
		}

		ProductTypeDict[] productTypeDicts = restRequest.getProductTypeDicts();
		if (ArrayUtils.isNotEmpty(productTypeDicts)) {// 批量更新
			productTypeDicts = productTypeDictService.update(productTypeDicts);

			return SimpleRestResponse.ok(productTypeDicts);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "删除数据，不做物理删除，只更新对应的数据状态")
	@PostMapping(value = "/delete")
	public SimpleRestResponse delete(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		ProductTypeDict productTypeDict = restRequest.getProductTypeDict();
		Long id = productTypeDict.getId();

		productTypeDictService.updateStatus(AbstractModel.Status.DELETE.getValue(), id);

		return SimpleRestResponse.ok();
	}

}
