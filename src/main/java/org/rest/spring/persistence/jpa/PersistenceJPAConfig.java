package org.rest.spring.persistence.jpa;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Profile( "jpa" )
@Configuration
@EnableTransactionManagement
public class PersistenceJPAConfig{
	
	@Value( "${driverClassName}" )
	private String driverClassName;
	
	@Value( "${url}" )
	private String url;
	
	@Value( "${persistence.dialect}" )
	String persistenceDialect;
	
	@Value( "${hibernate.show_sql}" )
	boolean hibernateShowSql;
	
	@Value( "${hibernate.hbm2ddl.auto}" )
	String hibernateHbm2ddlAuto;
	
	// beans
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){
		final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource( this.restDataSource() );
		factoryBean.setPackagesToScan( new String[ ] { "org.rest" } );
		
		final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter(){
			{
				this.setDatabasePlatform( PersistenceJPAConfig.this.persistenceDialect );
				this.setShowSql( PersistenceJPAConfig.this.hibernateShowSql );
				this.setGenerateDdl( true );
			}
		};
		factoryBean.setJpaVendorAdapter( vendorAdapter );
		
		factoryBean.setJpaProperties( this.additionlProperties() );
		
		return factoryBean;
	}
	
	@Bean
	public DataSource restDataSource(){
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName( this.driverClassName );
		dataSource.setUrl( this.url );
		dataSource.setUsername( "restUser" );
		dataSource.setPassword( "restmy5ql" );
		return dataSource;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(){
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory( this.entityManagerFactoryBean().getObject() );
		
		return transactionManager;
	}
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor(){
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	//
	final Properties additionlProperties(){
		return new Properties(){
			{
				// use this to inject additional properties in the EntityManager
			}
		};
	}
	
}
