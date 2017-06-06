package org.yansou.ci.storage.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author liutiejun
 * @create 2017-05-13 10:58
 */
@NoRepositoryBean
public interface GeneralRepository<T, ID extends Serializable> extends JpaRepository<T, ID>,
		JpaSpecificationExecutor<T> {

	int updateStatus(Integer status, ID id);

	/**
	 * 更新非空字段
	 *
	 * @param entity
	 */
	void updateNotNullField(T entity);

}
