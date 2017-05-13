package com.yansou.ci.web.service;

import com.yansou.ci.common.exception.DaoException;
import com.yansou.ci.core.model.rest.request.RestRequest;
import com.yansou.ci.core.model.rest.response.SimpleRestResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author liutiejun
 * @create 2017-05-10 14:42
 */
@FeignClient("ci-storage")
public interface AccountClient {

	@PostMapping(value = "/account/pagination")
	public SimpleRestResponse pagination(@RequestBody RestRequest restRequest) throws DaoException;

	@PostMapping(value = "/account/find")
	public SimpleRestResponse find(@RequestBody RestRequest restRequest) throws DaoException;

	@PostMapping(value = "/account/save")
	public SimpleRestResponse save(@RequestBody RestRequest restRequest) throws DaoException;

	@PostMapping(value = "/account/update")
	public SimpleRestResponse update(@RequestBody RestRequest restRequest) throws DaoException;

	@PostMapping(value = "/account/delete")
	public SimpleRestResponse delete(@RequestBody RestRequest restRequest) throws DaoException;

}
