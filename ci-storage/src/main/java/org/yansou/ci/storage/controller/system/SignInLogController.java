package org.yansou.ci.storage.controller.system;

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
import org.yansou.ci.core.db.model.system.SignInLog;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.SimpleRestResponse;
import org.yansou.ci.storage.service.system.SignInLogService;

import java.util.List;

/**
 * @author liutiejun
 * @create 2017-04-12 11:55
 */
@RestController
@RequestMapping(value = "/signInLog")
public class SignInLogController {

	private static final Logger LOG = LogManager.getLogger(SignInLogController.class);

	@Autowired
	private SignInLogService signInLogService;

	@ApiOperation(value = "分页获取数据详细信息")
	@PostMapping(value = "/pagination")
	public SimpleRestResponse pagination(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		PageCriteria pageCriteria = restRequest.getPageCriteria();

		Pagination<SignInLog> pagination = signInLogService.pagination(pageCriteria);

		return SimpleRestResponse.ok(pagination);
	}

	@ApiOperation(value = "获取数据详细信息")
	@PostMapping(value = "/find")
	public SimpleRestResponse find(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		SignInLog signInLog = restRequest.getSignInLog();

		if (signInLog == null) {// 查询所有的数据
			List<SignInLog> signInLogList = signInLogService.findAll();

			return SimpleRestResponse.ok(signInLogList.toArray(new SignInLog[0]));
		}

		Long id = signInLog.getId();
		if (id != null) {// 根据ID查询
			SignInLog otherSignInLog = signInLogService.findById(id);

			return SimpleRestResponse.ok(otherSignInLog);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "新增数据")
	@PostMapping(value = "/save")
	public SimpleRestResponse save(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		SignInLog signInLog = restRequest.getSignInLog();
		if (signInLog != null) {// 单个新增
			signInLog = signInLogService.save(signInLog);

			return SimpleRestResponse.id(signInLog.getId());
		}

		SignInLog[] signInLogs = restRequest.getSignInLogs();
		if (ArrayUtils.isNotEmpty(signInLogs)) {// 批量新增
			signInLogs = signInLogService.save(signInLogs);

			return SimpleRestResponse.ok(signInLogs);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "更新数据")
	@PostMapping(value = "/update")
	public SimpleRestResponse update(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		SignInLog signInLog = restRequest.getSignInLog();
		if (signInLog != null) {// 单个更新
			signInLog = signInLogService.update(signInLog);

			return SimpleRestResponse.id(signInLog.getId());
		}

		SignInLog[] signInLogs = restRequest.getSignInLogs();
		if (ArrayUtils.isNotEmpty(signInLogs)) {// 批量更新
			signInLogs = signInLogService.update(signInLogs);

			return SimpleRestResponse.ok(signInLogs);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "删除数据，不做物理删除，只更新对应的数据状态")
	@PostMapping(value = "/delete")
	public SimpleRestResponse delete(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		SignInLog signInLog = restRequest.getSignInLog();
		Long id = signInLog.getId();

		signInLogService.updateStatus(AbstractModel.Status.DELETE.getValue(), id);

		return SimpleRestResponse.ok();
	}

}
