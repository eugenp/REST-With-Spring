package org.rest.spring.persistence.jpa;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ImportResource( "classpath*:*springDataConfig.xml" )
@ComponentScan( { "org.rest.sec.persistence" } )
public class PersistenceJPAConfig{
	
	@Value( "${jdbc.driverClassName}" )
	private String driverClassName;
	
	@Value( "${jdbc.url}" )
	private String url;
	
	@Value( "${hibernate.dialect}" )
	String hibernateDialect;
	
	@Value( "${hibernate.show_sql}" )
	boolean hibernateShowSql;
	
	@Value( "${hibernate.hbm2ddl.auto}" )
	String hibernateHbm2ddlAuto;
	
	@Value( "${jpa.generateDdl}" )
	boolean jpaGenerateDdl;
	
	public PersistenceJPAConfig(){
		super();
	}
	
	// beans
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){
		final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource( restDataSource() );
		factoryBean.setPackagesToScan( new String[ ] { "org.rest" } );
		
		final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter(){
			{
				setDatabase( Database.H2 ); // TODO: comment this out, see if anything fails
				setDatabasePlatform( hibernateDialect );
				setShowSql( hibernateShowSql );
				setGenerateDdl( jpaGenerateDdl );
			}
		};
		factoryBean.setJpaVendorAdapter( vendorAdapter );
		
		factoryBean.setJpaProperties( additionlProperties() );
		
		return factoryBean;
	}
	
	@Bean
	public DataSource restDataSource(){
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName( driverClassName );
		dataSource.setUrl( url );
		// dataSource.setUsername( "restUser" );
		// dataSource.setPassword( "restmy5ql" );
		return dataSource;
	}
	
	@Bean
	public JpaTransactionManager transactionManager(){
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory( entityManagerFactoryBean().getObject() );
		
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
