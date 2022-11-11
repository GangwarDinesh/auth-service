package com.auth.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@ConfigurationProperties(prefix = "mysqldb.data-source")
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager", basePackages = "com.auth.repository")
public class DatabaseConfig {

	@Autowired
	private Environment env;

	@Value("${hibernate.current_session_context_class}")
	private String hibernateSession;

	@Value("${hibernate.dialect}")
	private String hibernateDialect;

	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactory(EntityManagerFactoryBuilder factoryBuilder) {
		EntityManagerFactoryBuilder.Builder builder = factoryBuilder.dataSource(dataSource());
		Map<String, String> properties = new HashMap<>();
		properties.put("hibernate.current_session_context_class", hibernateSession);
		properties.put("hibernate.dialect", hibernateDialect);

		Map<String, ?> hibernateProps = properties.entrySet().stream()
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
		if (null != hibernateProps) {
			builder.properties(properties);
		}
		return builder.packages("com.auth.entity").build();
	}

	@Bean
	@ConfigurationProperties(prefix = "mysqldb.data-source")
	public HikariConfig hikariConfig() {
	    return new HikariConfig();
	}
	
	@Bean(name = "dataSource")
	public DataSource dataSource() {
		return new HikariDataSource(hikariConfig());
	}

	@Bean(name = "transactionManager")
	public PlatformTransactionManager getTransactionManager(
			@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager txManager = new JpaTransactionManager(entityManagerFactory);
		int defaultTimeout = Integer.parseInt(env.getProperty("mysqldb.query.defaultTimeout"));
		txManager.setDefaultTimeout(defaultTimeout);
		return txManager;
	}
}
