package org.rest.controller.foo;

import java.util.List;

import org.rest.common.util.RestPreconditions;
import org.rest.model.Foo;
import org.rest.service.foo.IFooService;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	public FooController(){
		super();
	}
	
	// API
	
	@RequestMapping( value = "foo",method = RequestMethod.GET )
	@ResponseBody
	public final List< Foo > getAll(){
		return this.service.getAll();
	}
	
	@RequestMapping( value = "foo/{id}",method = RequestMethod.GET )
	@ResponseBody
	public final Foo get( @PathVariable( "id" ) final Long id ){
		return RestPreconditions.checkNotNull( this.service.getById( id ) );
	}
	
	@RequestMapping( value = "foo",method = RequestMethod.POST )
	@ResponseBody
	@ResponseStatus( HttpStatus.CREATED )
	public final Long create( @RequestBody final Foo entity ){
		RestPreconditions.checkNotNullFromRequest( entity );
		return this.service.create( entity );
	}
	
	@RequestMapping( value = "foo",method = RequestMethod.PUT )
	@ResponseStatus( HttpStatus.OK )
	public final void update( @RequestBody final Foo entity ){
		RestPreconditions.checkNotNullFromRequest( entity );
		RestPreconditions.checkNotNull( this.service.getById( entity.getId() ) );
		this.service.update( entity );
	}
	
	@RequestMapping( value = "foo/{id}",method = RequestMethod.DELETE )
	@ResponseStatus( HttpStatus.OK )
	public final void delete( @PathVariable( "id" ) final Long id ){
		this.service.deleteById( id );
	}
	
}
