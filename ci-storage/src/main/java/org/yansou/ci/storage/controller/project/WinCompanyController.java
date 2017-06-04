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
import org.yansou.ci.core.model.project.WinCompany;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.SimpleRestResponse;
import org.yansou.ci.storage.service.project.WinCompanyService;

import java.util.List;

/**
 * @author liutiejun
 * @create 2017-05-14 0:28
 */
@RestController
@RequestMapping(value = "/winCompany")
public class WinCompanyController {

	private static final Logger LOG = LogManager.getLogger(WinCompanyController.class);

	@Autowired
	private WinCompanyService winCompanyService;

	@ApiOperation(value = "分页获取数据详细信息")
	@PostMapping(value = "/pagination")
	public SimpleRestResponse pagination(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		PageCriteria pageCriteria = restRequest.getPageCriteria();

		Pagination<WinCompany> pagination = winCompanyService.pagination(pageCriteria);

		return SimpleRestResponse.ok(pagination);
	}

	@ApiOperation(value = "获取数据详细信息")
	@PostMapping(value = "/find")
	public SimpleRestResponse find(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		WinCompany winCompany = restRequest.getWinCompany();

		if (winCompany == null) {// 查询所有的数据
			List<WinCompany> winCompanyList = winCompanyService.findAll();

			return SimpleRestResponse.ok(winCompanyList.toArray(new WinCompany[0]));
		}

		Long id = winCompany.getId();
		if (id != null) {// 根据ID查询
			WinCompany otherWinCompany = winCompanyService.findById(id);

			return SimpleRestResponse.ok(otherWinCompany);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "新增数据")
	@PostMapping(value = "/save")
	public SimpleRestResponse save(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		WinCompany winCompany = restRequest.getWinCompany();

		LOG.info("winCompany: {}", winCompany);

		if (winCompany != null) {// 单个新增
			winCompany = winCompanyService.save(winCompany);

			return SimpleRestResponse.id(winCompany.getId());
		}

		WinCompany[] winCompanies = restRequest.getWinCompanies();
		if (ArrayUtils.isNotEmpty(winCompanies)) {// 批量新增
			winCompanies = winCompanyService.save(winCompanies);

			return SimpleRestResponse.ok(winCompanies);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "更新数据")
	@PostMapping(value = "/update")
	public SimpleRestResponse update(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		WinCompany winCompany = restRequest.getWinCompany();
		if (winCompany != null) {// 单个更新
			winCompany = winCompanyService.update(winCompany);

			return SimpleRestResponse.id(winCompany.getId());
		}

		WinCompany[] winCompanies = restRequest.getWinCompanies();
		if (ArrayUtils.isNotEmpty(winCompanies)) {// 批量更新
			winCompanies = winCompanyService.update(winCompanies);

			return SimpleRestResponse.ok(winCompanies);
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

		int count = winCompanyService.deleteById(ids);

		return SimpleRestResponse.ok("count", count);
	}

}
