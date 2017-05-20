package org.yansou.ci.storage.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yansou.ci.common.page.PageCriteria;
import org.yansou.ci.core.model.system.Account;
import org.yansou.ci.storage.common.dao.GeneralDao;
import org.yansou.ci.storage.common.service.GeneralServiceImpl;
import org.yansou.ci.storage.dao.system.AccountDao;
import org.yansou.ci.storage.service.system.AccountService;

/**
 * @author liutiejun
 * @create 2017-05-10 23:33
 */
@Service("accountService")
@Transactional
public class AccountServiceImpl extends GeneralServiceImpl<Account, Long> implements AccountService {

	private AccountDao accountDao;

	@Autowired
	@Qualifier("accountDao")
	@Override
	public void setGeneralDao(GeneralDao<Account, Long> generalDao) {
		this.generalDao = generalDao;
		this.accountDao = (AccountDao) generalDao;
	}

	@Override
	public Specification<Account> createSpecification(PageCriteria pageCriteria) {
		return null;
	}

	@Override
	public int updateStatus(Integer status, Long id) {
		return accountDao.updateStatus(status, id);
	}

}
