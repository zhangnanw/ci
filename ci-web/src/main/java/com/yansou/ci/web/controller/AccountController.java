package com.yansou.ci.web.controller;

import com.yansou.ci.common.exception.DaoException;
import com.yansou.ci.common.page.PageCriteria;
import com.yansou.ci.core.model.rest.request.RestRequest;
import com.yansou.ci.core.model.rest.response.SimpleRestResponse;
import com.yansou.ci.web.service.AccountClient;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liutiejun
 * @create 2017-04-12 11:55
 */
@RestController
@RequestMapping(value = "/user")
public class AccountController {

	@Autowired
	private AccountClient accountClient;

	@ApiOperation(value = "获取用户列表")
	@GetMapping(value = "/list")
	public SimpleRestResponse getUserList() {
		try {
			PageCriteria pageCriteria = new PageCriteria();
			pageCriteria.setCurrentPageNo(1);
			pageCriteria.setPageSize(10);

			RestRequest restRequest = new RestRequest();
			restRequest.setPageCriteria(pageCriteria);

			return accountClient.pagination(restRequest);
		} catch (DaoException e) {
			e.printStackTrace();
		}

		return null;
	}

}
