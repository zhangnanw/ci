package com.yansou.ci.web.service;

import com.yansou.ci.core.model.system.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author liutiejun
 * @create 2017-05-10 14:42
 */
@FeignClient("ci-storage")
public interface UserService {

	@GetMapping(value = "/user/list")
	public List<User> getUserList();

//	@PostMapping(value = "/user/add")
//	public String addUser(@RequestBody User user);
//
//	@PostMapping(value = "/user/update")
//	public String updateUser(@RequestBody User user);
//
//	@GetMapping(value = "/user/get/{id}")
//	public User getUser(@PathVariable Long id);

//	@GetMapping(value = "/user/delete/{id}")
//	public String deleteUser(@PathVariable Long id);

}
