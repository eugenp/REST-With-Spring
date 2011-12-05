package org.rest.spring.persistence.hibernate;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class PersistenceHibernateConfig{
	
	@Value( "${driverClassName}" )
	private String driverClassName;
	
	@Value( "${url}" )
	private String url;
	
	@Value( "${hibernate.dialect}" )
	String hibernateDialect;
	
	@Value( "${hibernate.show_sql}" )
	String hibernateShowSql;
	
	@Value( "${hibernate.hbm2ddl.auto}" )
	String hibernateHbm2ddlAuto;
	
	// beans
	
	@Bean
	public AnnotationSessionFactoryBean alertsSessionFactory(){
		final AnnotationSessionFactoryBean sessionFactory = new AnnotationSessionFactoryBean();
		sessionFactory.setDataSource( this.restDataSource() );
		sessionFactory.setPackagesToScan( new String[ ] { "org.rest" } );
		sessionFactory.setHibernateProperties( this.hibernateProperties() );
		return sessionFactory;
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
	public HibernateTransactionManager transactionManager(){
		final HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory( this.alertsSessionFactory().getObject() );
		
		return transactionManager;
	}
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor(){
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	// util
	
	final Properties hibernateProperties(){
		return new Properties(){
			{
				this.put( "hibernate.dialect", PersistenceHibernateConfig.this.hibernateDialect );
				this.put( "hibernate.hbm2ddl.auto", PersistenceHibernateConfig.this.hibernateHbm2ddlAuto );
				this.put( "hibernate.show_sql", PersistenceHibernateConfig.this.hibernateShowSql );
				
				// in progresses still
				// this.put( "hibernate.transaction.factory_class", "org.springframework.orm.hibernate3.SpringTransactionFactory" ); // SpringTransactionFactory.class.getSimpleName()
				// this.put( "hibernate.current_session_context_class", "thread" ); // org.springframework.orm.hibernate3.SpringSessionContext
			}
		};
	}
	
}
