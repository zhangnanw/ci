package com.yansou.ci.web.controller;

import com.yansou.ci.core.model.system.User;
import com.yansou.ci.web.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

	@Autowired
	private UserService userService;

	@ApiOperation(value = "获取用户列表")
	@GetMapping(value = "/list")
	public List<User> getUserList() {
		return userService.getUserList();
	}

//	@ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
//	@PostMapping(value = "/add")
//	public String addUser(@RequestBody User user) {
//		System.out.println("addUser--->" + user.toString());
//
//		userService.addUser(user);
//
//		return user.toString();
//	}
//
//	@ApiOperation(value = "更新用户详细信息", notes = "根据User对象更新用户")
//	@PostMapping(value = "/update")
//	public String updateUser(@RequestBody User user) {
//		userService.updateUser(user);
//
//		return user.toString();
//	}
//
//	@ApiOperation(value = "获取用户详细信息", notes = "根据URL中的id来获取用户详细信息")
//	@GetMapping(value = "/get/{id}")
//	public User getUser(@PathVariable Long id) {
//		return userService.getUser(id);
//	}

//	@ApiOperation(value = "删除用户", notes = "根据url的id来指定删除对象")
//	@GetMapping(value = "/delete/{id}")
//	public String deleteUser(@PathVariable Long id) {
//		User user = userService.getUser(id);
//
//		String result = user.toString();
//
//		userService.deleteUser(id);
//
//		return result;
//	}

}
