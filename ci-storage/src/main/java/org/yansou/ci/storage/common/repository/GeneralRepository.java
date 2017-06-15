package org.yansou.ci.storage.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author liutiejun
 * @create 2017-05-13 10:58
 */
@NoRepositoryBean
public interface GeneralRepository<T, ID extends Serializable> extends JpaRepository<T, ID>,
		JpaSpecificationExecutor<T> {

	/**
	 * 更新状态
	 *
	 * @param status
	 * @param id
	 *
	 * @return
	 */
	int updateStatus(Integer status, ID id);

	/**
	 * 更新非空字段
	 *
	 * @param entity
	 */
	void updateNotNullField(T entity);

	/**
	 * 条件查询，主要用于自定义查询
	 *
	 * @param hql
	 *
	 * @return
	 */
	List<Map<String, Object>> findByHql(String hql);

	/**
	 * 条件查询，主要用于自定义查询
	 *
	 * @param hql
	 * @param valuesMap
	 *
	 * @return
	 */
	List<Map<String, Object>> findByHql(String hql, Map<String, Object> valuesMap);

	/**
	 * 分页查询，主要用于自定义查询
	 *
	 * @param hql
	 * @param firstResult
	 * @param maxResults
	 *
	 * @return
	 */
	List<Map<String, Object>> findByHql(String hql, int firstResult, int maxResults);

	/**
	 * 分页查询，主要用于自定义查询
	 *
	 * @param hql
	 * @param firstResult
	 * @param maxResults
	 * @param valuesMap
	 *
	 * @return
	 */
	List<Map<String, Object>> findByHql(String hql, int firstResult, int maxResults, Map<String, Object> valuesMap);

}
