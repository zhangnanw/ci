package com.yansou.ci.web.controller.project;

import com.yansou.ci.common.datatables.DataTableVo;
import com.yansou.ci.core.model.project.BiddingData;
import com.yansou.ci.core.rest.response.CountResponse;
import com.yansou.ci.core.rest.response.IdResponse;
import com.yansou.ci.web.business.project.BiddingDataBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liutiejun
 * @create 2017-05-14 0:38
 */
@Controller
@RequestMapping(value = "/biddingData")
public class BiddingDataController {

	@Autowired
	private BiddingDataBusiness biddingDataBusiness;

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
		return "views/biddingData/list";
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
	public DataTableVo<BiddingData> showList(ModelMap model, HttpServletRequest request, HttpServletResponse
			response) {
		DataTableVo<BiddingData> dataTableVo = biddingDataBusiness.pagination(request);

		return dataTableVo;
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
		return "views/biddingData/add";
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
		BiddingData biddingData = biddingDataBusiness.findById(id);
		model.addAttribute("biddingData", biddingData);

		return "views/biddingData/edit";
	}

	/**
	 * 新增
	 *
	 * @param biddingData
	 * @param model
	 * @param request
	 * @param response
	 *
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public IdResponse save(BiddingData biddingData, ModelMap model, HttpServletRequest request, HttpServletResponse
			response) {
		return null;
	}

	/**
	 * 更新
	 *
	 * @param biddingData
	 * @param model
	 * @param request
	 * @param response
	 *
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public IdResponse update(BiddingData biddingData, ModelMap model, HttpServletRequest request, HttpServletResponse
			response) {
		return null;
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
