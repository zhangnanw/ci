package org.yansou.ci.crawler.entity;

@DBTable("t_organization")
public class Organization {

	@DBField(name = "id", type = "int unsigned not null auto_increment primary key")
	public int id;
	/**
	 * 机构名
	 */
	@DBField(name="organization",type="varchar(300)")
	public String organization;
	
	@DBField(name="organization_url",type="varchar(500)")
	public String organizationUrl;
	/**
	 * 洲
	 */
	@DBField(name="continent",type="varchar(300)")
	public String continent;
	/**
	 * 出版数量
	 */
	@DBField(name="publication_number",type="varchar(300)")
	public String publicationNumber;
	/**
	 * 引用数量
	 */
	@DBField(name="citation_count",type="varchar(300)")
	public String citationCount;
	/**
	 * 研究领域
	 */
	@DBField(name="fields_study",type="varchar(500)")
	public String fieldsStudy;
	/**
	 * 子领域
	 */
	@DBField(name="sub_organiaztion",type="varchar(500)")
	public String subOrganiaztion;
	/**
	 * 主页
	 */
	@DBField(name="homepage",type="varchar(500)")
	public String homepage;
	/**
	 * 分类
	 */
	@DBField(name="category",type="varchar(500)")
	public String category;
	
	@DBField(name = "insert_time", type = "timestamp default NOW()")
	public String insertTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	public String getPublicationNumber() {
		return publicationNumber;
	}

	public void setPublicationNumber(String publicationNumber) {
		this.publicationNumber = publicationNumber;
	}

	public String getCitationCount() {
		return citationCount;
	}

	public void setCitationCount(String citationCount) {
		this.citationCount = citationCount;
	}

	public String getFieldsStudy() {
		return fieldsStudy;
	}

	public void setFieldsStudy(String fieldsStudy) {
		this.fieldsStudy = fieldsStudy;
	}

	public String getSubOrganiaztion() {
		return subOrganiaztion;
	}

	public void setSubOrganiaztion(String subOrganiaztion) {
		this.subOrganiaztion = subOrganiaztion;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public String getOrganizationUrl() {
		return organizationUrl;
	}

	public void setOrganizationUrl(String organizationUrl) {
		this.organizationUrl = organizationUrl;
	}
	
	
	
}