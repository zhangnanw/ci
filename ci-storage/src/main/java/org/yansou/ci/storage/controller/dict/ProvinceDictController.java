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
import org.yansou.ci.core.db.model.dict.ProvinceDict;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.SimpleRestResponse;
import org.yansou.ci.storage.service.dict.ProvinceDictService;

import java.util.List;

@RestController
@RequestMapping("/provinceDict")
public class ProvinceDictController {

	private static final Logger LOG = LogManager.getLogger(ProvinceDictController.class);

	@Autowired
	private ProvinceDictService provinceDictService;

	@ApiOperation(value = "分页获取数据详细信息")
	@PostMapping(value = "/pagination")
	public SimpleRestResponse pagination(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		PageCriteria pageCriteria = restRequest.getPageCriteria();

		Pagination<ProvinceDict> pagination = provinceDictService.pagination(pageCriteria);

		return SimpleRestResponse.ok(pagination);
	}

	@ApiOperation(value = "获取数据详细信息")
	@PostMapping(value = "/find")
	public SimpleRestResponse find(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		ProvinceDict provinceDict = restRequest.getProvinceDict();

		if (provinceDict == null) {// 查询所有的数据
			List<ProvinceDict> provinceDictList = provinceDictService.findAll();

			return SimpleRestResponse.ok(provinceDictList.toArray(new ProvinceDict[0]));
		}

		Long id = provinceDict.getId();
		if (id != null) {// 根据ID查询
			ProvinceDict otherProvinceDict = provinceDictService.findById(id);

			return SimpleRestResponse.ok(otherProvinceDict);
		}

		Integer code = provinceDict.getCode();
		if (code != null) {// 根据code查询
			ProvinceDict otherProvinceDict = provinceDictService.findByCode(code);

			return SimpleRestResponse.ok(otherProvinceDict);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "新增数据")
	@PostMapping(value = "/save")
	public SimpleRestResponse save(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		ProvinceDict provinceDict = restRequest.getProvinceDict();

		LOG.info("provinceDict: {}", provinceDict);

		if (provinceDict != null) {// 单个新增
			provinceDict = provinceDictService.save(provinceDict);

			return SimpleRestResponse.id(provinceDict.getId());
		}

		ProvinceDict[] provinceDicts = restRequest.getProvinceDicts();
		if (ArrayUtils.isNotEmpty(provinceDicts)) {// 批量新增
			provinceDicts = provinceDictService.save(provinceDicts);

			return SimpleRestResponse.ok(provinceDicts);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "更新数据")
	@PostMapping(value = "/update")
	public SimpleRestResponse update(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		ProvinceDict provinceDict = restRequest.getProvinceDict();
		if (provinceDict != null) {// 单个更新
			provinceDictService.updateNotNullField(provinceDict);

			return SimpleRestResponse.id(provinceDict.getId());
		}

		ProvinceDict[] provinceDicts = restRequest.getProvinceDicts();
		if (ArrayUtils.isNotEmpty(provinceDicts)) {// 批量更新
			provinceDicts = provinceDictService.update(provinceDicts);

			return SimpleRestResponse.ok(provinceDicts);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "删除数据，不做物理删除，只更新对应的数据状态")
	@PostMapping(value = "/delete")
	public SimpleRestResponse delete(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		ProvinceDict provinceDict = restRequest.getProvinceDict();
		Long id = provinceDict.getId();

		provinceDictService.updateStatus(AbstractModel.Status.DELETE.getValue(), id);

		return SimpleRestResponse.ok();
	}

}
