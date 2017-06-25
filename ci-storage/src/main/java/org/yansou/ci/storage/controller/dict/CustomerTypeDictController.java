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
import org.yansou.ci.core.db.model.dict.CustomerTypeDict;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.SimpleRestResponse;
import org.yansou.ci.storage.service.dict.CustomerTypeDictService;

import java.util.List;

@RestController
@RequestMapping("/customerTypeDict")
public class CustomerTypeDictController {

	private static final Logger LOG = LogManager.getLogger(CustomerTypeDictController.class);

	@Autowired
	private CustomerTypeDictService customerTypeDictService;

	@ApiOperation(value = "分页获取数据详细信息")
	@PostMapping(value = "/pagination")
	public SimpleRestResponse pagination(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		PageCriteria pageCriteria = restRequest.getPageCriteria();

		Pagination<CustomerTypeDict> pagination = customerTypeDictService.pagination(pageCriteria);

		return SimpleRestResponse.ok(pagination);
	}

	@ApiOperation(value = "获取数据详细信息")
	@PostMapping(value = "/find")
	public SimpleRestResponse find(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		CustomerTypeDict customerTypeDict = restRequest.getCustomerTypeDict();

		if (customerTypeDict == null) {// 查询所有的数据
			List<CustomerTypeDict> customerTypeDictList = customerTypeDictService.findAll();

			return SimpleRestResponse.ok(customerTypeDictList.toArray(new CustomerTypeDict[0]));
		}

		Long id = customerTypeDict.getId();
		if (id != null) {// 根据ID查询
			CustomerTypeDict otherCustomerTypeDict = customerTypeDictService.findById(id);

			return SimpleRestResponse.ok(otherCustomerTypeDict);
		}

		Integer code = customerTypeDict.getCode();
		if (code != null) {// 根据code查询
			CustomerTypeDict otherCustomerTypeDict = customerTypeDictService.findByCode(code);

			return SimpleRestResponse.ok(otherCustomerTypeDict);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "新增数据")
	@PostMapping(value = "/save")
	public SimpleRestResponse save(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		CustomerTypeDict customerTypeDict = restRequest.getCustomerTypeDict();

		LOG.info("customerTypeDict: {}", customerTypeDict);

		if (customerTypeDict != null) {// 单个新增
			customerTypeDict = customerTypeDictService.save(customerTypeDict);

			return SimpleRestResponse.id(customerTypeDict.getId());
		}

		CustomerTypeDict[] customerTypeDicts = restRequest.getCustomerTypeDicts();
		if (ArrayUtils.isNotEmpty(customerTypeDicts)) {// 批量新增
			customerTypeDicts = customerTypeDictService.save(customerTypeDicts);

			return SimpleRestResponse.ok(customerTypeDicts);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "更新数据")
	@PostMapping(value = "/update")
	public SimpleRestResponse update(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		CustomerTypeDict customerTypeDict = restRequest.getCustomerTypeDict();
		if (customerTypeDict != null) {// 单个更新
			customerTypeDictService.updateNotNullField(customerTypeDict);

			return SimpleRestResponse.id(customerTypeDict.getId());
		}

		CustomerTypeDict[] customerTypeDicts = restRequest.getCustomerTypeDicts();
		if (ArrayUtils.isNotEmpty(customerTypeDicts)) {// 批量更新
			customerTypeDicts = customerTypeDictService.update(customerTypeDicts);

			return SimpleRestResponse.ok(customerTypeDicts);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "删除数据，不做物理删除，只更新对应的数据状态")
	@PostMapping(value = "/delete")
	public SimpleRestResponse delete(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		CustomerTypeDict customerTypeDict = restRequest.getCustomerTypeDict();
		Long id = customerTypeDict.getId();

		customerTypeDictService.updateStatus(AbstractModel.Status.DELETE.getValue(), id);

		return SimpleRestResponse.ok();
	}

}
