package org.yansou.ci.storage.dao.system;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.yansou.ci.core.model.system.Account;
import org.yansou.ci.storage.common.dao.GeneralDao;

/**
 * @author liutiejun
 * @create 2017-05-02 19:42
 */
@Repository("accountDao")
public interface AccountDao extends GeneralDao<Account, Long> {

	Account findByUsername(String username);

	@Query("select ac from Account ac where ac.username = :username")
	Account findAccountByUsername(String username);

	@Modifying
	@Query("update Account u set u.status = ?1 where u.id = ?2")
	int updateStatus(Integer status, Long id);

}
