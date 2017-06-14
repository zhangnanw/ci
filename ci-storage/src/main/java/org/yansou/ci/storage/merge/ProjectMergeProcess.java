package org.yansou.ci.storage.merge;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.yansou.ci.storage.repository.project.BiddingDataRepository;
import org.yansou.ci.storage.repository.project.PlanBuildDataRepository;

/**
 * Created by Administrator on 2017/6/12.
 */
public class ProjectMergeProcess implements Runnable {
	@Autowired
	PlanBuildDataRepository planBuildDataService;
	@Autowired
	BiddingDataRepository biddingDataService;

	@Override
	public void run() {
		ProjectVectorParse parse = new ProjectVectorParse();
		List<ProjectVector> list = Stream.concat(planBuildDataService.findAll().stream().map(parse::parse),
				biddingDataService.findAll().stream().map(parse::parse)).collect(Collectors.toList());
		
	}
}
