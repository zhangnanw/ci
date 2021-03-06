package org.yansou.ci.storage.controller.project;

import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yansou.ci.common.page.PageCriteria;
import org.yansou.ci.common.page.Pagination;
import org.yansou.ci.core.db.model.project.Competitor;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.SimpleRestResponse;
import org.yansou.ci.storage.service.project.CompetitorService;

import java.util.List;

/**
 * @author liutiejun
 * @create 2017-05-14 0:28
 */
@RestController
@RequestMapping(value = "/competitor")
public class CompetitorController {

	private static final Logger LOG = LogManager.getLogger(CompetitorController.class);

	@Autowired
	private CompetitorService competitorService;

	@ApiOperation(value = "分页获取数据详细信息")
	@PostMapping(value = "/pagination")
	public SimpleRestResponse pagination(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		PageCriteria pageCriteria = restRequest.getPageCriteria();

		Pagination<Competitor> pagination = competitorService.pagination(pageCriteria);

		return SimpleRestResponse.ok(pagination);
	}

	@ApiOperation(value = "获取数据详细信息")
	@PostMapping(value = "/find")
	public SimpleRestResponse find(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		Competitor competitor = restRequest.getCompetitor();

		if (competitor == null) {// 查询所有的数据
			List<Competitor> competitorList = competitorService.findAll();

			return SimpleRestResponse.ok(competitorList.toArray(new Competitor[0]));
		}

		Long id = competitor.getId();
		if (id != null) {// 根据ID查询
			Competitor otherCompetitor = competitorService.findById(id);

			return SimpleRestResponse.ok(otherCompetitor);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "新增数据")
	@PostMapping(value = "/save")
	public SimpleRestResponse save(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		Competitor competitor = restRequest.getCompetitor();

		LOG.info("competitor: {}", competitor);

		if (competitor != null) {// 单个新增
			competitor = competitorService.save(competitor);

			return SimpleRestResponse.id(competitor.getId());
		}

		Competitor[] competitors = restRequest.getCompetitors();
		if (ArrayUtils.isNotEmpty(competitors)) {// 批量新增
			competitors = competitorService.save(competitors);

			return SimpleRestResponse.ok(competitors);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "更新数据")
	@PostMapping(value = "/update")
	public SimpleRestResponse update(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		Competitor competitor = restRequest.getCompetitor();
		if (competitor != null) {// 单个更新
			competitorService.updateNotNullField(competitor);

			return SimpleRestResponse.id(competitor.getId());
		}

		Competitor[] competitors = restRequest.getCompetitors();
		if (ArrayUtils.isNotEmpty(competitors)) {// 批量更新
			int count = competitorService.updateNotNullField(competitors);

			return SimpleRestResponse.ok("count", count);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "删除数据，不做物理删除，只更新对应的数据状态")
	@PostMapping(value = "/delete")
	public SimpleRestResponse delete(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		Long[] ids = restRequest.getIds();

		int count = competitorService.deleteById(ids);

		return SimpleRestResponse.ok("count", count);
	}

}
