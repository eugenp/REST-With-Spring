package org.rest.common.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;

/**
 * @author eugenp
 */
@Transactional( propagation = Propagation.SUPPORTS )
public class CustomHibernateDaoSupport< T extends Serializable > extends HibernateDaoSupport{
	protected Class< T > clazz;
	
	public CustomHibernateDaoSupport( final Class< T > theClassToSet ){
		super();
		
		this.clazz = theClassToSet;
	}
	
	// config
	
	@Autowired
	public final void init( final SessionFactory factory ){
		this.setSessionFactory( factory );
	}
	
	// API - get
	
	@Transactional( readOnly = true )
	public T getById( final Long id ){
		Preconditions.checkNotNull( id );
		
		return this.getHibernateTemplate().get( this.clazz, id );
	}
	
	@Transactional( readOnly = true )
	public List< T > getAll(){
		return this.getHibernateTemplate().loadAll( this.clazz );
	}
	
	// API - create
	
	public Long create( final T entity ){
		Preconditions.checkNotNull( entity );
		
		return (Long) this.getHibernateTemplate().save( entity );
	}
	
	// API - update
	
	public void update( final T entity ){
		Preconditions.checkNotNull( entity );
		
		this.getHibernateTemplate().saveOrUpdate( entity );
	}
	
	// API - delete
	
	public void delete( final T entity ){
		Preconditions.checkNotNull( entity );
		this.getHibernateTemplate().delete( entity );
	}
	
}
