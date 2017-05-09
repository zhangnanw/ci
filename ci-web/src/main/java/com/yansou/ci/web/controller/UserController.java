package com.yansou.ci.web.controller;

import com.yansou.ci.core.model.system.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liutiejun
 * @create 2017-04-12 11:55
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

	private static Map<Long, User> userMap = Collections.synchronizedMap(new HashMap<Long, User>());

	@ApiOperation(value = "获取用户列表")
	@GetMapping(value = "/list")
	public List<User> getUserList() {
		return new ArrayList<>(userMap.values());
	}

	@ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
	@PostMapping(value = "/add")
	public String addUser(@RequestBody User user) {
		System.out.println("addUser--->" + user.toString());

		userMap.put(user.getId(), user);

		return user.toString();
	}

	@ApiOperation(value = "更新用户详细信息", notes = "根据User对象更新用户")
	@PostMapping(value = "/update")
	public String updateUser(@RequestBody User user) {
		Long id = user.getId();

		userMap.put(id, user);

		return user.toString();
	}

	@ApiOperation(value = "获取用户详细信息", notes = "根据URL中的id来获取用户详细信息")
	@GetMapping(value = "/get/{id}")
	public User getUser(@PathVariable Long id) {
		return userMap.get(id);
	}

	@ApiOperation(value = "删除用户", notes = "根据url的id来指定删除对象")
	@GetMapping(value = "/delete/{id}")
	public String deleteUser(@PathVariable Long id) {
		User user = userMap.get(id);

		userMap.remove(id);

		return user.toString();
	}

}
