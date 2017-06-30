package org.yansou.ci.storage.merge;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yansou.ci.common.utils.PojoUtils;
import org.yansou.ci.common.utils.ReflectUtils;
import org.yansou.ci.core.db.model.project.BiddingData;
import org.yansou.ci.core.db.model.project.PlanBuildData;
import org.yansou.ci.storage.repository.project.BiddingDataRepository;
import org.yansou.ci.storage.service.project.BiddingDataService;
import org.yansou.ci.storage.service.project.PlanBuildDataService;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2017/6/12.
 */
@Component
public class ProjectMergeProcess implements Runnable {

    final static private Logger LOG = LogManager.getLogger( ProjectMergeProcess.class );
    @Autowired
    private PlanBuildDataService planBuildDataService;
    @Autowired
    private BiddingDataService biddingDataService;

    @Override
    public void run() {
        ProjectVectorParse parse = new ProjectVectorParse();
        List<ProjectVector> list = new ArrayList<>();
        try {
            planBuildDataService.findAll().stream().map( parse::parse ).filter( Objects::nonNull ).forEach( list::add );
            biddingDataService.findAll().stream().map( parse::parse ).filter( Objects::nonNull ).forEach( list::add );
        } catch (Exception e) {
            throw new IllegalStateException( e );
        }
        Map<Object, List<ProjectVector>> groupMap = list.stream()
                .collect( Collectors.groupingBy( this::groupId ) );
        for (Entry<Object, List<ProjectVector>> ent : groupMap.entrySet()) {
            List<ProjectVector> group = ent.getValue();
            List<List<ProjectVector>> groupList = new ArrayList<>();

            LOG.info( "group.size:" + group.size() );
            // 如果组大于10
            if (group.size() > 10) {
                // 再次分组
                group.stream().collect( Collectors.groupingBy( pv -> pv.getA2() ) ).values().forEach( groupList::add );
            } else {
                groupList.add( group );
            }
            int groupNumber = 0;
            for (List<ProjectVector> doneGroup : groupList) {
                groupNumber++;
                if (groupList.size() > 1) {
                    LOG.info( "new group.size:" + group.size() );
                }
                // 拿到所有唯一标识
                List<String> idfList = new ArrayList<>();

                for (ProjectVector pv : doneGroup) {
                    System.out.println( JSON.toJSONString( pv.getQuote() ) );
                    String projectIdentifie = ReflectUtils.get( pv.getQuote(), "projectIdentifie" );
                    if (StringUtils.isNotBlank( projectIdentifie )) {
                        idfList.add( projectIdentifie );
                    }
                }
                //重新生成这个组的ID
                String newProjectID = ent.getKey().toString() + "_" + groupNumber;
                for (ProjectVector pv : doneGroup) {
                    if (!PojoUtils.set( pv.getQuote(), "projectIdentifie", newProjectID )) {
                        System.out.println( "没有找到对应字段。" );
                    }
                }
                doneGroup.stream().map( pv -> pv.getQuote() ).forEach( this::save );
                System.out.println( "*************************" );
            }
        }
    }

    private String groupId(ProjectVector f) {

        String partya = getPartya( f.getParty_a() );

        String gid = f.getA1() + ':' + f.getMw1() + ';' + partya;
        System.out.println( gid );
        return gid;
    }

    private void save(Object pojo) {
        try {
            if (pojo instanceof BiddingData) {
                biddingDataService.save( (BiddingData) pojo );
            } else if (pojo instanceof PlanBuildData) {
                planBuildDataService.save( (PlanBuildData) pojo );
            } else {
                throw new IllegalStateException( "不能保存的類型" );
            }
        } catch (Exception e) {
            throw new IllegalStateException( e );
        }
    }

    /**
     * 挑选使用频率最大的ID。
     *
     * @param idfs
     * @return
     */
    String getProjectIdentifie(String... idfs) {
        HashMap<String, AtomicInteger> countMap = new HashMap<>();
        for (String idf : idfs) {
            if (StringUtils.isNotBlank( idf )) {
                AtomicInteger count = countMap.get( idf );
                if (null == count) {
                    count = new AtomicInteger();
                    countMap.put( idf, count );
                }
                count.incrementAndGet();
            }
        }
        if (countMap.isEmpty()) {
            return UUID.randomUUID().toString();
        } else {
            String idf = null;
            int maxCount = 0;
            for (Entry<String, AtomicInteger> countEnt : countMap.entrySet()) {
                int count = countEnt.getValue().get();
                if (count > maxCount) {
                    idf = countEnt.getKey();
                    maxCount = count;
                }
            }
            return idf;
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

    public ProjectVector find(ProjectVector srcProjectVector, Collection<ProjectVector> collect) {
        double maxScore = 0;
        ProjectVector maxProjectVector = null;
        for (ProjectVector destProjectVector : collect) {
            double score = VectorSimilarity.tanimotoDistance( srcProjectVector, destProjectVector );
            if (maxScore < score) {
                maxProjectVector = destProjectVector;
                maxScore = score;
            }
        }
        return maxProjectVector;
    }
}
