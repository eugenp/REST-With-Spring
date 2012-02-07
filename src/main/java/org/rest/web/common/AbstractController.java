package org.rest.web.common;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.rest.common.IEntity;
import org.rest.common.event.PaginatedResultsRetrievedEvent;
import org.rest.common.event.ResourceCreatedEvent;
import org.rest.common.event.SingleResourceRetrievedEvent;
import org.rest.common.exceptions.BadRequestException;
import org.rest.common.exceptions.ConflictException;
import org.rest.common.exceptions.ResourceNotFoundException;
import org.rest.common.web.RestPreconditions;
import org.rest.persistence.service.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.base.Preconditions;

public abstract class AbstractController< T extends IEntity >{
	protected final Logger logger = LoggerFactory.getLogger( getClass() );
	
	private Class< T > clazz;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	public AbstractController( final Class< T > clazzToSet ){
		super();
		
		Preconditions.checkNotNull( clazzToSet );
		clazz = clazzToSet;
	}
	
	// template method
	
	protected abstract IService< T > getService();
	
	// find/get
	
	protected final T findOneInternal( final Long id, final UriComponentsBuilder uriBuilder, final HttpServletResponse response ){
		T resource = null;
		try{
			resource = RestPreconditions.checkNotNull( getService().findOne( id ) );
		}
		catch( final InvalidDataAccessApiUsageException ex ){
			logger.error( "InvalidDataAccessApiUsageException on find operation" );
			logger.warn( "InvalidDataAccessApiUsageException on find operation", ex );
			throw new ConflictException( ex );
		}
		
		eventPublisher.publishEvent( new SingleResourceRetrievedEvent< T >( clazz, uriBuilder, response ) );
		
		return resource;
	}
	
	protected final List< T > findAllInternal(){
		return getService().findAll();
	}
	
	public List< T > findPaginatedInternal( final int page, final int size, final String sortBy, final UriComponentsBuilder uriBuilder, final HttpServletResponse response ){
		Page< T > resultPage = null;
		try{
			resultPage = getService().findPaginated( page, size, sortBy );
		}
		catch( final InvalidDataAccessApiUsageException apiEx ){
			logger.error( "InvalidDataAccessApiUsageException on find operation" );
			logger.warn( "InvalidDataAccessApiUsageException on find operation", apiEx );
			throw new BadRequestException( apiEx );
		}
		
		if( page > resultPage.getTotalPages() ){
			throw new ResourceNotFoundException();
		}
		eventPublisher.publishEvent( new PaginatedResultsRetrievedEvent< T >( clazz, uriBuilder, response, page, resultPage.getTotalPages(), size ) );
		
		return resultPage.getContent();
	}

	// save/create/persist
	
	protected final void saveInternal( final T resource, final UriComponentsBuilder uriBuilder, final HttpServletResponse response ){
		RestPreconditions.checkRequestElementNotNull( resource );
		RestPreconditions.checkRequestState( resource.getId() == null );
		try{
			getService().save( resource );
		}
		catch( final IllegalStateException ex ){
			logger.error( "IllegalStateException on create operation for: " + resource.getClass().getSimpleName() );
			logger.warn( "IllegalStateException on create operation for: " + resource.getClass().getSimpleName(), ex );
			throw new ResourceNotFoundException( ex );
		}
		catch( final IllegalArgumentException ex ){
			logger.error( "IllegalArgumentException on create operation for: " + resource.getClass().getSimpleName() );
			logger.warn( "IllegalArgumentException on create operation for: " + resource.getClass().getSimpleName(), ex );
			throw new ConflictException( ex );
		}
		catch( final DataIntegrityViolationException ex ){ // on unique constraint
			logger.error( "DataIntegrityViolationException on create operation for: " + resource.getClass().getSimpleName() );
			logger.warn( "DataIntegrityViolationException on create operation for: " + resource.getClass().getSimpleName(), ex );
			throw new ConflictException( ex );
		}
		catch( final InvalidDataAccessApiUsageException dataEx ){ // on saving a new Entity that also contains new/unsaved entities
			logger.error( "InvalidDataAccessApiUsageException on create operation for: " + resource.getClass().getSimpleName() );
			logger.warn( "InvalidDataAccessApiUsageException on create operation for: " + resource.getClass().getSimpleName(), dataEx );
			throw new ConflictException( dataEx );
		}
		catch( final DataAccessException dataEx ){
			logger.error( "Generic DataAccessException on create operation for: " + resource.getClass().getSimpleName() );
			logger.warn( "Generic DataAccessException on create operation for: " + resource.getClass().getSimpleName(), dataEx );
			throw new ConflictException( dataEx );
		}
		
		eventPublisher.publishEvent( new ResourceCreatedEvent< T >( clazz, uriBuilder, response, resource.getId() ) );
	}
	
	// update
	
	protected final void updateInternal( final T resource ){
		RestPreconditions.checkRequestElementNotNull( resource );
		RestPreconditions.checkRequestElementNotNull( resource.getId() );
		RestPreconditions.checkNotNull( getService().findOne( resource.getId() ) );
		
		try{
			getService().save( resource );
		}
		catch( final InvalidDataAccessApiUsageException dataEx ){
			logger.error( "InvalidDataAccessApiUsageException on update operation for: " + resource.getClass().getSimpleName() );
			logger.warn( "InvalidDataAccessApiUsageException on update operation for: " + resource.getClass().getSimpleName(), dataEx );
			throw new ConflictException( dataEx );
		}
		catch( final DataIntegrityViolationException dataEx ){ // on unique constraint
			logger.error( "DataIntegrityViolationException on update operation for: " + resource.getClass().getSimpleName() );
			logger.warn( "DataIntegrityViolationException on update operation for: " + resource.getClass().getSimpleName(), dataEx );
			throw new ConflictException( dataEx );
		}
	}
	
	// delete/remove
	
	protected final void deleteByIdInternal( final long id ){
		try{
			getService().delete( id );
		}
		catch( final InvalidDataAccessApiUsageException dataEx ){
			logger.error( "InvalidDataAccessApiUsageException on delete operation" );
			logger.warn( "InvalidDataAccessApiUsageException on delete operation", dataEx );
			throw new ResourceNotFoundException( dataEx );
		}
		catch( final DataAccessException dataEx ){
			logger.error( "DataAccessException on delete operation" );
			logger.warn( "DataAccessException on delete operation", dataEx );
		}
	}
	
}
