package org.yansou.ci.storage.common.service;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;
import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.common.page.ColumnInfo;
import org.yansou.ci.common.page.OrderInfo;
import org.yansou.ci.common.page.PageCriteria;
import org.yansou.ci.common.page.Pagination;
import org.yansou.ci.common.utils.SimpleArrayUtils;
import org.yansou.ci.core.model.AbstractModel;
import org.yansou.ci.storage.common.dao.GeneralDao;
import org.yansou.ci.storage.common.page.PageSpecification;

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
public abstract class GeneralServiceImpl<T extends AbstractModel<ID>, ID extends Serializable> implements
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
	private Specification<T> createSpecification(PageCriteria pageCriteria) {
		return new PageSpecification<T>(pageCriteria);
	}

	/**
	 * 封装排序条件
	 *
	 * @param pageCriteria
	 *
	 * @return
	 */
	private Sort createSort(PageCriteria pageCriteria) {
		List<Sort.Order> orderList = new ArrayList<>();

		List<ColumnInfo> columnInfoList = pageCriteria.getColumnInfoList();

		if (CollectionUtils.isNotEmpty(columnInfoList)) {
			columnInfoList.stream().forEach(columnInfo -> {
				OrderInfo orderInfo = columnInfo.getOrderInfo();

				if (orderInfo != null) {
					String sortColumn = orderInfo.getPropertyName();
					Sort.Direction sortDirection = Sort.Direction.fromString(orderInfo.getOrderOp().getValue());

					orderList.add(new Sort.Order(sortDirection, sortColumn));
				}

			});
		}

		Sort sort = orderList.isEmpty() ? null : new Sort(orderList);

		return sort;
	}

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
	public int deleteById(ID id) throws DaoException {
		// 删除数据，不做物理删除，只更新对应的数据状态
		return updateStatus(AbstractModel.Status.DELETE.getValue(), id);
	}

	@Override
	public int deleteById(ID[] ids) throws DaoException {
		if (ArrayUtils.isEmpty(ids)) {
			return 0;
		}

		int result = 0;

		for (ID id : ids) {
			result += deleteById(id);
		}

		return result;
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
		entity.setStatus(AbstractModel.Status.UPDATE.getValue());

		return save(entity);
	}

	@Override
	public List<T> update(List<T> entities) throws DaoException {
		if (CollectionUtils.isNotEmpty(entities)) {
			entities.forEach(entity -> entity.setStatus(AbstractModel.Status.UPDATE.getValue()));

			return save(entities);
		}

		throw new IllegalArgumentException("Entities is empty");
	}

	@Override
	public T[] update(T[] entities) throws DaoException {
		if (ArrayUtils.isNotEmpty(entities)) {
			Arrays.stream(entities).forEach(entity -> entity.setStatus(AbstractModel.Status.UPDATE.getValue()));

			return save(entities);
		}

		throw new IllegalArgumentException("Entities is empty");
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
		return pagination(currentPageNo, pageSize, null, null);
	}

	@Override
	public Pagination<T> pagination(Integer currentPageNo, Integer pageSize, Specification<T> specification, Sort
			sort) throws DaoException {
		if (currentPageNo == null || currentPageNo <= 0) {
			currentPageNo = 1;
		}

		if (pageSize == null || pageSize <= 0) {
			pageSize = 10;
		}

		Pageable pageable = new PageRequest(currentPageNo - 1, pageSize, sort);

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
		Sort sort = createSort(pageCriteria);

		return pagination(currentPageNo, pageSize, specification, sort);
	}

	@Override
	public long countAll() throws DaoException {
		return generalDao.count();
	}

}
