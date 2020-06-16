package com.sts.employeems.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.sts.employeems.utill.Encrypt_Deprypt_Util;

/**
 * 
 * @author Sri
 * @comment It will provide database configuration
 */
@Configuration
@PropertySource("classpath:application-config.properties")
public class DBConfig {

	@Value("${db.url}")
	private String url;

	@Value("${db.password}")
	private String password;

	@Autowired
	private Environment environment;

	@Autowired
	private Encrypt_Deprypt_Util encDec;

	private static final Logger LOGGER = LoggerFactory.getLogger(DBConfig.class);

	@Bean
	public DataSource dataSource() {

		LOGGER.info("Start of dataSource()");
		DriverManagerDataSource dataSource1 = null;
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		try {
			dataSource.setUrl(url);
			dataSource.setUsername(environment.getProperty("db.useName"));

			dataSource1 = setPassword(dataSource, password);
			LOGGER.info("End of dataSource()");

		} catch (Exception e) {
			LOGGER.error("Error while creating the datasource {}", e);
		}

		return dataSource1;

	}

	private DriverManagerDataSource setPassword(DriverManagerDataSource dataSource, String password) {
		String decryptPwd = null;
		try {
			LOGGER.info("decrypted password for database ={} ", password);
			decryptPwd = encDec.decrypt(password);

			dataSource.setPassword(decryptPwd);

		} catch (Exception e) {
			LOGGER.error("Error while decrypting the password");
		}

		return dataSource;
	}

	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {

		HibernateJpaVendorAdapter adapter = null;
		LocalContainerEntityManagerFactoryBean factoryBean = null;

		try {
			LOGGER.info("Start of entityManagerFactoryBean()");
			factoryBean = new LocalContainerEntityManagerFactoryBean();

			adapter = new HibernateJpaVendorAdapter();
			adapter.setDatabasePlatform(environment.getProperty("db.dialect"));
			adapter.setGenerateDdl(true);

			factoryBean.setDataSource(dataSource());
			factoryBean.setPackagesToScan("com.sts.employeems.entity");
			factoryBean.setJpaVendorAdapter(adapter);

			LOGGER.info("End of entityManagerFactoryBean()");
		} catch (Exception e) {
			LOGGER.error("Error while creating the entityManagerFactoryBean", e);
		}
		return factoryBean;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {

		JpaTransactionManager jpaTransactionManager = null;

		try {
			LOGGER.info("Start of transactionManager(...)");

			jpaTransactionManager = new JpaTransactionManager(emf);
			jpaTransactionManager.setDataSource(dataSource());

			LOGGER.info("End of transactionManager(...)");
		} catch (Exception e) {
			LOGGER.info("Error while creating the transactionManager(...)", e);
		}
		return jpaTransactionManager;
	}
}
