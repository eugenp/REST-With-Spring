package org.rest.sec.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.rest.persistence.service.IService;
import org.rest.sec.model.Privilege;
import org.rest.sec.persistence.service.privilege.IPrivilegeService;
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
public class PrivilegeController extends AbstractController< Privilege >{
	
	@Autowired
	private IPrivilegeService service;
	
	public PrivilegeController(){
		super( Privilege.class );
	}
	
	// API
	
	// find
	
	@RequestMapping( value = "admin/privilege",params = { "page", "size" },method = GET )
	@ResponseBody
	public List< Privilege > findPaginated( @RequestParam( "page" ) final int page, @RequestParam( "size" ) final int size, final UriComponentsBuilder uriBuilder, final HttpServletResponse response ){
		return findPaginatedInternal( page, size, uriBuilder, response );
	}
	
	@RequestMapping( value = "admin/privilege",method = RequestMethod.GET )
	@ResponseBody
	public List< Privilege > findAll(){
		return findAllInternal();
	}
	
	@RequestMapping( value = "admin/privilege/{id}",method = GET )
	@ResponseBody
	public Privilege findOne( @PathVariable( "id" ) final Long id, final UriComponentsBuilder uriBuilder, final HttpServletResponse response ){
		return findOneInternal( id, uriBuilder, response );
	}
	
	// create
	
	@RequestMapping( value = "admin/privilege",method = POST )
	@ResponseStatus( HttpStatus.CREATED )
	public void create( @RequestBody final Privilege resource, final UriComponentsBuilder uriBuilder, final HttpServletResponse response ){
		saveInternal( resource, uriBuilder, response );
	}
	
	// update
	
	@RequestMapping( value = "admin/privilege",method = PUT )
	@ResponseStatus( HttpStatus.OK )
	public void update( @RequestBody final Privilege resource ){
		updateInternal( resource );
	}
	
	// delete
	
	@RequestMapping( value = "admin/privilege/{id}",method = DELETE )
	@ResponseStatus( HttpStatus.NO_CONTENT )
	public void delete( @PathVariable( "id" ) final Long id ){
		deleteByIdInternal( id );
	}
	
	//
	
	@Override
	protected IService< Privilege > getService(){
		return service;
	}
	
}
