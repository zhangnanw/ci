package org.yansou.ci.storage.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yansou.ci.core.model.system.Account;
import org.yansou.ci.storage.common.repository.GeneralRepository;
import org.yansou.ci.storage.common.service.GeneralServiceImpl;
import org.yansou.ci.storage.repository.system.AccountRepository;
import org.yansou.ci.storage.service.system.AccountService;

/**
 * @author liutiejun
 * @create 2017-05-10 23:33
 */
@Service("accountService")
@Transactional
public class AccountServiceImpl extends GeneralServiceImpl<Account, Long> implements AccountService {

	private AccountRepository accountRepository;

	@Autowired
	@Qualifier("accountRepository")
	@Override
	public void setGeneralRepository(GeneralRepository<Account, Long> generalRepository) {
		this.generalRepository = generalRepository;
		this.accountRepository = (AccountRepository) generalRepository;
	}

	@Override
	public int updateStatus(Integer status, Long id) {
		return accountRepository.updateStatus(status, id);
	}

}
