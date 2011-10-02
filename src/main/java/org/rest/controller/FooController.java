package org.rest.controller;

import org.rest.service.IFooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author eugenp
 */
@Controller
final class FooController{
	
	@Autowired
	IFooService fooService;
	
	public FooController(){
		super();
	}
	
	// API
	@RequestMapping( value = "/foo",method = RequestMethod.GET )
	public final void get( @RequestParam final Long id ){
		this.fooService.getById( id );
	}
	
}
