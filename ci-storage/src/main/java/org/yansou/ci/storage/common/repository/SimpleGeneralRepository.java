package org.yansou.ci.storage.common.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * @author liutiejun
 * @create 2017-06-05 11:53
 */
public class SimpleGeneralRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements
		GeneralRepository<T, ID> {

	private static final Logger LOG = LogManager.getLogger(SimpleGeneralRepository.class);

	private final EntityManager entityManager;

	private SessionFactory sessionFactory;

	public SimpleGeneralRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);

		this.entityManager = entityManager;

		this.sessionFactory = getSessionFactory(entityManager);
	}

	public SimpleGeneralRepository(Class<T> domainClass, EntityManager entityManager) {
		super(domainClass, entityManager);

		this.entityManager = entityManager;

		this.sessionFactory = getSessionFactory(entityManager);
	}

	private SessionFactory getSessionFactory(EntityManager entityManager) {
		HibernateEntityManagerFactory hibernateEntityManagerFactory = (HibernateEntityManagerFactory) entityManager
				.getEntityManagerFactory();

		return hibernateEntityManagerFactory.getSessionFactory();
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public int updateStatus(Integer status, ID id) {
		Class<T> domainType = getDomainClass();
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
	public void updateNotNullField(T entity) {
		Class<T> domainType = getDomainClass();
		ClassMetadata classMetadata = sessionFactory.getClassMetadata(domainType);

		SessionImplementor session = (SessionImplementor) sessionFactory.getCurrentSession();

		Serializable id = classMetadata.getIdentifier(entity, session);
		T persistentObject = (T) getSession().get(domainType, id);

		String[] propertyNames = classMetadata.getPropertyNames();

		for (String propertyName : propertyNames) {
			Object value = classMetadata.getPropertyValue(entity, propertyName);

			if (value != null) {
				classMetadata.setPropertyValue(persistentObject, propertyName, value);
			}
		}
	}
}
