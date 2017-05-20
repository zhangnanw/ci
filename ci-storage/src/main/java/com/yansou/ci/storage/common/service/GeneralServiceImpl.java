package com.yansou.ci.storage.common.service;

import com.yansou.ci.common.exception.DaoException;
import com.yansou.ci.common.page.PageCriteria;
import com.yansou.ci.common.page.Pagination;
import com.yansou.ci.common.utils.SimpleArrayUtils;
import com.yansou.ci.storage.common.dao.GeneralDao;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;

import javax.persistence.Id;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author liutiejun
 * @create 2017-05-10 18:19
 */
@Transactional
public abstract class GeneralServiceImpl<T extends Serializable, ID extends Serializable> implements
		GeneralService<T, ID> {

	private String entityName;
	private Class<T> entityClass;
	private String pkName;

	protected GeneralDao<T, ID> generalDao;

	@SuppressWarnings("unchecked")
	public GeneralServiceImpl() {
		this.entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];

		this.entityName = this.entityClass.getSimpleName();

		Field[] fields = this.entityClass.getDeclaredFields();
		for (Field f : fields) {
			if (f.isAnnotationPresent(Id.class)) {
				this.pkName = f.getName();
				break;
			}
		}
	}

	public abstract void setGeneralDao(GeneralDao<T, ID> generalDao);

	/**
	 * 封装分页查询条件
	 *
	 * @param pageCriteria
	 *
	 * @return
	 */
	public abstract Specification<T> createSpecification(PageCriteria pageCriteria);

	@Override
	public T save(T entity) throws DaoException {
		Assert.notNull(entity, "Entity is null");

		return generalDao.save(entity);
	}

	@Override
	public List<T> save(List<T> entities) throws DaoException {
		if (CollectionUtils.isEmpty(entities)) {
			throw new IllegalArgumentException("Entities is empty");
		}

		return generalDao.save(entities);
	}

	@Override
	public T[] save(T[] entities) throws DaoException {
		if (ArrayUtils.isEmpty(entities)) {
			throw new IllegalArgumentException("Entities is empty");
		}

		List<T> entityList = new ArrayList<>();

		Arrays.stream(entities).forEach(entityList::add);

		entityList = generalDao.save(entityList);

		T[] entityArray = SimpleArrayUtils.newArrayByClass(entityClass, entityList.size());
		entityArray = entityList.toArray(entityArray);

		return entityArray;
	}

	@Override
	public void deleteById(ID id) throws DaoException {
		generalDao.delete(id);
	}

	@Override
	public void delete(T entity) throws DaoException {
		generalDao.delete(entity);
	}

	@Override
	public void delete(List<T> entities) throws DaoException {
		generalDao.deleteInBatch(entities);
	}

	@Override
	public T update(T entity) throws DaoException {
		return save(entity);
	}

	@Override
	public List<T> update(List<T> entities) throws DaoException {
		return save(entities);
	}

	@Override
	public T[] update(T[] entities) throws DaoException {
		return save(entities);
	}

	@Override
	public T findById(ID id) throws DaoException {
		return generalDao.findOne(id);
	}

	@Override
	public List<T> findAll() throws DaoException {
		return generalDao.findAll();
	}

	@Override
	public Pagination<T> pagination(Integer currentPageNo, Integer pageSize) throws DaoException {
		return pagination(currentPageNo, pageSize, null);
	}

	@Override
	public Pagination<T> pagination(Integer currentPageNo, Integer pageSize, Specification<T> specification) throws
			DaoException {
		if (currentPageNo == null || currentPageNo <= 0) {
			currentPageNo = 1;
		}

		if (pageSize == null || pageSize <= 0) {
			pageSize = 10;
		}

		Pageable pageable = new PageRequest(currentPageNo - 1, pageSize);

		Page<T> page = null;

		if (specification == null) {
			page = generalDao.findAll(pageable);
		} else {
			page = generalDao.findAll(specification, pageable);
		}

		long totalCount = page.getTotalElements();

		List<T> dataList = page.getContent();
		T[] dataArray = SimpleArrayUtils.newArrayByClass(entityClass, dataList.size());
		dataArray = dataList.toArray(dataArray);

		return new Pagination<T>(totalCount, pageSize, currentPageNo, dataArray);
	}

	@Override
	public Pagination<T> pagination(PageCriteria pageCriteria) throws DaoException {
		Integer currentPageNo = pageCriteria.getCurrentPageNo();
		Integer pageSize = pageCriteria.getPageSize();

		Specification<T> specification = createSpecification(pageCriteria);

		return pagination(currentPageNo, pageSize, specification);
	}

	@Override
	public long countAll() throws DaoException {
		return generalDao.count();
	}

}
