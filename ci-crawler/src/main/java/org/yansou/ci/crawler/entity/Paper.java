package org.yansou.ci.crawler.entity;

import java.util.Arrays;

@DBTable("tab_paper_update")
public class Paper {
	@DBField(name = "id", type = "int unsigned not null auto_increment primary key")
	public int id;
	/**
	 * 
	 * 描述信息：title
	 * 
	 * @data: 2015年4月2日
	 */
	@DBField(name = "title", type = "varchar(800) COMMENT 'title名称'")
	public String title;
	/**
	 * 
	 * 描述信息：作者
	 * 
	 * @data: 2015年4月2日 注意和作者单位地址的关系 存储格式： xx作者-1,2;yy作者-2...
	 *        意思就是xx作者对应单位中的1和2两个地址，yy作者对应单位中的2，一个作者可以有多个单位 分号符统一用卍字符
	 */
	@DBField(name = "authors", type = "text COMMENT '作者'")
	public String authors;
	/**
	 * 
	 * 描述信息：作者地址或工作单位 注意和作者的关系 存储格式： 1-xx单位;2-yy单位...
	 * 
	 * @data: 2015年4月2日
	 */

	@DBField(name = "work_unit", type = "text COMMENT '作者的工作单位'")
	public String workUnit;
	/**
	 * 
	 * 描述信息：论文关键词
	 * 
	 * @data: 2015年4月2日
	 */
	@DBField(name = "keywords", type = "text COMMENT '论文关键词'")
	public String keywords;
	/*
	 * 论文的摘要
	 */
	@DBField(name = "abstracts", type = "text COMMENT '摘要'")
	public String abstracts;
	/**
	 * 
	 * 描述信息：论文的doi信息
	 * 
	 * @data: 2015年4月2日
	 */
	@DBField(name = "doi", type = "varchar(500)")
	public String doi;
	/**
	 * 
	 * 描述信息：被引用率
	 * 
	 * @data: 2015年4月2日
	 */
	@DBField(name = "cited", type = "varchar(300) COMMENT '被引用率'")
	public String cited;
	/**
	 * 自引率
	 */
	@DBField(name = "self_cited", type = "varchar(300) COMMENT '自引率'")
	public String selfCited;
	/**
	 * 论文的下载地址
	 */
	@DBField(name = "download_url", type = "varchar(1000) COMMENT 'pdf下载链接多个之间以卐字符分割'")
	public String downloadUrl;
	/**
	 * 当出现有论文下载地址，但不在在当前网站，需要跳转到其他网站下载时， 把跳转链接存在这里（至少这个链接上存在pdf下载地址）
	 */
	@DBField(name = "download_url_bak", type = "varchar(1000) COMMENT 'pdf下载备用链接多个之间以卐字符分割'")
	public String downloadUrlBak;
	/**
	 * 出版日期（发布日期）格式暂不处理 类似year
	 */
	@DBField(name = "publish_time", type = "varchar(500) COMMENT '出版日期（发布日期）格式暂不处理'")
	public String publishTime;
	/**
	 * 参考文献
	 */
	@DBField(name = "reference", type = "longtext COMMENT '参考文献'")
	public String references;
	/**
	 * 引用文献 和reference正好相反，表示其他引用该论文的论文
	 */
	@DBField(name = "cited_doc", type = "longtext COMMENT '引用文献'")
	public String citedDoc;
	/**
	 * 期刊名称或者会议名称 类似关键词journal、venue Conference
	 */
	@DBField(name = "article_name", type = "varchar(300) COMMENT '期刊或会议名称'")
	public String articleName;
	/**
	 * 论文磁盘地址，使用相对地址存储
	 */
	@DBField(name = "local_address", type = "varchar(300) COMMENT 'pdf磁盘存储地址'")
	public String localAddress;
	/**
	 * 论文来源 期刊（article）或会议(conference)或者某搜索引擎 值 [article、conference、某搜索引擎]
	 * 网站域名为值也行哦
	 */
	@DBField(name = "source", type = "varchar(300) COMMENT '论文来源[journal、conference、某搜索引擎名称]'")
	public String source;

	/**
	 * 
	 * 描述信息：入口url
	 * 
	 * @data: 2015年4月2日
	 */
	@DBField(name = "enter_url", type = "varchar(500) COMMENT '种子url'")
	public String enterUrl;
	/**
	 * 抓取页url
	 */
	@DBField(name = "fetch_url", type = "varchar(500) COMMENT '期刊主下载地址种子url'")
	public String fetchUrl;
	/**
	 * 主题
	 */
	@DBField(name = "subject", type = "varchar(500) COMMENT '主题'")
	public String subject;

	/********************* 以上为公共部分 *************************************/

