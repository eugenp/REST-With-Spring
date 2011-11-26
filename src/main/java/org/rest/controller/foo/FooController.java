package org.rest.controller.foo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rest.common.event.ResourceCreated;
import org.rest.common.event.SingleResourceRetrieved;
import org.rest.common.util.RestPreconditions;
import org.rest.model.Foo;
import org.rest.service.foo.IFooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author eugenp
 */
@Controller
final class FooController{
	
	@Autowired
	IFooService service;
	
	@Autowired
	ApplicationEventPublisher eventPublisher;
	
	public FooController(){
		super();
	}
	
	// API
	
	@RequestMapping( value = "admin/foo",method = RequestMethod.GET )
	@ResponseBody
	public final List< Foo > getAll(){
		return this.service.getAll();
	}
	
	@RequestMapping( value = "admin/foo/{id}",method = RequestMethod.GET )
	@ResponseBody
	public final Foo get( @PathVariable( "id" ) final Long id, final HttpServletRequest request, final HttpServletResponse response ){
		final Foo resourceById = RestPreconditions.checkNotNull( this.service.getById( id ) );
		
		this.eventPublisher.publishEvent( new SingleResourceRetrieved( this, request, response ) );
		return resourceById;
	}
	
	@RequestMapping( value = "admin/foo",method = RequestMethod.POST )
	@ResponseStatus( HttpStatus.CREATED )
	public final void create( @RequestBody final Foo resource, final HttpServletRequest request, final HttpServletResponse response ){
		RestPreconditions.checkRequestElementNotNull( resource );
		final Long idOfCreatedResource = this.service.create( resource );
		
		this.eventPublisher.publishEvent( new ResourceCreated( this, request, response, idOfCreatedResource ) );
	}
	
	@RequestMapping( value = "admin/foo",method = RequestMethod.PUT )
	@ResponseStatus( HttpStatus.OK )
	public final void update( @RequestBody final Foo resource ){
		RestPreconditions.checkRequestElementNotNull( resource );
		RestPreconditions.checkNotNull( this.service.getById( resource.getId() ) );
		this.service.update( resource );
	}
	
	@RequestMapping( value = "admin/foo/{id}",method = RequestMethod.DELETE )
	@ResponseStatus( HttpStatus.NO_CONTENT )
	public final void delete( @PathVariable( "id" ) final Long id ){
		this.service.deleteById( id );
	}
	
}
