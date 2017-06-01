package org.yansou.ci.storage.controller.project;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.model.project.BiddingSnapshot;
import org.yansou.ci.core.model.project.PlanBuildSnapshot;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.SimpleRestResponse;
import org.yansou.ci.storage.service.project.BiddingSnapshotService;
import org.yansou.ci.storage.service.project.PlanBuildSnapshotService;

/**
 * 快照读写
 *
 * @author n.zhang
 */
@RestController
@RequestMapping("/snapshot")
public class SnapshotController {

	@Autowired
	private BiddingSnapshotService biddingSnapshotService;

	@Autowired
	private PlanBuildSnapshotService planBuildSnapshotService;

	@ApiOperation("招中标快照")
	@GetMapping("/bidding/{snapshotId}")
	public SimpleRestResponse bidding(@PathVariable String snapshotId) {
		BiddingSnapshot ent = biddingSnapshotService.getSnapshot(snapshotId);
		return SimpleRestResponse.ok("context", ent.getContext());
	}

	@ApiOperation("招中标快照写入")
	@PostMapping("/bidding")
	public SimpleRestResponse setBidding(RestRequest request) {
		try {
			BiddingSnapshot res = biddingSnapshotService.save(request.getBiddingSnapshot());
			return SimpleRestResponse.ok("url", "/snapshot/bidding/" + res.getId());

		} catch (DaoException e) {
			return SimpleRestResponse.exception(e);
		}
	}

	@ApiOperation("拟在建快照")
	@GetMapping("/planbuild/{snapshotId}")
	public SimpleRestResponse planBuild(@PathVariable String snapshotId) {
		PlanBuildSnapshot ent = planBuildSnapshotService.getSnapshot(snapshotId);
		return SimpleRestResponse.ok("context", ent.getContext());
	}

	@ApiOperation("招中标快照写入")
	@PostMapping("/planbuild")
	public SimpleRestResponse setPlanBuild(RestRequest request) {
		try {
			PlanBuildSnapshot res = planBuildSnapshotService.save(request.getPlanBuildSnapshot());
			return SimpleRestResponse.ok("url", "/snapshot/bidding/" + res.getId());
		} catch (DaoException e) {
			return SimpleRestResponse.exception(e);
		}
	}

}
