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
import org.yansou.ci.core.rest.report.ReportRo;
import org.yansou.ci.core.rest.response.CountResponse;
import org.yansou.ci.core.rest.response.IdResponse;
import org.yansou.ci.web.business.project.BiddingDataBusiness;
import org.yansou.ci.web.business.project.MergeDataBusiness;
import org.yansou.ci.web.business.project.PlanBuildDataBusiness;
import org.yansou.ci.web.business.project.RecordDataBusiness;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liutiejun
 * @create 2017-05-14 0:38
 */
@Controller
@RequestMapping(value = "/biddingData")
public class BiddingDataController {

	private static final Logger LOG = LogManager.getLogger(BiddingDataController.class);

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
	public DataTablesOutput<BiddingData> showList(ModelMap model, HttpServletRequest request,
												  HttpServletResponse response) {
		DataTablesOutput<BiddingData> dataTablesOutput = biddingDataBusiness.pagination(request);

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

		String projectIdentifie = biddingData.getProjectIdentifie();// 项目唯一标识

		MergeData[] mergeDatas = mergeDataBusiness.findByProjectIdentifie(projectIdentifie);
		PlanBuildData[] planBuildDatas = planBuildDataBusiness.findByProjectIdentifie(projectIdentifie);
		RecordData[] recordDatas = recordDataBusiness.findByProjectIdentifie(projectIdentifie);

		model.addAttribute("biddingData", biddingData);
		model.addAttribute("mergeDatas", mergeDatas);
		model.addAttribute("planBuildDatas", planBuildDatas);
		model.addAttribute("recordDatas", recordDatas);

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
	public IdResponse save(BiddingData biddingData, ModelMap model, HttpServletRequest request,
						   HttpServletResponse response) {
		IdResponse restResponse = biddingDataBusiness.save(biddingData);

		IdRo idRo = restResponse.getResult();
		if (idRo != null) {
			idRo.setUrl("/biddingData/list");
		}

		return restResponse;
	}

	/**
	 * 更新
	 *
	 * @param biddingData
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
	public IdResponse update(BiddingData biddingData, Long[] biddingDataIds, Long[] mergeDataIds,
							 Long[] planBuildDataIds, Long[] recordDataIds, ModelMap model, HttpServletRequest request,
							 HttpServletResponse response) {
		mergeDataBusiness.updateChecked(biddingData.getProjectIdentifie(), mergeDataIds);
		planBuildDataBusiness.updateChecked(biddingData.getProjectIdentifie(), planBuildDataIds);
		recordDataBusiness.updateChecked(biddingData.getProjectIdentifie(), recordDataIds);

		IdResponse restResponse = biddingDataBusiness.update(biddingData);

		IdRo idRo = restResponse.getResult();
		if (idRo != null) {
			idRo.setUrl("/biddingData/list");
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
		CountResponse restResponse = biddingDataBusiness.deleteById(ids);

		return restResponse;
	}

	/**
	 * 进入中标图表页面
	 *
	 * @param model
	 * @param request
	 * @param response
	 *
	 * @return
	 *
	 * @author hzx
	 */
	@RequestMapping(value = "/bidChart")
	public String showBidChart(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

		return "views/biddingData/bidChart";
	}

	/**
	 * 进入招标图表页面
	 *
	 * @param model
	 * @param request
	 * @param response
	 *
	 * @return
	 *
	 * @author hzx
	 */
	@RequestMapping(value = "/tenderChart")
	public String showTenderChart(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

		return "views/biddingData/tenderChart";
	}

	/**
	 * 图表显示Bar柱状图
	 *
	 * @param model
	 * @param request
	 * @param response
	 *
	 * @return
	 *
	 * @author hzx
	 */
	@RequestMapping(value = "/showBar", method = RequestMethod.POST)
	@ResponseBody
	public Map showBar(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> reJson = new HashMap<String, Object>();
		List<String> campany = new ArrayList<>();
		for (int i = 1; i <= 20; i++) {
			campany.add("公司" + i);
		}
		List<Integer> mount = new ArrayList<>();
		for (int i = 1; i <= 20; i++) {
			mount.add((int) Math.round(Math.random() * 30));
		}
		reJson.put("campany", campany);
		reJson.put("mount", mount);
		return reJson;
	}

	/**
	 * 图表显示Pie饼图，中标产品分类
	 *
	 * @param model
	 * @param request
	 * @param response
	 *
	 * @return
	 *
	 * @throws java.text.ParseException
	 * @author hzx
	 */
	@RequestMapping(value = "/showPie", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> showtest(ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws java.text.ParseException {
		String dateString = "2017-05-06 ";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
		Date date = sdf.parse(dateString);
		ReportRo statisticsByProductType = biddingDataBusiness.statisticsByProductType(date, new Date());
		double[] series = statisticsByProductType.getSeries()[0].getData();
		String[] xdata = statisticsByProductType.getxAxis()[0].getData();

		List<Map<String, Object>> test = new ArrayList<Map<String, Object>>();
		Map<String, Object> reJson;
		for (int i = 0; i < series.length; i++) {
			reJson = new HashMap<String, Object>();
			reJson.put("name", xdata[i]);
			reJson.put("value", series[i]);
			test.add(reJson);

		}
		return test;

	}

	/**
	 * 图表显示Mul混合图
	 *
	 * @param model
	 * @param request
	 * @param response
	 *
	 * @return
	 *
	 * @author hzx
	 */
	@RequestMapping(value = "/showMul", method = RequestMethod.POST)
	@ResponseBody
	public Map showMul(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> reJson = new HashMap<String, Object>();
		List<Integer> count = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			count.add((int) Math.round(Math.random() * 100));
		}
		List<Integer> xdate = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			xdate.add(i);
		}

		List<Integer> mount = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			mount.add((int) Math.round(Math.random() * 100));
		}
		reJson.put("count", count);
		reJson.put("xdate", xdate);
		reJson.put("mount", mount);
		return reJson;
	}

