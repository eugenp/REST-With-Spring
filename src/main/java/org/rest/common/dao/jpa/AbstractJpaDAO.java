package org.rest.common.dao.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.rest.common.dao.ICommonOperations;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;

@SuppressWarnings( "unchecked" )
@Transactional( propagation = Propagation.SUPPORTS )
public abstract class AbstractJpaDAO< T extends Serializable > implements ICommonOperations< T >{
	private Class< T > clazz;
	
	protected EntityManager entityManager;
	
	public AbstractJpaDAO(){
		super();
	}
	
	//
	@PersistenceContext
	public void setEm( final EntityManager emToSet ){
		Preconditions.checkNotNull( emToSet );
		this.entityManager = emToSet;
	}
	
	public void setClazz( final Class< T > clazzToSet ){
		Preconditions.checkNotNull( clazzToSet );
		this.clazz = clazzToSet;
	}
	
	// get
	
	@Override
	@Transactional( readOnly = true )
	public T getById( final Long id ){
		Preconditions.checkArgument( id != null );
		
		return this.entityManager.find( this.clazz, id );
	}
	
	@Override
	@Transactional( readOnly = true )
	public List< T > getAll(){
		return this.entityManager.createQuery( "from " + this.clazz.getName() ).getResultList();
	}
	
	// create/persist
	
	@Override
	public void create( final T entity ){
		Preconditions.checkNotNull( entity );
		
		this.entityManager.persist( entity );
	}
	
	// update
	
	@Override
	public void update( final T entity ){
		Preconditions.checkNotNull( entity );
		
		this.entityManager.merge( entity );
	}
	
	// delete
	
	@Override
	public void delete( final T entity ){
		Preconditions.checkNotNull( entity );
		
		this.entityManager.remove( entity );
	}
	
	public void deleteById( final Long entityId ){
		final T entity = this.getById( entityId );
		Preconditions.checkState( entity != null );
		
		this.delete( entity );
	}
	
}
