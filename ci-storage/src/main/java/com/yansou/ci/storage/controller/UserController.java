package com.yansou.ci.storage.controller;

import com.yansou.ci.core.model.AbstractModel;
import com.yansou.ci.core.model.rest.request.RestRequest;
import com.yansou.ci.core.model.rest.response.RestResponse;
import com.yansou.ci.core.model.system.User;
import com.yansou.ci.storage.repository.system.UserRepository;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping(value = "/user")
public class UserController {

	private static final Logger LOG = LogManager.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepository;

	@ApiOperation(value = "分页获取用户列表")
	@GetMapping(value = "/list")
	public RestResponse<User[]> list() {
		List<User> userList = userRepository.findAll();

		LOG.info("userList: {}", userList);

		return RestResponse.ok(userList.toArray(new User[0]));
	}

	@ApiOperation(value = "创建用户")
	@PostMapping(value = "/save")
	public RestResponse<User> save(@RequestBody RestRequest restRequest) {
		User user = restRequest.getUser();
		user = userRepository.save(user);

		return RestResponse.ok(user);
	}

	@ApiOperation(value = "更新用户详细信息")
	@PostMapping(value = "/update")
	public RestResponse<User> update(@RequestBody RestRequest restRequest) {
		User user = restRequest.getUser();
		user = userRepository.save(user);

		return RestResponse.ok(user);
	}

	@ApiOperation(value = "获取用户详细信息")
	@PostMapping(value = "/find")
	public RestResponse<User> find(@RequestBody RestRequest restRequest) {
		User user = restRequest.getUser();
		Long id = user.getId();

		User otherUser = userRepository.findOne(id);

		return RestResponse.ok(otherUser);
	}

	@ApiOperation(value = "删除用户，不做物理删除")
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(@RequestBody RestRequest restRequest) {
		User user = restRequest.getUser();
		LOG.info("delete user: {}", user);

		Long id = user.getId();

		int result = userRepository.updateStatus(AbstractModel.Status.DELETE.getValue(), id);
		LOG.info("result: {}", result);

		return RestResponse.ok();
	}

}
