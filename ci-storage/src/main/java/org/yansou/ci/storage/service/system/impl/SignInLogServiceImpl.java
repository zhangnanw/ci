package org.yansou.ci.storage.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yansou.ci.core.db.model.system.SignInLog;
import org.yansou.ci.storage.common.repository.GeneralRepository;
import org.yansou.ci.storage.common.service.GeneralServiceImpl;
import org.yansou.ci.storage.repository.system.SignInLogRepository;
import org.yansou.ci.storage.service.system.SignInLogService;

/**
 * @author liutiejun
 * @create 2017-05-10 23:33
 */
@Service("signInLogService")
@Transactional
public class SignInLogServiceImpl extends GeneralServiceImpl<SignInLog, Long> implements SignInLogService {

	private SignInLogRepository signInLogRepository;

	@Autowired
	@Qualifier("signInLogRepository")
	@Override
	public void setGeneralRepository(GeneralRepository<SignInLog, Long> generalRepository) {
		this.generalRepository = generalRepository;
		this.signInLogRepository = (SignInLogRepository) generalRepository;
	}

}
