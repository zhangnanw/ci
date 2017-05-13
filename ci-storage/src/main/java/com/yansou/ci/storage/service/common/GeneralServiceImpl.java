//package com.yansou.ci.storage.service.common;
//
//import com.yansou.ci.common.exception.DaoException;
//import com.yansou.ci.common.page.PageCriteria;
//import com.yansou.ci.common.page.Pagination;
//import com.yansou.ci.common.utils.SimpleArrayUtils;
//import com.yansou.ci.storage.common.service.GeneralService;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.lang3.ArrayUtils;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.util.Assert;
//
//import javax.persistence.Id;
//import javax.transaction.Transactional;
//import java.io.Serializable;
//import java.lang.reflect.Field;
//import java.lang.reflect.ParameterizedType;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author liutiejun
// * @create 2017-05-10 18:19
// */
//@Transactional
//public abstract class GeneralServiceImpl<T extends Serializable, ID extends Serializable> implements GeneralService<T, ID> {
//
//
//	private String entityName;
//	private Class<T> entityClass;
//	private String pkName;
//
//	protected JpaRepository<T, ID> jpaRepository;
//
//	@SuppressWarnings("unchecked")
//	public GeneralServiceImpl() {
//		this.entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
//				.getActualTypeArguments()[0];
//
//		this.entityName = this.entityClass.getSimpleName();
//
//		Field[] fields = this.entityClass.getDeclaredFields();
//		for (Field f : fields) {
//			if (f.isAnnotationPresent(Id.class)) {
//				this.pkName = f.getName();
//				break;
//			}
//		}
//	}
//
//	public abstract void setJpaRepository(JpaRepository<T, ID> jpaRepository);
//
//	@Override
//	public T save(T entity) throws DaoException {
//		Assert.notNull(entity, "Entity is null");
//
//		return jpaRepository.save(entity);
//	}
//
//	@Override
//	public List<T> save(List<T> entities) throws DaoException {
//		if (CollectionUtils.isEmpty(entities)) {
//			throw new IllegalArgumentException("Entities is empty");
//		}
//
//		return jpaRepository.save(entities);
//	}
//
//	@Override
//	public List<T> save(T[] entities) throws DaoException {
//		if (ArrayUtils.isEmpty(entities)) {
//			throw new IllegalArgumentException("Entities is empty");
//		}
//
//		List<T> list = new ArrayList<>();
//
//		for (T t : entities) {
//			list.add(t);
//		}
//
//		return jpaRepository.save(list);
//	}
//
//	@Override
//	public T saveOrUpdate(T entity) throws DaoException {
//		Assert.notNull(entity, "Entity is null");
//
//		return jpaRepository.save(entity);
//	}
//
//	@Override
//	public List<T> saveOrUpdate(List<T> entities) throws DaoException {
//		if (CollectionUtils.isEmpty(entities)) {
//			throw new IllegalArgumentException("Entities is empty");
//		}
//
//		return jpaRepository.save(entities);
//	}
//
//	@Override
//	public void deleteById(ID id) throws DaoException {
//		jpaRepository.delete(id);
//	}
//
//	@Override
//	public void delete(T entity) throws DaoException {
//		jpaRepository.delete(entity);
//	}
//
//	@Override
//	public void delete(List<T> entities) throws DaoException {
//		jpaRepository.deleteInBatch(entities);
//	}
//
//	@Override
//	public T update(T entity) throws DaoException {
//		return jpaRepository.save(entity);
//	}
//
//	@Override
//	public List<T> update(List<T> entities) throws DaoException {
//		if (CollectionUtils.isEmpty(entities)) {
//			throw new IllegalArgumentException("Entities is empty");
//		}
//
//		return jpaRepository.save(entities);
//	}
//
//	@Override
//	public T findById(ID id) throws DaoException {
//		return jpaRepository.findOne(id);
//	}
//
//	@Override
//	public List<T> findAll() throws DaoException {
//		return jpaRepository.findAll();
//	}
//
//	@Override
//	public Pagination<T> paginationAll(Integer currentPageNo, Integer pageSize) throws DaoException {
//		if (currentPageNo == null || currentPageNo <= 0) {
//			currentPageNo = 1;
//		}
//
//		if (pageSize == null || pageSize <= 0) {
//			pageSize = 10;
//		}
//
//		Pageable pageable = new PageRequest(currentPageNo - 1, pageSize);
//		Page<T> page = jpaRepository.findAll(pageable);
//
//		long totalCount = page.getTotalElements();
//
//		List<T> dataList = page.getContent();
//		T[] dataArray = SimpleArrayUtils.newArrayByClass(entityClass, dataList.size());
//
//		return new Pagination<T>(totalCount, pageSize, currentPageNo, dataArray);
//	}
//
//	@Override
//	public Pagination<T> pagination(PageCriteria pageCriteria) throws DaoException {
//		Integer currentPageNo = pageCriteria.getCurrentPageNo();
//		Integer pageSize = pageCriteria.getPageSize();
//
//		if (currentPageNo == null || currentPageNo <= 0) {
//			currentPageNo = 1;
//		}
//
//		if (pageSize == null || pageSize <= 0) {
//			pageSize = 10;
//		}
//
//		Pageable pageable = new PageRequest(currentPageNo - 1, pageSize);
//
//		Page<T> page = jpaRepository.findall
//
//		long totalCount = page.getTotalElements();
//
//		List<T> dataList = page.getContent();
//		T[] dataArray = SimpleArrayUtils.newArrayByClass(entityClass, dataList.size());
//
//		return new Pagination<T>(totalCount, pageSize, currentPageNo, dataArray);
//	}
//
//	@Override
//	public long countAll() throws DaoException {
//		return jpaRepository.count();
//	}
//
//}
