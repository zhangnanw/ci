package org.yansou.ci.storage.merge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.db.model.project.PlanBuildData;
import org.yansou.ci.storage.service.project.BiddingDataService;
import org.yansou.ci.storage.service.project.PlanBuildDataService;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Administrator on 2017/6/12.
 */
@Component
public class ProjectMergeProcess implements Runnable {
    @Autowired
    PlanBuildDataService planBuildDataService;
    @Autowired
    BiddingDataService biddingDataService;


    @Override
    public void run() {
        ProjectVectorParse projectVectorParse = new ProjectVectorParse();
        try {
//            Stream<ProjectVector> stream1 = planBuildDataService.findAll().stream().map(projectVectorParse::parse);
            Stream<ProjectVector> stream2 = biddingDataService.findAll().stream().map(projectVectorParse::parse);
//            Stream.concat(stream1, stream2).collect(Collectors.groupingBy(a -> a.getA1()));
            PlanBuildData pi = planBuildDataService.findByProjectIdentifie("616684:2017-01-09-16-42");
            System.out.println(pi);
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }


}

