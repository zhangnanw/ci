package org.yansou.ci.web.controller.project;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yansou.ci.common.datatables.mapping.DataTablesOutput;
import org.yansou.ci.core.db.model.project.Competitor;
import org.yansou.ci.core.rest.model.IdRo;
import org.yansou.ci.core.rest.response.CountResponse;
import org.yansou.ci.core.rest.response.IdResponse;
import org.yansou.ci.web.business.project.CompetitorBusiness;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liutiejun
 * @create 2017-05-14 0:38
 */
@Controller
@RequestMapping(value = "/competitor")
public class CompetitorController {

	private static final Logger LOG = LogManager.getLogger(CompetitorController.class);

	@Autowired
	private CompetitorBusiness competitorBusiness;

	/**
	 * 进入列表页面
	 *
	 * @param model
	 * @param request
	 * @param response
	 *
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		return "views/competitor/list";
	}

	/**
	 * 显示列表页数据
	 *
	 * @param model
	 * @param request
	 * @param response
	 *
	 * @return
	 */
	@RequestMapping(value = "/showList", method = RequestMethod.POST)
	@ResponseBody
	public DataTablesOutput<Competitor> showList(ModelMap model, HttpServletRequest request, HttpServletResponse
			response) {
		DataTablesOutput<Competitor> dataTablesOutput = competitorBusiness.pagination(request);

		return dataTablesOutput;
	}

	/**
	 * 进入新增页面
	 *
	 * @param model
	 * @param request
	 * @param response
	 *
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		return "views/competitor/add";
	}

	/**
	 * 进入更新页面
	 *
	 * @param id
	 * @param model
	 * @param request
	 * @param response
	 *
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		Competitor competitor = competitorBusiness.findById(id);

		model.addAttribute("competitor", competitor);

		return "views/competitor/edit";
	}

	/**
	 * 新增
	 *
	 * @param competitor
	 * @param model
	 * @param request
	 * @param response
	 *
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public IdResponse save(Competitor competitor, ModelMap model, HttpServletRequest request, HttpServletResponse
			response) {
		IdResponse restResponse = competitorBusiness.save(competitor);

		IdRo idRo = restResponse.getResult();
		if (idRo != null) {
			idRo.setUrl("/competitor/list");
		}

		return restResponse;
	}

	/**
	 * 更新
	 *
	 * @param competitor
	 * @param model
	 * @param request
	 * @param response
	 *
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public IdResponse update(Competitor competitor, ModelMap model, HttpServletRequest request, HttpServletResponse
			response) {
		IdResponse restResponse = competitorBusiness.update(competitor);

		IdRo idRo = restResponse.getResult();
		if (idRo != null) {
			idRo.setUrl("/competitor/list");
		}

		return restResponse;
	}

	/**
	 * 删除
	 *
	 * @param ids
	 * @param model
	 * @param request
	 * @param response
	 *
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	public CountResponse delete(Long[] ids, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		CountResponse restResponse = competitorBusiness.deleteById(ids);

		return restResponse;
	}

}
