package org.yansou.ci.storage.repository.system;

import org.springframework.stereotype.Repository;
import org.yansou.ci.core.db.model.system.Account;
import org.yansou.ci.storage.common.repository.GeneralRepository;

/**
 * @author liutiejun
 * @create 2017-05-02 19:42
 */
@Repository("accountRepository")
public interface AccountRepository extends GeneralRepository<Account, Long> {

	Account findAccountByUsername(String username);

}
