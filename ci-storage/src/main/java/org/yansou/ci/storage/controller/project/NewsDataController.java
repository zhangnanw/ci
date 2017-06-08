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
import org.yansou.ci.core.db.model.project.NewsData;
import org.yansou.ci.core.rest.request.RestRequest;
import org.yansou.ci.core.rest.response.SimpleRestResponse;
import org.yansou.ci.storage.service.project.NewsDataService;

import java.util.List;

/**
 * @author liutiejun
 * @create 2017-05-14 0:28
 */
@RestController
@RequestMapping(value = "/newsData")
public class NewsDataController {

	private static final Logger LOG = LogManager.getLogger(NewsDataController.class);

	@Autowired
	private NewsDataService newsDataService;

	@ApiOperation(value = "分页获取数据详细信息")
	@PostMapping(value = "/pagination")
	public SimpleRestResponse pagination(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		PageCriteria pageCriteria = restRequest.getPageCriteria();

		Pagination<NewsData> pagination = newsDataService.pagination(pageCriteria);

		return SimpleRestResponse.ok(pagination);
	}

	@ApiOperation(value = "获取数据详细信息")
	@PostMapping(value = "/find")
	public SimpleRestResponse find(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		NewsData newsData = restRequest.getNewsData();

		if (newsData == null) {// 查询所有的数据
			List<NewsData> newsDataList = newsDataService.findAll();

			return SimpleRestResponse.ok(newsDataList.toArray(new NewsData[0]));
		}

		Long id = newsData.getId();
		if (id != null) {// 根据ID查询
			NewsData otherNewsData = newsDataService.findById(id);

			return SimpleRestResponse.ok(otherNewsData);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "新增数据")
	@PostMapping(value = "/save")
	public SimpleRestResponse save(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		NewsData newsData = restRequest.getNewsData();

		LOG.info("newsData: {}", newsData);

		if (newsData != null) {// 单个新增
			newsData = newsDataService.save(newsData);

			return SimpleRestResponse.id(newsData.getId());
		}

		NewsData[] newsDatas = restRequest.getNewsDatas();
		if (ArrayUtils.isNotEmpty(newsDatas)) {// 批量新增
			newsDatas = newsDataService.save(newsDatas);

			return SimpleRestResponse.ok(newsDatas);
		}

		return SimpleRestResponse.exception();
	}

	@ApiOperation(value = "更新数据")
	@PostMapping(value = "/update")
	public SimpleRestResponse update(@RequestBody RestRequest restRequest) throws Exception {
		if (restRequest == null) {
			return SimpleRestResponse.exception("请求参数为空");
		}

		NewsData newsData = restRequest.getNewsData();
		if (newsData != null) {// 单个更新
			newsDataService.updateNotNullField(newsData);

			return SimpleRestResponse.id(newsData.getId());
		}

		NewsData[] newsDatas = restRequest.getNewsDatas();
		if (ArrayUtils.isNotEmpty(newsDatas)) {// 批量更新
			newsDatas = newsDataService.update(newsDatas);

			return SimpleRestResponse.ok(newsDatas);
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

		int count = newsDataService.deleteById(ids);

		return SimpleRestResponse.ok("count", count);
	}

}
