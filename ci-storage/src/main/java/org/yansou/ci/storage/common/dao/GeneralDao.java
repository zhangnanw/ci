package org.yansou.ci.storage.common.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author liutiejun
 * @create 2017-05-13 10:58
 */
@NoRepositoryBean
public interface GeneralDao<T extends Serializable, ID extends Serializable> extends JpaRepository<T, ID>,
		JpaSpecificationExecutor<T> {

}
