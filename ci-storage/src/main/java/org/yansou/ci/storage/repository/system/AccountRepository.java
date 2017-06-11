package org.yansou.ci.storage.repository.system;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.yansou.ci.core.db.model.system.Account;
import org.yansou.ci.storage.common.repository.GeneralRepository;

/**
 * @author liutiejun
 * @create 2017-05-02 19:42
 */
@Repository("accountRepository")
public interface AccountRepository extends GeneralRepository<Account, Long> {

	Account findByUsername(String username);

	@Query("select ac from Account ac where ac.username = :username")
	Account findAccountByUsername(String username);

}
