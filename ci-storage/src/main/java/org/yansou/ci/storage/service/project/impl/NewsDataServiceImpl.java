package org.yansou.ci.storage.service.project.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yansou.ci.core.db.model.project.NewsData;
import org.yansou.ci.storage.common.repository.GeneralRepository;
import org.yansou.ci.storage.common.service.GeneralServiceImpl;
import org.yansou.ci.storage.repository.project.NewsDataRepository;
import org.yansou.ci.storage.service.project.NewsDataService;

/**
 * @author liutiejun
 * @create 2017-05-14 0:23
 */
@Service("newsDataService")
@Transactional
public class NewsDataServiceImpl extends GeneralServiceImpl<NewsData, Long> implements NewsDataService {

	private NewsDataRepository newsDataRepository;

	@Autowired
	@Qualifier("newsDataRepository")
	@Override
	public void setGeneralRepository(GeneralRepository<NewsData, Long> generalRepository) {
		this.generalRepository = generalRepository;
		this.newsDataRepository = (NewsDataRepository) generalRepository;
	}

}
