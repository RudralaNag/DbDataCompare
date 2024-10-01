package com.americanexpress.dbcompare.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement

public class DataSourceConfig {

	// Database1 DataSource
	@Primary
	@Bean(name = "database1DataSource")
	@ConfigurationProperties(prefix = "spring.datasource.database1")
	public DataSource database1DataSource() {
		return DataSourceBuilder.create().build();
	}

	// JPA Config for Database1
	@Primary
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("database1DataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("com.americanexpress.dbcompare.entity") // Entities for database1
				.persistenceUnit("database1").build();
	}

	@Primary
	@Bean(name = "database1TransactionManager")
	public PlatformTransactionManager database1TransactionManager(
			@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

	// Database2 DataSource
	@Bean(name = "database2DataSource")
	@ConfigurationProperties(prefix = "spring.datasource.database2")
	public DataSource database2DataSource() {
		return DataSourceBuilder.create().build();
	}

	// JPA Config for Database2
	@Bean(name = "database2EntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean database2EntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("database2DataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("com.americanexpress.dbcompare.entity") // Entities for database2
				.persistenceUnit("database2").build();
	}

	@Bean(name = "database2TransactionManager")
	public PlatformTransactionManager database2TransactionManager(
			@Qualifier("database2EntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
