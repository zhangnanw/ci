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
import org.yansou.ci.core.model.project.BiddingData;
import org.yansou.ci.core.model.project.WinCompany;
import org.yansou.ci.core.rest.model.IdRo;
import org.yansou.ci.core.rest.response.CountResponse;
import org.yansou.ci.core.rest.response.IdResponse;
import org.yansou.ci.web.business.project.BiddingDataBusiness;
import org.yansou.ci.web.business.project.WinCompanyBusiness;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liutiejun
 * @create 2017-05-14 0:38
 */
@Controller
@RequestMapping(value = "/winCompany")
public class WinCompanyController {

	private static final Logger LOG = LogManager.getLogger(WinCompanyController.class);

	@Autowired
	private BiddingDataBusiness biddingDataBusiness;

	@Autowired
	private WinCompanyBusiness winCompanyBusiness;

	/**
	 * 进入列表页面
	 *
	 * @param biddingDataId
	 * @param model
	 * @param request
	 * @param response
	 *
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Long biddingDataId, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		BiddingData biddingData = biddingDataBusiness.findById(biddingDataId);

		model.addAttribute("biddingData", biddingData);

		return "views/winCompany/list";
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
	public DataTablesOutput<WinCompany> showList(ModelMap model, HttpServletRequest request, HttpServletResponse
			response) {
		DataTablesOutput<WinCompany> dataTablesOutput = winCompanyBusiness.pagination(request);

		return dataTablesOutput;
	}

	/**
	 * 进入新增页面
	 *
	 * @param biddingDataId
	 * @param model
	 * @param request
	 * @param response
	 *
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Long biddingDataId, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		BiddingData biddingData = biddingDataBusiness.findById(biddingDataId);

		model.addAttribute("biddingData", biddingData);

		return "views/winCompany/add";
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
		WinCompany winCompany = winCompanyBusiness.findById(id);
		BiddingData biddingData = winCompany.getBiddingData();

		LOG.info("winCompany: {}", winCompany);

		model.addAttribute("winCompany", winCompany);
		model.addAttribute("biddingData", biddingData);

		return "views/winCompany/edit";
	}

	/**
	 * 新增
	 *
	 * @param winCompany
	 * @param model
	 * @param request
	 * @param response
	 *
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public IdResponse save(Long biddingDataId, WinCompany winCompany, ModelMap model, HttpServletRequest request,
						   HttpServletResponse response) {
		IdResponse restResponse = winCompanyBusiness.save(biddingDataId, winCompany);

		IdRo idRo = restResponse.getResult();
		if (idRo != null) {
			idRo.setUrl("/winCompany/list?biddingDataId=" + biddingDataId);
		}

		return restResponse;
	}

	/**
	 * 更新
	 *
	 * @param winCompany
	 * @param model
	 * @param request
	 * @param response
	 *
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public IdResponse update(Long biddingDataId, WinCompany winCompany, ModelMap model, HttpServletRequest request,
							 HttpServletResponse response) {
		IdResponse restResponse = winCompanyBusiness.update(biddingDataId, winCompany);

		IdRo idRo = restResponse.getResult();
		if (idRo != null) {
			idRo.setUrl("/winCompany/list?biddingDataId=" + biddingDataId);
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
		return null;
	}

}
