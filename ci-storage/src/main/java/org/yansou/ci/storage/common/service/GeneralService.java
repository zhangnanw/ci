package org.yansou.ci.storage.common.service;

import org.springframework.data.jpa.domain.Specification;
import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.common.page.PageCriteria;
import org.yansou.ci.common.page.Pagination;
import org.yansou.ci.core.model.AbstractModel;

import java.io.Serializable;
import java.util.List;

/**
 * service层封装
 *
 * @param <T>
 * @param <ID>
 *
 * @author liutiejun
 * @create 2017-05-10 17:35
 */
public interface GeneralService<T extends AbstractModel<ID>, ID extends Serializable> {

	/**
	 * 新增
	 *
	 * @param entity
	 *
	 * @return
	 *
	 * @throws DaoException
	 */
	T save(T entity) throws DaoException;

	/**
	 * 新增
	 *
	 * @param entities
	 *
	 * @throws DaoException
	 */
	List<T> save(List<T> entities) throws DaoException;

	/**
	 * 新增
	 *
	 * @param entities
	 *
	 * @throws DaoException
	 */
	T[] save(T[] entities) throws DaoException;

	/**
	 * 删除
	 *
	 * @param id
	 *
	 * @throws DaoException
	 */
	void deleteById(ID id) throws DaoException;

	/**
	 * 删除
	 *
	 * @param entity
	 *
	 * @throws DaoException
	 */
	void delete(T entity) throws DaoException;

	/**
	 * 删除
	 *
	 * @param entities
	 *
	 * @throws DaoException
	 */
	void delete(List<T> entities) throws DaoException;

	/**
	 * 更新
	 *
	 * @param entity
	 *
	 * @throws DaoException
	 */
	T update(T entity) throws DaoException;

	/**
	 * 更新
	 *
	 * @param entities
	 *
	 * @throws DaoException
	 */
	List<T> update(List<T> entities) throws DaoException;

	/**
	 * 更新
	 *
	 * @param entities
	 *
	 * @throws DaoException
	 */
	T[] update(T[] entities) throws DaoException;

	/**
	 * 更新数据的状态
	 *
	 * @param status
	 * @param id
	 *
	 * @return
	 *
	 * @throws DaoException
	 */
	int updateStatus(Integer status, ID id) throws DaoException;

	/**
	 * 根据ID查询
	 *
	 * @param id
	 *
	 * @return
	 *
	 * @throws DaoException
	 */
	T findById(ID id) throws DaoException;

	/**
	 * 查询所有数据
	 *
	 * @return
	 *
	 * @throws DaoException
	 */
	List<T> findAll() throws DaoException;

	/**
	 * 分页查询
	 *
	 * @param currentPageNo 从1开始
	 * @param pageSize
	 *
	 * @return
	 *
	 * @throws DaoException
	 */
	Pagination<T> pagination(Integer currentPageNo, Integer pageSize) throws DaoException;

	/**
	 * 分页查询
	 *
	 * @param currentPageNo
	 * @param pageSize
	 * @param specification
	 *
	 * @return
	 *
	 * @throws DaoException
	 */
	Pagination<T> pagination(Integer currentPageNo, Integer pageSize, Specification<T> specification) throws
			DaoException;

	/**
	 * 分页查询
	 *
	 * @param pageCriteria
	 *
	 * @return
	 *
	 * @throws DaoException
	 */
	Pagination<T> pagination(PageCriteria pageCriteria) throws DaoException;

	/**
	 * 获取所有数据的数量(为了分页使用的)
	 *
	 * @return
	 *
	 * @throws DaoException
	 */
	long countAll() throws DaoException;

}
