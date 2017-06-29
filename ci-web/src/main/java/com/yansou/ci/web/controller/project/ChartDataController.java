package com.yansou.ci.web.controller.project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yansou.ci.core.model.chart.ChartData;
import com.yansou.ci.web.business.project.ChartDataBusiness;

/**
 * 
 * @author zhangyingying
 *
 */
@Controller
@RequestMapping(value="/chartData")
public class ChartDataController {
	
	@Autowired
	private ChartDataBusiness chartDataBusiness;
	
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public String list(ModelMap model,HttpServletRequest request, HttpServletResponse response) throws ParseException{
		String s=request.getParameter("ctBeginTime");
		String ss=request.getParameter("ctEndTime");
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date ctBeginTime=formatter.parse(s);
		Date ctEndTime=formatter.parse(ss);
		ChartData[] chartlist=chartDataBusiness.findAll(ctBeginTime, ctEndTime);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("RowCount", chartlist.length);
		map.put("Rows", chartlist);
		return "chart/chart_zxt.jsp";
	}
}