	/********************* 期刊特有部分*begin ********************************/
	/**
	 * 期刊类 卷 一般和号一起出现
	 */
	@DBField(name = "volume", type = "varchar(300) COMMENT '期刊类 卷 一般和号一起出现'")
	public String volume;
	/**
	 * 期刊类 号 一般和卷一起出现 类似的number等
	 */
	@DBField(name = "no_issue", type = "varchar(300) COMMENT '期刊类 号 一般和卷一起出现 类似的number等'")
	public String noOrIssue;
	/**
	 * 
	 * 描述信息：期刊类 论文在期刊上出现的开始和结束的页码
	 * 
	 * @data: 2015年4月2日
	 */
	@DBField(name = "pages", type = "varchar(300) COMMENT '论文在期刊上的开始和结束的页码'")
	public String pages;
	/**
	 * 期刊类 投稿日期和录用日期出现的文本内容
	 */
	@DBField(name = "contribute_offer", type = "text COMMENT '投稿日期和录用日期的合并字段'")
	public String contributeOffer;
	/********************* 期刊特有项*end ************************************/

	/********************* 会议特有项*begin **********************************/
	/**
	 * 
	 * 描述信息：会议地址
	 * 
	 * @data: 2015年4月2日
	 */
	@DBField(name = "conference_address", type = "varchar(500) COMMENT '会议地址'")
	public String conferenceAddress;
	/********************* 会议特有项*end ************************************/
	/**
	 * 
	 * 描述信息：出版社
	 * 
	 * @data: 2015年4月2日
	 */
	@DBField(name = "publishing", type = "varchar(300) COMMENT '出版社'")
	public String publishing;
	/**
	 * issn编号
	 */
	@DBField(name = "issn", type = "varchar(300) COMMENT 'issn编号'")
	public String issn;

	@DBField(name = "flag", type = "int(4) default 0 COMMENT '论文下载标识:0-默认；1-下载成功；2-下载失败;3-跳转页面'")
	public int flag = 0;

	@DBField(name = "insert_time", type = "timestamp default NOW()")
	public String insertTime;

	@DBField(name = "title_flag", type = "int(4) default 0 COMMENT 'title是否已被搜索:0-默认；1-高校已搜索'")
	public int titleFlag = 0;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getAbstracts() {
		return abstracts;
	}

	public void setAbstracts(String abstracts) {
		this.abstracts = abstracts;
	}

	public String getDoi() {
		return doi;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}

	public String getCited() {
		return cited;
	}

	public void setCited(String cited) {
		this.cited = cited;
	}

	public String getSelfCited() {
		return selfCited;
	}

	public void setSelfCited(String selfCited) {
		this.selfCited = selfCited;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getReferences() {
		return references;
	}

	public void setReferences(String references) {
		this.references = references;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getLocalAddress() {
		return localAddress;
	}

	public void setLocalAddress(String localAddress) {
		this.localAddress = localAddress;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getEnterUrl() {
		return enterUrl;
	}

	public void setEnterUrl(String enterUrl) {
		this.enterUrl = enterUrl;
	}

	public String getFetchUrl() {
		return fetchUrl;
	}

	public void setFetchUrl(String fetchUrl) {
		this.fetchUrl = fetchUrl;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getNoOrIssue() {
		return noOrIssue;
	}

	public void setNoOrIssue(String noOrIssue) {
		this.noOrIssue = noOrIssue;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public String getContributeOffer() {
		return contributeOffer;
	}

	public void setContributeOffer(String contributeOffer) {
		this.contributeOffer = contributeOffer;
	}

	public String getConferenceAddress() {
		return conferenceAddress;
	}

	public void setConferenceAddress(String conferenceAddress) {
		this.conferenceAddress = conferenceAddress;
	}

	public String getPublishing() {
		return publishing;
	}

	public void setPublishing(String publishing) {
		this.publishing = publishing;
	}

	public String getDownloadUrlBak() {
		return downloadUrlBak;
	}

	public void setDownloadUrlBak(String downloadUrlBak) {
		this.downloadUrlBak = downloadUrlBak;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.getClass().getName()).append('\n');
		sb.append('[').append('\n');
		Arrays.asList(this.getClass().getFields()).forEach(field -> {
			sb.append(field.getName());
			sb.append('=');
			try {
				Object value = field.get(this);
				if (null != value)
					sb.append(value);
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			}
			sb.append(',').append('\n');
		});
		sb.append(']').append('\n');
		return sb.toString();
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getCitedDoc() {
		return citedDoc;
	}

	public void setCitedDoc(String citedDoc) {
		this.citedDoc = citedDoc;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public int getTitleFlag() {
		return titleFlag;
	}

	public void setTitleFlag(int titleFlag) {
		this.titleFlag = titleFlag;
	}

}
