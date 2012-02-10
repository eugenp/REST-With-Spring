package org.rest.sec.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.rest.sec.model.Privilege;
import org.rest.sec.persistence.service.IPrivilegeService;
import org.rest.web.common.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping( value = "privilege" )
public class PrivilegeController extends AbstractController< Privilege >{
	
	@Autowired
	private IPrivilegeService service;
	
	public PrivilegeController(){
		super( Privilege.class );
	}
	
	// API
	
	// find - all/paginated
	
	@RequestMapping( params = { "page", "size"/*, "sortBy"*/},method = RequestMethod.GET )
	@ResponseBody
	public List< Privilege > findPaginated( @RequestParam( "page" ) final int page, @RequestParam( "size" ) final int size, @RequestParam( value = "sortBy",required = false ) final String sortBy, final UriComponentsBuilder uriBuilder, final HttpServletResponse response ){
		return findPaginatedInternal( page, size, sortBy, uriBuilder, response );
	}
	
	@RequestMapping( method = RequestMethod.GET )
	@ResponseBody
	public List< Privilege > findAll(){
		return findAllInternal();
	}
	
	// find - one
	
	@RequestMapping( value = "/{id}",method = RequestMethod.GET )
	@ResponseBody
	public Privilege findOne( @PathVariable( "id" ) final Long id, final UriComponentsBuilder uriBuilder, final HttpServletResponse response ){
		return findOneInternal( id, uriBuilder, response );
	}
	
	// create
	
	@RequestMapping( method = RequestMethod.POST )
	@ResponseStatus( HttpStatus.CREATED )
	public void create( @RequestBody final Privilege resource, final UriComponentsBuilder uriBuilder, final HttpServletResponse response ){
		saveInternal( resource, uriBuilder, response );
	}
	
	// update
	
	@RequestMapping( method = RequestMethod.PUT )
	@ResponseStatus( HttpStatus.OK )
	public void update( @RequestBody final Privilege resource ){
		updateInternal( resource );
	}
	
	// delete
	
	@RequestMapping( value = "/{id}",method = RequestMethod.DELETE )
	@ResponseStatus( HttpStatus.NO_CONTENT )
	public void delete( @PathVariable( "id" ) final Long id ){
		deleteByIdInternal( id );
	}
	
	// Spring
	
	@Override
	protected final IPrivilegeService getService(){
		return service;
	}
	
}
