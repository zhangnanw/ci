package org.yansou.ci.storage.merge;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yansou.ci.common.utils.PojoUtils;
import org.yansou.ci.common.utils.ReflectUtils;
import org.yansou.ci.core.db.model.project.ProjectInfo;
import org.yansou.ci.storage.repository.project.BiddingDataRepository;
import org.yansou.ci.storage.repository.project.PlanBuildDataRepository;
import org.yansou.ci.storage.repository.project.ProjectInfoRepository;

/**
 * Created by Administrator on 2017/6/12.
 */
@Component
public class ProjectMergeProcess implements Runnable {
	@Autowired
	PlanBuildDataRepository planBuildDataService;
	@Autowired
	BiddingDataRepository biddingDataService;
	@Autowired
	ProjectInfoRepository projectInfoRepository;

	@Override
	public void run() {

		ProjectVectorParse parse = new ProjectVectorParse();
		List<ProjectVector> projectVectorList = Stream.concat(planBuildDataService.findAll().stream().map(parse::parse),
				biddingDataService.findAll().stream().map(parse::parse)).collect(Collectors.toList());
		LinkedList<ProjectVector> fifo = new LinkedList<>(projectVectorList);
		while (true) {
			ProjectVector src = fifo.poll();
			if (null == src) {
				break;
			}
			ProjectVector dest = find(src, fifo);
			ProjectInfo projectInfo = new ProjectInfo();

			Date srcDate = ReflectUtils.get(src, "quote.publishTime");
			Date destDate = ReflectUtils.get(dest, "quote.publishTime");
			if (null != dest) {
				if (null != srcDate && null != destDate && srcDate.before(destDate)) {
					PojoUtils.copyTo(src.getQuote(), projectInfo);
					PojoUtils.copyTo(dest.getQuote(), projectInfo);
				} else {
					PojoUtils.copyTo(dest.getQuote(), projectInfo);
					PojoUtils.copyTo(src.getQuote(), projectInfo);
				}
			} else {
				PojoUtils.copyTo(src.getQuote(), projectInfo);

			}
			projectInfoRepository.save(projectInfo);
		}
	}

	public ProjectVector find(ProjectVector srcProjectVector, Collection<ProjectVector> collect) {
		double maxScore = 0;
		ProjectVector maxProjectVector = null;
		for (ProjectVector destProjectVector : collect) {
			double score = VectorSimilarity.tanimotoDistance(srcProjectVector, destProjectVector);
			if (maxScore < score) {
				maxProjectVector = destProjectVector;
				maxScore = score;
			}
		}
		return maxProjectVector;
	}
}
