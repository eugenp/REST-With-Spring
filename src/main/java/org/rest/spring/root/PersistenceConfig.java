package org.rest.spring.root;

import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class PersistenceConfig{
	
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
	public BasicDataSource restDataSource(){
		final BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName( this.driverClassName );
		basicDataSource.setUrl( this.url );
		basicDataSource.setUsername( "restUser" );
		basicDataSource.setPassword( "restmy5ql" );
		
		return basicDataSource;
	}
	
	@Bean
	public AnnotationSessionFactoryBean alertsSessionFactory(){
		final AnnotationSessionFactoryBean alertsSessionFactory = new AnnotationSessionFactoryBean();
		alertsSessionFactory.setDataSource( this.restDataSource() );
		alertsSessionFactory.setPackagesToScan( new String[ ] { "org.rest" } );
		alertsSessionFactory.setHibernateProperties( this.hibernateProperties() );
		
		return alertsSessionFactory;
	}
	
	@Bean
	public HibernateTransactionManager transactionManager(){
		final HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory( this.alertsSessionFactory().getObject() );
		
		return transactionManager;
	}
	
	final Properties hibernateProperties(){
		return new Properties(){
			{
				this.put( "hibernate.dialect", PersistenceConfig.this.hibernateDialect );
				this.put( "hibernate.hbm2ddl.auto", PersistenceConfig.this.hibernateHbm2ddlAuto );
				this.put( "hibernate.show_sql", PersistenceConfig.this.hibernateShowSql );
				
				// in progresses still
				this.put( "hibernate.transaction.factory_class", "org.springframework.orm.hibernate3.SpringTransactionFactory" ); // SpringTransactionFactory.class.getSimpleName()
				this.put( "hibernate.current_session_context_class", "thread" );
			}
		};
	}
	
}
