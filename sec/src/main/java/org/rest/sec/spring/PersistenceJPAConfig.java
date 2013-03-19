package org.rest.sec.spring;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ImportResource("classpath*:*secPersistenceConfig.xml")
@ComponentScan({ "org.rest.sec.persistence" })
@PropertySource({ "classpath:persistence-${persistenceTarget:mysql}.properties" })
public class PersistenceJPAConfig {

    @Value("${jdbc.driverClassName}")
    private String driverClassName;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jpa.generateDdl}")
    boolean jpaGenerateDdl;

    // Hibernate specific
    @Value("${hibernate.dialect}")
    String hibernateDialect;
    @Value("${hibernate.show_sql}")
    boolean hibernateShowSql;
    @Value("${hibernate.hbm2ddl.auto}")
    String hibernateHbm2ddlAuto;

    @Value("${jdbc.username}")
    private String jdbcUsername;
    @Value("${jdbc.password}")
    private String jdbcPassword;

    public PersistenceJPAConfig() {
        super();
    }

    // beans

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(restDataSource());
        factoryBean.setPackagesToScan(new String[] { "org.rest" });

        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter() {
            {
                // setDatabase( Database.H2 ); // TODO: is this necessary
                setDatabasePlatform(hibernateDialect);
                setShowSql(hibernateShowSql);
                setGenerateDdl(jpaGenerateDdl);
            }
        };
        factoryBean.setJpaVendorAdapter(vendorAdapter);

        factoryBean.setJpaProperties(additionlProperties());

        return factoryBean;
    }

    @Bean
    public DataSource restDataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(jdbcUsername);
        dataSource.setPassword(jdbcPassword);
        return dataSource;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    //
    final Properties additionlProperties() {
        return new Properties() {
            {
                // use this to inject additional properties in the EntityManager
                setProperty("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
                setProperty("hibernate.ejb.naming_strategy", org.hibernate.cfg.ImprovedNamingStrategy.class.getName());
            }
        };
    }

}
