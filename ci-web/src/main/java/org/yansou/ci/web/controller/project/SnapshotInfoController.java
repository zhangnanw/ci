package org.yansou.ci.web.controller.project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author liutiejun
 * @create 2017-05-31 21:23
 */
@Controller
@RequestMapping("/snapshotInfo")
public class SnapshotInfoController {

	@GetMapping("/{dataType}/{dataId}")
	public String detail(@PathVariable String dataType, @PathVariable String dataId) {
		return null;
	}

}
