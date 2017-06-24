package org.yansou.ci.storage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.yansou.ci.storage.common.repository.SimpleGeneralRepository;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

/**
 * @author liutiejun
 * @create 2017-05-05 09:31
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactoryPrimary", transactionManagerRef =
		"transactionManagerPrimary", basePackages = {"org.yansou.ci.storage.repository"},//设置Repository所在位置
		repositoryBaseClass = SimpleGeneralRepository.class)
public class PrimaryConfigurer {

	@Autowired
	@Qualifier("primaryDataSource")
	private DataSource primaryDataSource;

	@Primary
	@Bean(name = "entityManagerPrimary")
	public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
		return entityManagerFactoryPrimary(builder).getObject().createEntityManager();
	}

	@Primary
	@Bean(name = "entityManagerFactoryPrimary")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(primaryDataSource).properties(getVendorProperties(primaryDataSource)).packages("org" +
				".yansou.ci.core.db.model") //设置实体类所在位置
				.persistenceUnit("primaryPersistenceUnit").build();
	}

	@Autowired
	private JpaProperties jpaProperties;

	private Map<String, String> getVendorProperties(DataSource dataSource) {
		return jpaProperties.getHibernateProperties(dataSource);
	}

	@Primary
	@Bean(name = "transactionManagerPrimary")
	public PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {
		return new JpaTransactionManager(entityManagerFactoryPrimary(builder).getObject());
	}

}