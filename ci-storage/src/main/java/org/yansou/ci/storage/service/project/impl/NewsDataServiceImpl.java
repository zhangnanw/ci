package org.yansou.ci.storage.service.project.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.model.project.NewsData;
import org.yansou.ci.storage.common.dao.GeneralDao;
import org.yansou.ci.storage.common.service.GeneralServiceImpl;
import org.yansou.ci.storage.dao.project.NewsDataDao;
import org.yansou.ci.storage.service.project.NewsDataService;

/**
 * @author liutiejun
 * @create 2017-05-14 0:23
 */
@Service("newsDataService")
@Transactional
public class NewsDataServiceImpl extends GeneralServiceImpl<NewsData, Long> implements NewsDataService {

	private NewsDataDao newsDataDao;

	@Autowired
	@Qualifier("newsDataDao")
	@Override
	public void setGeneralDao(GeneralDao<NewsData, Long> generalDao) {
		this.generalDao = generalDao;
		this.newsDataDao = (NewsDataDao) generalDao;
	}

	@Override
	public int updateStatus(Integer status, Long id) throws DaoException {
		return newsDataDao.updateStatus(status, id);
	}

}