	/**
	 * 图表显示Line折线图
	 *
	 * @param model
	 * @param request
	 * @param response
	 *
	 * @return
	 *
	 * @author hzx
	 */
	@RequestMapping(value = "/showLine", method = RequestMethod.POST)
	@ResponseBody
	public Map showLine(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> reJson = new HashMap<String, Object>();
		List<Integer> single = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			single.add((int) Math.round(Math.random() * 5));
		}
		List<Integer> xdate = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			xdate.add(i);
		}

		List<Integer> Multi = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			Multi.add((int) Math.round(Math.random() * 5));
		}
		reJson.put("single", single);
		reJson.put("xdate", xdate);
		reJson.put("Multi", Multi);
		return reJson;
	}

	/**
	 * 图表显示Live堆积图
	 *
	 * @param model
	 * @param request
	 * @param response
	 *
	 * @return
	 *
	 * @throws java.text.ParseException
	 * @author hzx
	 */
	@RequestMapping(value = "/showLive", method = RequestMethod.POST)
	@ResponseBody
	public ReportRo showLive(ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws java.text.ParseException {
		/*
		 * Map<String,Object> reJson=new HashMap<String,Object>(); List<Integer>
		 * china=new ArrayList<>(); List<Integer> europe=new ArrayList<>();
		 * List<Integer> northAmerica=new ArrayList<>(); List<Integer>
		 * newMarket=new ArrayList<>(); List<Integer> others=new ArrayList<>();
		 * for(int i=1;i<=5;i++){ china.add(30+(int) Math.round(Math.random() *
		 * 10)); europe.add(20+(int) Math.round(Math.random() * 10));
		 * northAmerica.add(15+(int) Math.round(Math.random() * 10));
		 * newMarket.add(20+(int) Math.round(Math.random() * 10));
		 * others.add(10+(int) Math.round(Math.random() * 10)); } List<String>
		 * xdate=new ArrayList<>(); for(int i=1;i<=5;i++){ xdate.add("Q"+i); }
		 * reJson.put("xdate", xdate); reJson.put("china", china);
		 * reJson.put("europe", europe); reJson.put("northAmerica",
		 * northAmerica); reJson.put("newMarket", newMarket);
		 * reJson.put("others", others); return reJson;
		 */
		String dateString = "2017-05-06 ";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
		Date date = sdf.parse(dateString);
		ReportRo statisticsByProjectProvince = biddingDataBusiness.statisticsByProjectProvince(date, new Date());
		statisticsByProjectProvince.getSeries()[0].getData();
		return statisticsByProjectProvince;
	}
}
