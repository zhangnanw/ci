package org.yansou.ci.crawler.core;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.yansou.ci.crawler.entity.DBField;

public class Seed<T> implements Delayed, Cloneable {
	@DBField(name = "id", type = "int unsigned not null auto_increment primary key")
	public int id;
	/**
	 * 种子url
	 */

	@DBField(name = "seed_url", type = "varchar(255) COMMENT '期刊主下载地址种子url'")
	public String seedUrl;

	// /**
	// * issn
	// */
	// @DBField(name = "issn", type = "varchar(100)")
	// public String issn;
	// /**
	// * 期刊名称
	// */
	// @DBField(name = "article_name", type = "varchar(300) COMMENT '期刊名称'")
	// public String articleName;
	// /**
	// * 出版社，没有出版社的用other
	// */
	// @DBField(name = "publisher", type = "varchar(500) COMMENT '出版社'")
	// public String publisher;
	/**
	 * 默认值为0表示没抓取过，之后改为1表示抓去过，可以进行增量抓取
	 */
	@DBField(name = "is_fetch", type = "int default 0 COMMENT '默认值为0表示没抓取过，之后改为1表示抓取过，可以进行增量抓取'")
	public int isFetch;
	// /**
	// * 期刊或者会议 1表示期刊种子，2表示会议种子
	// */
	// @DBField(name = "type", type =
	// "int default 1 COMMENT '期刊或者会议 1表示期刊种子，2表示会议种子,目前为期刊居多'")
	// public int type;
	/**
	 * 模板路劲，模板文件的上级目录最好为publisher，方便管理模板 暂时没用起来
	 */
	@DBField(name = "tpl_path", type = "varchar(500) COMMENT '模板路劲，模板文件的上级目录最好为publisher，方便管理模板 暂时没用起来'")
	public String tplPath;

	@DBField(name = "output_type", type = "varchar(100) COMMENT '值为后面的3个之1：com.pyc.paper.entity.PaperPopular、com.pyc.paper.entity.PaperRecent、com.pyc.paper.entity.PaperMost、com.pyc.paper.entity.Paper'")
	public String outputType;

	@DBField(name = "next_crawl_time", type = "timestamp default NOW() COMMENT '下次取走的时间'")
	public Date nextCrawlTime;
	@DBField(name = "remark", type = "varchar(100) COMMENT '备注'")
	public String remark;
	public String countInfo;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSeedUrl() {
		return seedUrl;
	}

	public void setSeedUrl(String seedUrl) {
		this.seedUrl = seedUrl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// public String getPublisher() {
	// return publisher;
	// }
	//
	// public void setPublisher(String publisher) {
	// this.publisher = publisher;
	// }
	//
	public int getIsFetch() {
		return isFetch;
	}

	public void setIsFetch(int isFetch) {
		this.isFetch = isFetch;
	}

	//
	// public int getType() {
	// return type;
	// }
	//
	// public void setType(int type) {
	// this.type = type;
	// }

	public String getTplPath() {
		return tplPath;
	}

	public void setTplPath(String tplPath) {
		this.tplPath = tplPath;
	}

	// public String getIssn() {
	// return issn;
	// }
	//
	// public void setIssn(String issn) {
	// this.issn = issn;
	// }
	//
	// public String getArticleName() {
	// return articleName;
	// }
	//
	// public void setArticleName(String articleName) {
	// this.articleName = articleName;
	// }

	public String getOutputType() {
		return outputType;
	}

	public void setOutputType(String outputType) {
		this.outputType = outputType;
	}

	public Date getNextCrawlTime() {
		return nextCrawlTime;
	}

	public void setNextCrawlTime(Date nextCrawlTime) {
		this.nextCrawlTime = nextCrawlTime;
	}

	@Override
	public int compareTo(Delayed o) {
		return 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Seed<T> clone() {
		try {
			return (Seed<T>) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new IllegalStateException(e);
		}
	}
}
