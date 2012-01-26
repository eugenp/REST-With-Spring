package org.rest.poc.web.controller.foo;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.rest.common.event.PaginatedResultsRetrievedEvent;
import org.rest.common.exceptions.ResourceNotFoundException;
import org.rest.persistence.service.IService;
import org.rest.poc.model.Foo;
import org.rest.poc.persistence.service.foo.IFooService;
import org.rest.web.common.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class FooController extends AbstractController< Foo >{
	
	@Autowired
	private IFooService service;
	
	public FooController(){
		super( Foo.class );
	}
	
	// API
	
	// find
	
	@RequestMapping( value = "admin/foo",params = { "page", "size" },method = GET )
	@ResponseBody
	public List< Foo > findPaginated( @RequestParam( "page" ) final int page, @RequestParam( "size" ) final int size, final UriComponentsBuilder uriBuilder, final HttpServletResponse response ){
		final Page< Foo > resultPage = service.findPaginated( page, size );
		if( page > resultPage.getTotalPages() ){
			throw new ResourceNotFoundException();
		}
		eventPublisher.publishEvent( new PaginatedResultsRetrievedEvent< Foo >( Foo.class, uriBuilder, response, page, resultPage.getTotalPages(), size ) );
		
		return resultPage.getContent();
	}
	
	@RequestMapping( value = "admin/foo",method = GET )
	@ResponseBody
	public List< Foo > findAll(){
		List< Foo > allResources = null;
		try{
			allResources = findAllInternal();
		}
		catch( final Throwable ex ){
			logger.error( "", ex );
		}
		return allResources;
	}

	@RequestMapping( value = "admin/foo/{id}",method = GET )
	@ResponseBody
	public Foo findOne( @PathVariable( "id" ) final Long id, final UriComponentsBuilder uriBuilder, final HttpServletResponse response ){
		return findOneInternal( id, uriBuilder, response );
	}
	
	// create
	
	@RequestMapping( value = "admin/foo",method = POST )
	@ResponseStatus( HttpStatus.CREATED )
	public void create( @RequestBody final Foo resource, final UriComponentsBuilder uriBuilder, final HttpServletResponse response ){
		saveInternal( resource, uriBuilder, response );
	}
	
	// update
	
	@RequestMapping( value = "admin/foo",method = PUT )
	@ResponseStatus( HttpStatus.OK )
	public void update( @RequestBody final Foo resource ){
		updateInternal( resource );
	}
	
	// delete
	
	@RequestMapping( value = "admin/foo/{id}",method = DELETE )
	@ResponseStatus( HttpStatus.NO_CONTENT )
	public void delete( @PathVariable( "id" ) final Long id ){
		deleteByIdInternal( id );
	}
	
	//
	
	@Override
	protected final IService< Foo > getService(){
		return service;
	}
	
}
