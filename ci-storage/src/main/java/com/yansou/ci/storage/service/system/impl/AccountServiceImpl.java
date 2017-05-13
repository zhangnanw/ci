package com.yansou.ci.storage.service.system.impl;

import com.yansou.ci.common.page.PageCriteria;
import com.yansou.ci.core.model.system.Account;
import com.yansou.ci.storage.common.dao.GeneralDao;
import com.yansou.ci.storage.common.service.GeneralServiceImpl;
import com.yansou.ci.storage.dao.system.AccountDao;
import com.yansou.ci.storage.service.system.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
