package org.rest.persistence.service;

import java.util.List;

import org.rest.common.IEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

@Transactional
public abstract class AbstractService< T extends IEntity > implements IService< T >{
	protected final Logger logger = LoggerFactory.getLogger( this.getClass() );
	
	public AbstractService(){
		super();
	}
	
	// API
	
	// find/get
	
	@Override
	@Transactional( readOnly = true )
	public T findOne( final long id ){
		return this.getDao().findOne( id );
	}
	
	@Override
	@Transactional( readOnly = true )
	public List< T > findAll(){
		return Lists.newArrayList( this.getDao().findAll() );
	}
	
	@Override
	@Transactional( readOnly = true )
	public Page< T > findPaginated( final int page, final int size, final String sortBy ){
		Sort sortInfo = null;
		if( sortBy != null ){
			sortInfo = new Sort( sortBy );
		}
		
		return getDao().findAll( new PageRequest( page, size, sortInfo ) );
	}
	
	// save/create/persist
	
	@Override
	public T create( final T entity ){
		Preconditions.checkNotNull( entity );
		
		final T persistedEntity = this.getDao().save( entity );
		
		return persistedEntity;
	}
	
	// update/merge
	
	@Override
	public void update( final T entity ){
		Preconditions.checkNotNull( entity );
		
		this.getDao().save( entity );
	}
	
	// delete
	
	@Override
	public void deleteAll(){
		this.getDao().deleteAll();
	}
	@Override
	public void delete( final long id ){
		this.getDao().delete( id );
	}
	
	//
	protected abstract PagingAndSortingRepository< T, Long > getDao();
	
}
