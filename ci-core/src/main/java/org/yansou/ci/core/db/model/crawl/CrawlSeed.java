package org.yansou.ci.core.db.model.crawl;

import org.yansou.ci.core.db.model.AbstractModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 爬虫种子。
 * @author Administrator
 *
 */
@Entity
@Table(name = "ci_crawl_seed")
public class CrawlSeed extends AbstractModel<Long> {
	private static final long serialVersionUID = 5322731563926975054L;
	@Column
	private String tplClass;
	@Column
	private String sendUrl;
	@Column
	private String tplPath;
	@Column
	private String headlers;
	public String getTplClass() {
		return tplClass;
	}

	public void setTplClass(String tplClass) {
		this.tplClass = tplClass;
	}

	public String getSendUrl() {
		return sendUrl;
	}

	public void setSendUrl(String sendUrl) {
		this.sendUrl = sendUrl;
	}

	public String getTplPath() {
		return tplPath;
	}

	public void setTplPath(String tplPath) {
		this.tplPath = tplPath;
	}

	public String getHeadlers() {
		return headlers;
	}

	public void setHeadlers(String headlers) {
		this.headlers = headlers;
	}
}
