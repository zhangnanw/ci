package org.yansou.ci.web.business;

import org.yansou.ci.common.datatables.mapping.DataTablesOutput;
import org.yansou.ci.core.rest.response.CountResponse;
import org.yansou.ci.core.rest.response.IdResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @author liutiejun
 * @create 2017-05-13 22:54
 */
public interface GeneralBusiness<T extends Serializable, ID extends Serializable> {

	public static final String CI_STORAGE = "ci-storage";

	/**
	 * 根据ID查询
	 *
	 * @param id
	 *
	 * @return
	 */
	T findById(ID id);

	/**
	 * 查询所有的数据
	 *
	 * @return
	 */
	T[] findAll();

	/**
	 * 分页查询
	 *
	 * @param request
	 *
	 * @return
	 */
	DataTablesOutput<T> pagination(HttpServletRequest request);

	/**
	 * 新增
	 *
	 * @param entity
	 *
	 * @return
	 */
	IdResponse save(T entity);

	/**
	 * 更新
	 *
	 * @param entity
	 *
	 * @return
	 */
	IdResponse update(T entity);

	/**
	 * 批量更新
	 *
	 * @param entities
	 *
	 * @return
	 */
	CountResponse update(T[] entities);

	/**
	 * 删除
	 *
	 * @param ids
	 *
	 * @return
	 */
	CountResponse deleteById(ID[] ids);

}
