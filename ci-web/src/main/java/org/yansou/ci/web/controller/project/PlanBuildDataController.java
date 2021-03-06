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
import org.yansou.ci.core.db.model.project.BiddingData;
import org.yansou.ci.core.db.model.project.MergeData;
import org.yansou.ci.core.db.model.project.PlanBuildData;
import org.yansou.ci.core.db.model.project.RecordData;
import org.yansou.ci.core.rest.model.IdRo;
import org.yansou.ci.core.rest.response.CountResponse;
import org.yansou.ci.core.rest.response.IdResponse;
import org.yansou.ci.web.business.project.BiddingDataBusiness;
import org.yansou.ci.web.business.project.MergeDataBusiness;
import org.yansou.ci.web.business.project.PlanBuildDataBusiness;
import org.yansou.ci.web.business.project.RecordDataBusiness;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liutiejun
 * @create 2017-05-14 0:38
 */
@Controller
@RequestMapping(value = "/planBuildData")
public class PlanBuildDataController {

	private static final Logger LOG = LogManager.getLogger(PlanBuildDataController.class);

	@Autowired
	private BiddingDataBusiness biddingDataBusiness;

	@Autowired
	private MergeDataBusiness mergeDataBusiness;

	@Autowired
	private PlanBuildDataBusiness planBuildDataBusiness;

	@Autowired
	private RecordDataBusiness recordDataBusiness;

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
		return "views/planBuildData/list";
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
	public DataTablesOutput<PlanBuildData> showList(ModelMap model, HttpServletRequest request, HttpServletResponse
			response) {
		DataTablesOutput<PlanBuildData> dataTablesOutput = planBuildDataBusiness.pagination(request);

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
		return "views/planBuildData/add";
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
		PlanBuildData planBuildData = planBuildDataBusiness.findById(id);

		String projectIdentifie = planBuildData.getProjectIdentifie();// 项目唯一标识

		BiddingData[] biddingDatas = biddingDataBusiness.findByProjectIdentifie(projectIdentifie);
		MergeData[] mergeDatas = mergeDataBusiness.findByProjectIdentifie(projectIdentifie);
		RecordData[] recordDatas = recordDataBusiness.findByProjectIdentifie(projectIdentifie);

		model.addAttribute("planBuildData", planBuildData);
		model.addAttribute("biddingDatas", biddingDatas);
		model.addAttribute("mergeDatas", mergeDatas);
		model.addAttribute("recordDatas", recordDatas);

		return "views/planBuildData/edit";
	}

	/**
	 * 新增
	 *
	 * @param planBuildData
	 * @param model
	 * @param request
	 * @param response
	 *
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public IdResponse save(PlanBuildData planBuildData, ModelMap model, HttpServletRequest request,
						   HttpServletResponse response) {
		IdResponse restResponse = planBuildDataBusiness.save(planBuildData);

		IdRo idRo = restResponse.getResult();
		if (idRo != null) {
			idRo.setUrl("/planBuildData/list");
		}

		return restResponse;
	}

	/**
	 * 更新
	 *
	 * @param planBuildData
	 * @param biddingDataIds
	 * @param mergeDataIds
	 * @param planBuildDataIds
	 * @param recordDataIds
	 * @param model
	 * @param request
	 * @param response
	 *
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public IdResponse update(PlanBuildData planBuildData, Long[] biddingDataIds, Long[] mergeDataIds, Long[]
			planBuildDataIds, Long[] recordDataIds, ModelMap model, HttpServletRequest request, HttpServletResponse
									 response) {
		biddingDataBusiness.updateChecked(planBuildData.getProjectIdentifie(), biddingDataIds);
		mergeDataBusiness.updateChecked(planBuildData.getProjectIdentifie(), mergeDataIds);
		recordDataBusiness.updateChecked(planBuildData.getProjectIdentifie(), recordDataIds);

		IdResponse restResponse = planBuildDataBusiness.update(planBuildData);

		IdRo idRo = restResponse.getResult();
		if (idRo != null) {
			idRo.setUrl("/planBuildData/list");
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
		CountResponse restResponse = planBuildDataBusiness.deleteById(ids);

		return restResponse;
	}

}
