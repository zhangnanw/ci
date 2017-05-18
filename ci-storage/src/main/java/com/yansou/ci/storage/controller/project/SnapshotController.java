package com.yansou.ci.storage.controller.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yansou.ci.common.exception.DaoException;
import com.yansou.ci.core.model.project.BiddingSnapshot;
import com.yansou.ci.core.rest.request.RestRequest;
import com.yansou.ci.core.rest.response.SimpleRestResponse;
import com.yansou.ci.storage.service.project.BiddingSnapshotService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/snapshot")
public class SnapshotController {
	@Autowired
	private BiddingSnapshotService biddingSnapshotService;

	@ApiOperation("招中标快照")
	@GetMapping("/bidding/{id}")
	public SimpleRestResponse bidding(@PathVariable Long id) {
		try {
			BiddingSnapshot ent = biddingSnapshotService.findById(id);
			return SimpleRestResponse.ok("context", ent.getContext());
		} catch (DaoException e) {
			return SimpleRestResponse.exception(e);
		}
	}

	@ApiOperation("招中标快照写入")
	@PutMapping("/bidding")
	public SimpleRestResponse setBidding(RestRequest request) {
		try {
			BiddingSnapshot res = biddingSnapshotService.save(request.getBiddingSnapshot());
			return SimpleRestResponse.ok("url", "/snapshot/bidding/" + res.getId());

		} catch (DaoException e) {
			return SimpleRestResponse.exception(e);
		}

	}
}
