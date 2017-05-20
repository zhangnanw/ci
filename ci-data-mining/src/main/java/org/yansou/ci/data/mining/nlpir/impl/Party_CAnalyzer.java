package org.yansou.ci.data.mining.nlpir.impl; 
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yansou.ci.data.mining.Analyzer;
import org.yansou.ci.data.mining.ansj.Ansj;
import org.yansou.ci.data.mining.util.ErrUtils;
import org.yansou.ci.data.mining.util.Readability;
import org.yansou.ci.data.mining.util.RegexUtils;
import org.yansou.ci.data.mining.util.TableScan;
import org.yansou.ci.data.mining.util.TextTools;

import com.alibaba.fastjson.JSONObject;
/**
 * 
 * @author LENOVO
 *
 */
public class Party_CAnalyzer implements Analyzer {
	private static final Logger LOG = LoggerFactory.getLogger(Party_CAnalyzer.class);
	@Override
	public boolean match(JSONObject obj) {
		return true;
	}
	@Override
	public JSONObject analy(JSONObject obj) {
		JSONObject res = new JSONObject();
		String err=new String();
		String text = obj.getString("context");
		text+=TableScan.filter(text);
		Readability rb = new Readability(text);
		rb.init();
		String html = rb.outerHtml();
		if (StringUtils.length(html) > 200) {
			text=html;
		}
		text = TextTools.normalize(text);
		if(text.contains("投诉人")&&text.contains("被投诉人"))
			return res;
		String sd=Party_AAnalyzer.sa(text);
		LOG.debug(text);
		String fch = Ansj.getInstance().raragraphProcess(text);
		LOG.debug(fch);
		String tba="";
		tba=RegexUtils.regex("(?:招标代理机构名称|招标代理单位|采购代理机构名称|采购机构全称|采购代理机构全称|代理人|发布人|采购代理机构|采购代理机构|招标代理机构全称|招标代理机构|招标代理名称|政府采购管理部门|招标代理人名称|采购组织机构名称|招标代理|代理机构|采购代理单位|集中采购机构):([\u4e00-\u9fa5\\（\\）]+)", text, 1);
		if (StringUtils.contains(tba, "我中心")||StringUtils.contains(tba, "月接")||StringUtils.contains(tba, "成交供应商")
				||StringUtils.contains(tba, "名称")||StringUtils.contains(tba, "联系方式")||StringUtils.contains(tba, "书面形式")
				||StringUtils.contains(tba, "签字")||StringUtils.contains(tba, "公章")||StringUtils.contains(tba, "联系")
				||StringUtils.contains(tba, sd))
			tba="";	
		if(StringUtils.isBlank(tba)){
			LOG.debug("12");
			tba=RegexUtils.regex("([\u4e00-\u9fa5\\（\\）]+)受[\u4e00-\u9fa5\\（\\）]+委托", text, 1); 
			if(StringUtils.isBlank(tba)){
				tba=RegexUtils.regex("委托([\u4e00-\u9fa5\\（\\）]+(?:采购中心|招标股份有限公司|采购服务有限公司|招标服务有限公司|采购股份有限公司|招标有限公司|采购有限公司|管理有限公司))", text, 1); 
			}
			if(StringUtils.isBlank(tba)){
				tba=RegexUtils.regex("至([\u4e00-\u9fa5\\（\\）]+(?:采购中心|招标股份有限公司|采购服务有限公司|招标服务有限公司|采购股份有限公司|管理有限公司))", text, 1); 
			}
			if(StringUtils.isBlank(tba)){
				tba=RegexUtils.regex("和([\u4e00-\u9fa5\\（\\）]+(?:采购中心|招标股份有限公司|采购服务有限公司|招标服务有限公司|采购股份有限公司|管理有限公司))", text, 1); 
			}
			if(StringUtils.isBlank(tba)){
				tba=RegexUtils.regex("以([\u4e00-\u9fa5\\（\\）]+(?:采购中心|招标股份有限公司|采购服务有限公司|招标服务有限公司|采购股份有限公司|管理有限公司))", text, 1); 
			}
			if (StringUtils.contains(tba, "下"))
				tba="";
			if(StringUtils.isBlank(tba)){
				tba=RegexUtils.regex("或([\u4e00-\u9fa5\\（\\）]+(?:采购中心|招标股份有限公司|采购服务有限公司|招标服务有限公司|采购股份有限公司|管理有限公司))", text, 1); 
			}
			if(StringUtils.isBlank(tba)){
				tba=RegexUtils.regex("([\u4e00-\u9fa5\\（\\）]+(?:采购中心|招标股份有限公司|采购服务有限公司|招标服务有限公司|采购股份有限公司|管理有限公司))", text, 1); 
			}
			if (StringUtils.contains(tba, "我中心")||StringUtils.contains(tba, "月接")||StringUtils.contains(tba, "成交供应商")
					||StringUtils.contains(tba, "名称")||StringUtils.contains(tba, "联系方式")||StringUtils.contains(tba, "书面形式")
					||StringUtils.contains(tba, "签字")||StringUtils.contains(tba, "公章")||StringUtils.contains(tba, "联系")
					||StringUtils.contains(tba, sd)||StringUtils.contains(tba, "王"))
				tba="";			
			LOG.debug("11");
		}
		try{
		tba=tba.replaceAll("[地址]$", "");
		}catch(Exception e){}
		try{
			tba=tba.replaceAll("[地]$", "");
			}catch(Exception e){}
		try{
			tba=tba.replaceAll("[网]$", "");
			}catch(Exception e){}
		try{
			tba=tba.replaceAll("[\\（]$", "");
			}catch(Exception e){}
		LOG.debug(tba);
		if(StringUtils.isBlank(tba)){
		String[] string;
		string = fch.split(" ");
		int startmark=-1;
		int endmark=-1;
		try{
		for(int i=string.length-1;i>10;i--){
			if(StringUtils.contains(string[i],"采购")||StringUtils.contains(string[i],"招标")||StringUtils.contains(string[i],"咨询")
					||StringUtils.contains(string[i],"代理")||StringUtils.contains(string[i],"资源")||StringUtils.contains(string[i],"服务")||StringUtils.contains(string[i],"招投标"))
			{
				LOG.debug("1");
				for(int j=i+1;j<i+6&&j<string.length;j++)
				{
					if(StringUtils.contains(string[j],"心")||StringUtils.contains(string[j],"公司")
					||(StringUtils.contains(string[j],"办")&&!StringUtils.contains(string[j],"人")&&!StringUtils.contains(string[j],"公")&&!StringUtils.contains(string[j],"法")))
					{
						endmark=j;
						break;
					}		
				}
				for(int j=i-1>=0?i-1:0;j>i-6&&j>=0;j--)
				{
					if(endmark<0||StringUtils.contains(string[j],"保洁"))
					{
						LOG.debug("4");
						break;
					}
					if(StringUtils.contains(string[j],"戴尔")||StringUtils.contains(string[j],"省")||StringUtils.contains(string[j],"市")
							||StringUtils.contains(string[j],"ns")||StringUtils.contains(string[j],"nz")||StringUtils.contains(string[j],"中央")
							||StringUtils.contains(string[j],"中国")||StringUtils.contains(string[j],"国家"))
					{
						startmark=j;
					}
				}
			}
			if(endmark>0&&startmark>0)
				break;
			endmark=-1;
			startmark=-1;
		}}catch(Exception e){}
		if(endmark>0&&startmark>0)
		{
			LOG.debug("6");
			StringBuilder sb = new StringBuilder(25600);
			for(int i=startmark;i<=endmark&&i<string.length;i++)
			{
				LOG.debug("7");
				String[] temporary=string[i].split("/");
				try{
				sb.append(temporary[0]);
				LOG.debug(temporary[0]);
				}catch(Exception e){}
			}
			String sa=sb.toString();
			try{
			sa=sa.replaceAll("^[;:.-(),]", "");}catch(Exception e){}	
			try{
				LOG.debug(sa);		
			if(sa!="月接"&&sa!="单位名称"&&StringUtils.isNotBlank(sa)&&!StringUtils.contains(sa, "供应商")
					&&!StringUtils.contains(sa, "应当")&&!StringUtils.contains(sa, "公告")
					&&!StringUtils.contains(sa, "我")&&!StringUtils.contains(sa, "向")
					&&!StringUtils.contains(sa, "n")&&!StringUtils.contains(sa, sd))
			{
			res.put("tb_agency", sa);
			return res;
			}}catch(Exception e){}	
		}}
		LOG.debug(tba);
		if(StringUtils.isBlank(tba)){
			tba = RegexUtils.regex("(?:招标人|采购人|招标单位名称|采购单位名称|采购人名称|采购单位|采购机构名称|委托单位|申请单位):([0-9\u4e00-\u9fa5]+)", text, 1);
			tba=RegexUtils.regex("至([\u4e00-\u9fa5\\（\\）]+政府)采购", text, 1); 
			if(StringUtils.isNotBlank(tba))
				err="第三方抽出为***政府需验证";
		}
		if(StringUtils.isBlank(tba)){
			tba=RegexUtils.regex("以([\u4e00-\u9fa5\\（\\）]+政府)采购", text, 1); 
			if(StringUtils.isNotBlank(tba))
				err="第三方抽出为***政府需验证";
		}
		if(StringUtils.isBlank(tba)){
			tba=RegexUtils.regex("对([\u4e00-\u9fa5\\（\\）]+政府)采购", text, 1); 
			if(StringUtils.isNotBlank(tba))
				err="第三方抽出为***政府需验证";
		}
		if(StringUtils.isBlank(tba)){
			tba=RegexUtils.regex("([\u4e00-\u9fa5\\（\\）]+政府)采购", text, 1); 
			if(StringUtils.isNotBlank(tba))
				err="第三方抽出为***政府需验证";
		}
//		tba.replaceAll("^[;:.-(),]", "");
		if(StringUtils.isNotBlank(tba)&&!StringUtils.contains(tba, "单位名称")&&!StringUtils.contains(tba, "供应商")
				&&!StringUtils.contains(tba, "应当")&&!StringUtils.contains(tba, "我")&&!StringUtils.contains(tba, "向")
				&&!StringUtils.contains(tba, "n")&&!StringUtils.contains(tba, "关于")&&!StringUtils.contains(tba, "潜在")
				&&!StringUtils.contains(tba, sd)&&!StringUtils.contains(tba, "财政厅"))
		{	
			ErrUtils.checkAdd(obj,true,err,0.0001);
			res.put("tb_agency", tba);
		}
		return res;
	}
}
