package org.yansou.ci.storage.common.repository;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.Transformers;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author liutiejun
 * @create 2017-06-05 11:53
 */
public class SimpleGeneralRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements
		GeneralRepository<T, ID> {

	private static final Logger LOG = LogManager.getLogger(SimpleGeneralRepository.class);

	private final EntityManager entityManager;

	public SimpleGeneralRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);

		this.entityManager = entityManager;
	}

	public SimpleGeneralRepository(Class<T> domainClass, EntityManager entityManager) {
		super(domainClass, entityManager);

		this.entityManager = entityManager;
	}

	/**
	 * Accessing Hibernate APIs from JPA
	 *
	 * @return
	 */
	private SessionFactory getSessionFactory() {
		SessionFactory sessionFactory = entityManager.getEntityManagerFactory().unwrap(SessionFactory.class);

		return sessionFactory;
	}

	/**
	 * Accessing Hibernate APIs from JPA
	 *
	 * @return
	 */
	private Session getSession() {
		Session session = entityManager.unwrap(Session.class);

		return session;
	}

	/**
	 * Accessing Hibernate APIs from JPA
	 *
	 * @return
	 */
	private SessionImplementor getSessionImplementor() {
		SessionImplementor sessionImplementor = entityManager.unwrap(SessionImplementor.class);

		return sessionImplementor;
	}

	@Override
	public int updateStatus(Integer status, ID id) {
		Class<T> domainType = getDomainClass();

		SessionFactory sessionFactory = getSessionFactory();
		ClassMetadata classMetadata = sessionFactory.getClassMetadata(domainType);

		T persistentObject = (T) getSession().get(domainType, id);

		String[] propertyNames = classMetadata.getPropertyNames();

		if (status != null) {
			classMetadata.setPropertyValue(persistentObject, "status", status);

			return 1;
		}

		return 0;
	}

	@Override
	public int updateNotNullField(T entity) {
		Class<T> domainType = getDomainClass();

		SessionFactory sessionFactory = getSessionFactory();
		ClassMetadata classMetadata = sessionFactory.getClassMetadata(domainType);

		SessionImplementor sessionImplementor = getSessionImplementor();

		Serializable id = classMetadata.getIdentifier(entity, sessionImplementor);
		T persistentObject = (T) getSession().get(domainType, id);

		String[] propertyNames = classMetadata.getPropertyNames();

		for (String propertyName : propertyNames) {
			Object value = classMetadata.getPropertyValue(entity, propertyName);

			if (value != null) {
				classMetadata.setPropertyValue(persistentObject, propertyName, value);
			}
		}

		return 1;
	}

	@Override
	public List<Map<String, Object>> findByHql(String hql) {
		return findByHql(hql, -1, -1, null);
	}

	@Override
	public List<Map<String, Object>> findByHql(String hql, Map<String, Object> valuesMap) {
		return findByHql(hql, -1, -1, valuesMap);
	}

	@Override
	public List<Map<String, Object>> findByHql(String hql, int firstResult, int maxResults) {
		return findByHql(hql, firstResult, maxResults, null);
	}

	@Override
	public List<Map<String, Object>> findByHql(String hql, int firstResult, int maxResults, Map<String, Object>
			valuesMap) {
		Query query = createQuery(hql, firstResult, maxResults, valuesMap);

		List<Map<String, Object>> dataList = null;

		if (query != null) {
			dataList = query.list();
		}

		return dataList;
	}

	private List<Object> convert(List<Map<String, Object>> dataList, Class<?> clazz) {
		if (CollectionUtils.isEmpty(dataList) || clazz == null) {
			return null;
		}

		PropertyDescriptor[] propertyDescriptors = new PropertyDescriptor[0];
		try {
			propertyDescriptors = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
		} catch (IntrospectionException e) {
			LOG.error(e.getMessage(), e);
		}

		if (ArrayUtils.isEmpty(propertyDescriptors)) {
			return null;
		}

		List<Object> result = new ArrayList<>();

		for (Map<String, Object> rowMap : dataList) {
			try {
				Object object = createObject(rowMap, clazz, propertyDescriptors);

				result.add(object);
			} catch (IllegalAccessException e) {
				LOG.error(e.getMessage(), e);
			} catch (InstantiationException e) {
				LOG.error(e.getMessage(), e);
			}
		}

		return result;
	}

	private Object createObject(Map<String, Object> rowMap, Class<?> clazz, PropertyDescriptor[] propertyDescriptors)
			throws IllegalAccessException, InstantiationException {
		final Object object = clazz.newInstance();

		rowMap.forEach((k, v) -> {
			try {
				setValue(object, k, v, propertyDescriptors);
			} catch (InvocationTargetException e) {
				LOG.error(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				LOG.error(e.getMessage(), e);
			}
		});

		return object;
	}

	private void setValue(Object object, String propertyName, Object propertyValue, PropertyDescriptor[]
			propertyDescriptors) throws InvocationTargetException, IllegalAccessException {
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			if (propertyName.equalsIgnoreCase(propertyDescriptor.getName())) {
				Method method = propertyDescriptor.getWriteMethod();

				if (propertyValue != null) {
					propertyValue = ConvertUtils.convert(propertyValue, propertyDescriptor.getPropertyType());
				}

				method.invoke(object, propertyValue);
			}
		}

	}

	protected Query createQuery(String hql, int firstResult, int maxResults, Map<String, Object> valuesMap) {
		if (StringUtils.isBlank(hql)) {
			return null;
		}

		Query query = getSession().createQuery(hql);

		if (!MapUtils.isEmpty(valuesMap)) {
			for (Map.Entry<String, Object> entry : valuesMap.entrySet()) {
				String name = entry.getKey();
				Object value = entry.getValue();

				if (value instanceof Collection) {// 集合
					query.setParameterList(name, (Collection) value);
				} else if (value instanceof Object[]) {// 数组
					query.setParameterList(name, (Object[]) value);
				} else {
					query.setParameter(name, value);
				}

			}
		}

		if (firstResult >= 0) {
			query.setFirstResult(firstResult);
		}

		if (maxResults >= 0) {
			query.setMaxResults(maxResults);
		}

		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		return query;
	}

}
