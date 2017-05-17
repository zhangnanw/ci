package wang.biaoshu.analyzer.nlpir.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ansj.domain.Result;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import wang.biaoshu.analyzer.Analyzer;
import wang.biaoshu.analyzer.util.TableScan;
import wang.biaoshu.analyzer.util.TextTools;

public class CategoryCodeAnalyzer implements Analyzer{
	
	@SuppressWarnings("unused")
	private static final Logger LOG=LoggerFactory.getLogger(CategoryCodeAnalyzer.class);
	
	@Override
	public boolean match(JSONObject obj){
		return true;
	}
	
	public List<Map<String, Object>> getCategory(){
		MysqlDataSource ds=new MysqlDataSource();
		ds.setURL("jdbc:mysql://biaoshuking.mysql.rds.aliyuncs.com:3306/hngp?useUnicode=true&characterEncoding=utf-8");
		ds.setUser("hngp");
		ds.setPassword("hngp123");
		QueryRunner qr=new QueryRunner(ds);
		String sql="select id,category_code,category_name,keywords from tab_bidd_category";
		List<Map<String, Object>> maplist =new ArrayList<Map<String,Object>>();
		try {
			maplist=qr.query(sql, new MapListHandler());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return maplist;
	}
	
	@Override
	public JSONObject analy(JSONObject obj){
		JSONObject res=new JSONObject();
		List<Map<String, Object>> maplist=getCategory();
		String category_code=null;
		for(int i=0;i<maplist.size()&&StringUtils.isBlank(category_code);i++){
			String category_name=(String) maplist.get(i).get("category_name");
			if(obj.getString("title").contains(category_name)){
				category_code=(String) maplist.get(i).get("category_code");
			}
			if(StringUtils.isBlank(category_code)){
				String text=obj.getString("context");
				text+=TableScan.filter(text);
				text=TextTools.normalize(text);
				if(StringUtils.isBlank(text)){
					res.put("category_code_list", "");
				}
				if(text.contains(category_name)){
					category_code=(String) maplist.get(i).get("category_code");
				}
			}
		}
		if(StringUtils.isBlank(category_code)){
			for(int j=0;j<maplist.size()&&StringUtils.isBlank(category_code);j++){
				Result categorys=ToAnalysis.parse((String) maplist.get(j).get("category_name"));
				for(int k=0;k<categorys.size();k++){
					String category=categorys.get(k).getName();
					if(category.length()==1||StringUtils.equals(category, "工程")||StringUtils.equals(category, "试验")||StringUtils.equals(category, "中央")||StringUtils.equals(category, "工具")
							||StringUtils.equals(category, "加工")||StringUtils.equals(category, "处理")||StringUtils.equals(category, "设施")||StringUtils.equals(category, "作业")
							||StringUtils.equals(category, "设备")||StringUtils.equals(category, "装备")||StringUtils.equals(category, "器材")||StringUtils.equals(category, "家具")
							||StringUtils.equals(category, "机械")||StringUtils.equals(category, "中心")||StringUtils.equals(category, "机")||StringUtils.equals(category, "液")) continue;
					else{
						if(obj.getString("title").contains(category)){
							category_code=(String) maplist.get(j).get("category_code");
						}
						if(StringUtils.isBlank(category_code)){
							String text=obj.getString("context");
							text+=TableScan.filter(text);
							text=TextTools.normalize(text);
							if(StringUtils.isBlank(text)){
								res.put("category_code_list", "");
							}
							if(text.contains(category)){
								category_code=(String) maplist.get(j).get("category_code");
							}
						}
					}
				}
			}
		}
		
		if(StringUtils.isNotBlank(category_code)){
			JSONArray json=new JSONArray();
			if(category_code.length()==4){
				json.add(category_code);
			}
			if(category_code.length()==8){
				json.add(category_code.substring(0, 4));
				json.add(category_code);
			}
			if(category_code.length()==12){
				json.add(category_code.substring(0, 4));
				json.add(category_code.substring(0, 8));
				json.add(category_code);
			}
			res.put("category_code_list", json);
		}
		return res;
	}
	
	
	
	
}
