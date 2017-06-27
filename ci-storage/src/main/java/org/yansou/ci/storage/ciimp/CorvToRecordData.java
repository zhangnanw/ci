package org.yansou.ci.storage.ciimp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yansou.ci.common.utils.JSONArrayHandler;
import org.yansou.ci.common.utils.JSONUtils;
import org.yansou.ci.common.utils.RegexUtils;
import org.yansou.ci.core.db.model.project.RecordData;
import org.yansou.ci.core.db.model.project.SnapshotInfo;
import org.yansou.ci.data.mining.analyzer.impl.AreaAnalyzer;
import org.yansou.ci.storage.service.project.RecordDataService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Component
public class CorvToRecordData extends AbsStatistics {
    @Autowired
    private RecordDataService recordDataService;

    public void run() {
        try {
            JSONArray arr = qr.query( "SELECT * FROM tab_put_on_record WHERE url not in(SELECT url FROM `" + TmpConfigRead.getCfgName() + "`.ci_record_data)", JSONArrayHandler.create() );
            for (int i = 0; i < arr.size(); i++) {
                JSONObject obj = arr.getJSONObject( i );
                RecordData rd = new PutIn2RecordData( obj ).get();
                SnapshotInfo snap = new SnapshotInfo();
                String snapshotId = UUID.randomUUID().toString();
                snap.setSnapshotId( snapshotId );
                snap.setDataType( 3 );
                snap.setContext( obj.getString( "context" ) );
                rd.setSnapshotId( snapshotId );
                recordDataService.saveDataAndSnapshotInfo( rd, snap );
            }
        } catch (Exception e) {
            throw new IllegalStateException( e );
        }
    }

    private static class PutIn2RecordData {
        private JSONObject srcObj;

        public PutIn2RecordData(JSONObject srcObj) {
            this.srcObj = srcObj;
        }

        public RecordData get() {
            RecordData rd = new RecordData();
            rd.setUrl( srcObj.getString( "url" ) );
            rd.setHtmlSource( srcObj.getString( "context" ) );
            rd.setProjectName( srcObj.getString( "title" ) );
            if (StringUtils.isBlank( rd.getProjectName() )) {
                rd.setProjectName( srcObj.getString( "anchor_text" ) );
            }
            String projectProvince = toProvince();
            if (org.apache.commons.lang3.StringUtils.isNotBlank( projectProvince )) {
                codeMap.keySet().stream().filter( Objects::nonNull ).filter( key -> key.contains( projectProvince ) )
                        .forEach( key -> {
                            rd.setProjectProvince( codeMap.get( key ) );
                        } );
            }
            rd.setApprovalTime( toApprovalTime() );
            rd.setProjectCity( toCity() );
            rd.setProjectDistrict( toDistrict() );
            return rd;
        }

        /**
         * 地区解析器
         */
        private static AreaAnalyzer AREA_ANALYZER = AreaAnalyzer.getExamples();
        final static private Map<String, Integer> codeMap = Maps.newHashMap();

        static {
            String[] arr = "1:北京市;2:天津市;3:上海市;4:重庆市;5:安徽省;6:福建省;7:甘肃省;8:广东省;9:贵州省;10:海南省;11:河北省;12:河南省;13:湖北省;14:湖南省;15:吉林省;16:江苏省;17:江西省;18:辽宁省;19:青海省;20:山东省;21:山西省;22:陕西省;23:四川省;24:云南省;25:浙江省;26:台湾省;27:黑龙江省;28:西藏自治区;29:内蒙古自治区;30:宁夏回族自治区;31:广西壮族自治区;32:新疆维吾尔自治区;33:香港特别行政区;34:澳门特别行政区"
                    .split( ";" );
            for (String line : arr) {
                String[] as = line.split( ":" );
                codeMap.put( as[1], Integer.valueOf( as[0] ) );
            }
        }

        private String toProvince() {
            JSONObject res = AREA_ANALYZER.analy( srcObj );
            JSONArray arr = res.getJSONArray( "area" );
            if (JSONUtils.isEmpty( arr )) {
                return null;
            }
            return arr.getString( 0 );
        }

        private String toCity() {
            JSONObject res = AREA_ANALYZER.analy( srcObj );
            JSONArray arr = res.getJSONArray( "area" );
            if (JSONUtils.isEmpty( arr ) || arr.size() <= 1) {
                return null;
            }
            return arr.getString( 1 );
        }

        private String toDistrict() {
            JSONObject res = AREA_ANALYZER.analy( srcObj );
            JSONArray arr = res.getJSONArray( "area" );
            if (JSONUtils.isEmpty( arr ) || arr.size() <= 2) {
                return null;
            }
            return arr.getString( 2 );
        }

        /**
         * 获得审批时间
         *
         * @return
         */
        private Date toApprovalTime() {
            String ctx = srcObj.getString( "context" );
            java.text.SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
            JSONObject t = RegexUtils.regexToJSONObject( "(?<yyyy>(?:20)[0-9]{2})[-/年](?<MM>[0-1]?[0-9])[-/月](?<dd>[0-3]?[0-9])", ctx );
            if (!t.isEmpty()) {
                try {
                    return sdf.parse( t.getString( "yyyy" ) + '-' + t.getString( "MM" ) + '-' + t.getString( "dd" ) );
                } catch (ParseException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            return null;
        }
    }

}
