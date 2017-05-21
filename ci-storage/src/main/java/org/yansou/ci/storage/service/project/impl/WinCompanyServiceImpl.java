package org.yansou.ci.storage.service.project.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.common.page.PageCriteria;
import org.yansou.ci.core.model.project.WinCompany;
import org.yansou.ci.storage.common.dao.GeneralDao;
import org.yansou.ci.storage.common.service.GeneralServiceImpl;
import org.yansou.ci.storage.dao.project.WinCompanyDao;
import org.yansou.ci.storage.service.project.WinCompanyService;

/**
 * @author liutiejun
 * @create 2017-05-14 0:23
 */
@Service("winCompanyService")
@Transactional
public class WinCompanyServiceImpl extends GeneralServiceImpl<WinCompany, Long> implements WinCompanyService {

	private WinCompanyDao winCompanyDao;

	@Autowired
	@Qualifier("winCompanyDao")
	@Override
	public void setGeneralDao(GeneralDao<WinCompany, Long> generalDao) {
		this.generalDao = generalDao;
		this.winCompanyDao = (WinCompanyDao) generalDao;
	}

	@Override
	public Specification<WinCompany> createSpecification(PageCriteria pageCriteria) {
		return null;
	}

	@Override
	public int updateStatus(Integer status, Long id) throws DaoException {
		return winCompanyDao.updateStatus(status, id);
	}

}
