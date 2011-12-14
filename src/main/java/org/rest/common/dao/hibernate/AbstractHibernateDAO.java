package org.rest.common.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.rest.common.dao.ICommonOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;

@SuppressWarnings( "unchecked" )
@Transactional( propagation = Propagation.SUPPORTS )
public abstract class AbstractHibernateDAO< T extends Serializable > implements ICommonOperations< T >{
	private Class< T > clazz;
	
	@Autowired
	SessionFactory sessionFactory;
	
	public AbstractHibernateDAO(){
		super();
	}
	
	//
	
	public final void setClazz( final Class< T > clazzToSet ){
		Preconditions.checkNotNull( clazzToSet );
		this.clazz = clazzToSet;
	}
	
	// get
	
	@Override
	@Transactional( readOnly = true )
	public T getById( final Long id ){
		Preconditions.checkArgument( id != null );
		
		return (T) this.getCurrentSession().get( this.clazz, id );
	}
	
	@Override
	@Transactional( readOnly = true )
	public List< T > getAll(){
		return this.getCurrentSession().createQuery( "from " + this.clazz.getName() ).list();
	}
	
	// create/persist
	
	@Override
	public void create( final T entity ){
		Preconditions.checkNotNull( entity );
		
		this.getCurrentSession().persist( entity );
	}
	
	// update
	
	@Override
	public void update( final T entity ){
		Preconditions.checkNotNull( entity );
		
		this.getCurrentSession().merge( entity );
	}
	
	// delete
	
	@Override
	public void delete( final T entity ){
		Preconditions.checkNotNull( entity );
		
		this.getCurrentSession().delete( entity );
	}
	
	public void deleteById( final Long entityId ){
		final T entity = this.getById( entityId );
		Preconditions.checkState( entity != null );
		
		this.delete( entity );
	}
	
	// util
	
	protected Session getCurrentSession(){
		return this.sessionFactory.getCurrentSession();
	}
	
}
