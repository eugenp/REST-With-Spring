package org.rest.persistence.service;

import java.util.List;

import org.rest.common.IEntity;
import org.rest.persistence.event.EntityCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

@Transactional
public abstract class AbstractService< T extends IEntity > implements IService< T >{
	protected final Logger logger = LoggerFactory.getLogger( getClass() );
	
	private final Class< T > clazz;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	public AbstractService( final Class< T > clazzToSet ){
		Preconditions.checkNotNull( clazzToSet );
		clazz = clazzToSet;
	}
	
	// find/get
	
	@Override
	@Transactional( readOnly = true )
	public T findOne( final long id ){
		return getDao().findOne( id );
	}
	
	@Override
	@Transactional( readOnly = true )
	public List< T > findAll(){
		return Lists.newArrayList( getDao().findAll() );
	}
	
	@Override
	@Transactional( readOnly = true )
	public Page< T > findPaginated( final int page, final int size ){
		return getDao().findAll( new PageRequest( page, size ) );
	}
	
	// save/create/persist
	
	@Override
	public T save( final T entity ){
		Preconditions.checkNotNull( entity );
		
		final T persistedEntity = getDao().save( entity );
		
		eventPublisher.publishEvent( new EntityCreatedEvent< T >( this, clazz, entity ) );
		
		return persistedEntity;
	}
	
	// update/merge
	
	@Override
	public void update( final T entity ){
		Preconditions.checkNotNull( entity );
		
		getDao().save( entity );
	}
	
	// delete
	
	@Override
	public void deleteAll(){
		getDao().deleteAll();
	}
	@Override
	public void delete( final List< T > entities ){
		Preconditions.checkNotNull( entities );
		
		getDao().delete( entities );
	}
	@Override
	public void delete( final long id ){
		/*final T entity = this.findOne( id );
		RestPreconditions.checkNotNull( entity );*/
		// - note: disabled because it doesn't belong here; it doesn't really belong in the controller either, so I'm catching the exception and translating in the controller - and this will go away
		
		getDao().delete( id );
	}
	
	// template method
	
	protected abstract PagingAndSortingRepository< T, Long > getDao();
	
	// util
	
	public final void setEventPublisher( final ApplicationEventPublisher eventPublisherToSet ){
		this.eventPublisher = eventPublisherToSet;
	}
	
}
