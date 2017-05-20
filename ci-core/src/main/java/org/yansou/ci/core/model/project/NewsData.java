package org.yansou.ci.core.model.project;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;
import org.yansou.ci.core.model.AbstractModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 新闻数据
 *
 * @author liutiejun
 * @create 2017-05-14 12:16
 */
@Entity
@Table(name = "ci_news_data")
public class NewsData extends AbstractModel<Long> {

	private static final long serialVersionUID = 6142909747114018985L;

	@Column
	private String title;// 标题

	@Column
	private String content;// 内容

	@Column
	private String author;// 作者

	@Column
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date publishTime;// 发布时间

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
}
