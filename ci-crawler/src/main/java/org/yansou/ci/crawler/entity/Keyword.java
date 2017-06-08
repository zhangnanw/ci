package org.yansou.ci.crawler.entity;
/**
 * 
 * 描述信息：搜索引擎关键词表
 * @author: liuqingchao - liuqingchao@pyc.com.cn
 * @data: 2015年4月8日
 */
@DBTable("t_keyword")
public class Keyword {
	
	@DBField(name = "id", type = "int unsigned not null auto_increment primary key")
	public int id;
	/**
	 * 关键词
	 */
	@DBField(name = "keyword", type = "varchar(100) unique")
	public String keyword;
	/**
	 * 权重值
	 */
	@DBField(name = "weight", type = "int(4) DEFAULT 5")
	public int weight;
	/**
	 * 是否爬取过得标记默认为0，爬取过置为1或者大于0的整数
	 */
	@DBField(name = "flag", type = "int(4) DEFAULT 0")
	public int flag;
	
	@DBField(name = "insert_time", type = "timestamp default NOW()")
	public String insertTime;
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	@Override
	public String toString() {
		return "Keyword [keyword=" + keyword + ", weight=" + weight + ", flag="
				+ flag + "]";
	}
	
	
	
}
