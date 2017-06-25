package org.yansou.ci.storage.service.system;

import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.db.model.system.Account;
import org.yansou.ci.storage.common.service.GeneralService;

/**
 * @author liutiejun
 * @create 2017-05-05 14:45
 */
public interface AccountService extends GeneralService<Account, Long> {

	Account findByUsername(String username) throws DaoException;

}
