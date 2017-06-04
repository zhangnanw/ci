package org.yansou.ci.web.controller.project;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yansou.ci.core.model.project.SnapshotInfo;
import org.yansou.ci.web.business.project.SnapshotInfoBusiness;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liutiejun
 * @create 2017-05-31 21:23
 */
@Controller
@RequestMapping("/snapshotInfo")
public class SnapshotInfoController {

	private static final Logger LOG = LogManager.getLogger(SnapshotInfoController.class);

	@Autowired
	private SnapshotInfoBusiness snapshotInfoBusiness;

	@GetMapping("/detail/{snapshotId}")
	public String detail(@PathVariable String snapshotId, ModelMap model, HttpServletRequest request,
						 HttpServletResponse response) {
		SnapshotInfo snapshotInfo = snapshotInfoBusiness.findBySnapshotId(snapshotId);

		model.addAttribute("snapshotInfo", snapshotInfo);

		return "views/snapshotInfo/detail";
	}

}
