package org.rest.controller.hello;

import java.util.List;

import org.rest.common.util.RestPreconditions;
import org.rest.model.Hello;
import org.rest.service.hello.IHelloService;
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
final class HelloController{
	
	@Autowired
	IHelloService helloService;
	
	public HelloController(){
		super();
	}
	
	// API
	
	@RequestMapping( value = "helloWorld",method = RequestMethod.GET )
	@ResponseBody
	public final List< Hello > getAll(){
		return this.helloService.getAll();
	}
	
	@RequestMapping( value = "helloWorld/{id}",method = RequestMethod.GET )
	@ResponseBody
	public final Hello getById( @PathVariable( "id" ) final Long id ){
		return RestPreconditions.checkNotNull( this.helloService.getById( id ) );
	}
	
	@RequestMapping( value = "helloWorld",method = RequestMethod.POST )
	@ResponseStatus( HttpStatus.CREATED )
	@ResponseBody
	public final Long create( @RequestBody final Hello entity ){
		RestPreconditions.checkNotNullFromRequest( entity );
		return this.helloService.create( entity );
	}
	
	@RequestMapping( value = "helloWorld",method = RequestMethod.PUT )
	@ResponseStatus( HttpStatus.OK )
	public final void update( @RequestBody final Hello entity ){
		RestPreconditions.checkNotNullFromRequest( entity );
		this.helloService.update( entity );
	}
	
	@RequestMapping( value = "helloWorld/{id}",method = RequestMethod.DELETE )
	@ResponseStatus( HttpStatus.OK )
	public final void deleteById( @PathVariable( "id" ) final Long id ){
		this.helloService.deleteById( id );
	}
	
}
