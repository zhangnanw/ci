package org.yansou.ci.web.client;
//package com.yansou.ci.web.client;
//
//import com.yansou.ci.core.rest.request.RestRequest;
//import com.yansou.ci.core.rest.response.SimpleRestResponse;
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
///**
// * @author liutiejun
// * @create 2017-05-10 14:42
// */
//@FeignClient("ci-storage")
//public interface AccountClient {
//
//	@PostMapping(value = "/account/pagination")
//	public SimpleRestResponse pagination(@RequestBody RestRequest restRequest);
//
//	@PostMapping(value = "/account/find")
//	public SimpleRestResponse find(@RequestBody RestRequest restRequest);
//
//	@PostMapping(value = "/account/save")
//	public SimpleRestResponse save(@RequestBody RestRequest restRequest);
//
//	@PostMapping(value = "/account/update")
//	public SimpleRestResponse update(@RequestBody RestRequest restRequest);
//
//	@PostMapping(value = "/account/delete")
//	public SimpleRestResponse delete(@RequestBody RestRequest restRequest);
//
//}
