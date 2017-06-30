package org.yansou.ci.storage.merge;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.yansou.ci.storage.CiStorageApplication;
import org.yansou.ci.storage.repository.project.PlanBuildDataRepository;

import com.alibaba.fastjson.JSON;

/**
 * Created by Administrator on 2017/6/12.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CiStorageApplication.class)
public class TestProjectMergeProcess {
    @Autowired
    PlanBuildDataRepository planBuildDataService;
    @Autowired
    PlanBuildDataRepository biddingDataService;
    @Autowired
    ProjectMergeProcess projectMergeProcess;

    @Test
    public void testRun1() {
        projectMergeProcess.run();
    }

    @Test
    public void testProjectMergeProcess() {
        try {
            ProjectVectorParse parse = new ProjectVectorParse();
            List<ProjectVector> list = Stream.concat( planBuildDataService.findAll().stream().map( parse::parse ),
                    biddingDataService.findAll().stream().map( parse::parse ) ).collect( Collectors.toList() );
            Map<String, List<ProjectVector>> a1Group = list.stream().collect( Collectors.groupingBy( f -> f.getA1() ) );
            Map<String, List<ProjectVector>> mw1Group = list.stream().collect( Collectors.groupingBy( f -> f.getMw1() ) );
            Map<String, List<ProjectVector>> party_AGroup = list.stream()
                    .collect( Collectors.groupingBy( f -> f.getParty_a() ) );
            System.out.println( a1Group );
            System.out.println( mw1Group );
            System.out.println( party_AGroup );

            for (String key : a1Group.keySet()) {
                List<ProjectVector> a1 = a1Group.get( key );

            }
            for (String key : mw1Group.keySet()) {

            }
            for (String key : party_AGroup.keySet()) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGroup() throws Exception {
        ProjectVectorParse parse = new ProjectVectorParse();
        List<ProjectVector> list = Stream.concat( planBuildDataService.findAll().stream().map( parse::parse ),
                biddingDataService.findAll().stream().map( parse::parse ) ).collect( Collectors.toList() );
        Map<String, List<ProjectVector>> party_AGroup = list.stream()
                .collect( Collectors.groupingBy( f -> f.getA1() + f.getMw1() + getPartya( f.getParty_a() ) ) );
        for (List<ProjectVector> l : party_AGroup.values()) {
            if (l.size() > 1) {
                System.out.println( l.size() );
                for (ProjectVector pv : l) {
                    System.out.println( JSON.toJSONString( pv.getQuote() ) );
                }
            }
            System.out.println( "*************************" );
        }
    }

    String getPartya(String val) {
        if (null == val) {
            return "";
        }
        val = val.replaceAll( "\\pP.*?$", "" );
        val = val.replaceAll( "（.*?）", "" );
        val = val.replaceAll( "\\(.*?\\)", "" );
        val = val.replaceAll( "[\\(\\)（）]", "" );
        System.out.println( val );
        return val;
    }

    @Test
    public void testList1() throws Exception {
        planBuildDataService.findAll().stream().sorted( (a, b) -> a.getId().compareTo( b.getId() ) ).map( JSON::toJSONString ).forEach( System.out::println );
    }

    @Test
    public void testRun() {
        try {
            projectMergeProcess.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testView() {

    }

}
