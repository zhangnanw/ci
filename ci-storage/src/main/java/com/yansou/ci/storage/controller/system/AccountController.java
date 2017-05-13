package com.yansou.ci.storage.controller.system;

import com.yansou.ci.common.page.PageCriteria;
import com.yansou.ci.common.page.Pagination;
import com.yansou.ci.core.model.AbstractModel;
import com.yansou.ci.core.model.system.Account;
import com.yansou.ci.core.rest.request.RestRequest;
import com.yansou.ci.core.rest.response.SimpleRestResponse;
import com.yansou.ci.storage.service.system.AccountService;
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
 * @create 2017-04-12 11:55
 */
@RestController
@RequestMapping(value = "/account")
public class AccountController {

	private static final Logger LOG = LogManager.getLogger(AccountController.class);

	@Autowired
	private AccountService accountService;

	@ApiOperation(value = "分页获取数据详细信息")
	@PostMapping(value = "/pagination")
	public SimpleRestResponse pagination(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		PageCriteria pageCriteria = restRequest.getPageCriteria();

		Pagination<Account> pagination = accountService.pagination(pageCriteria);

		return SimpleRestResponse.ok(pagination);
	}

	@ApiOperation(value = "获取数据详细信息")
	@PostMapping(value = "/find")
	public SimpleRestResponse find(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		Account account = restRequest.getAccount();

		if (account == null) {// 查询所有的数据
			List<Account> accountList = accountService.findAll();

			return SimpleRestResponse.ok(accountList.toArray(new Account[0]));
		}

		Long id = account.getId();
		if (id != null) {// 根据ID查询
			Account otherAccount = accountService.findById(id);

			return SimpleRestResponse.ok(otherAccount);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "新增数据")
	@PostMapping(value = "/save")
	public SimpleRestResponse save(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		Account account = restRequest.getAccount();
		if (account != null) {// 单个新增
			account = accountService.save(account);

			return SimpleRestResponse.id(account.getId());
		}

		Account[] accounts = restRequest.getAccounts();
		if (ArrayUtils.isNotEmpty(accounts)) {// 批量新增
			accounts = accountService.save(accounts);

			return SimpleRestResponse.ok(accounts);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "更新数据")
	@PostMapping(value = "/update")
	public SimpleRestResponse update(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		Account account = restRequest.getAccount();
		if (account != null) {// 单个更新
			account = accountService.update(account);

			return SimpleRestResponse.id(account.getId());
		}

		Account[] accounts = restRequest.getAccounts();
		if (ArrayUtils.isNotEmpty(accounts)) {// 批量更新
			accounts = accountService.update(accounts);

			return SimpleRestResponse.ok(accounts);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "删除数据，不做物理删除，只更新对应的数据状态")
	@PostMapping(value = "/delete")
	public SimpleRestResponse delete(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		Account account = restRequest.getAccount();
		Long id = account.getId();

		accountService.updateStatus(AbstractModel.Status.DELETE.getValue(), id);

		return SimpleRestResponse.ok();
	}

}
