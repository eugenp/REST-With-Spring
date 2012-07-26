package org.rest.sec.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rest.common.exceptions.ConflictException;
import org.rest.common.util.QueryUtil;
import org.rest.common.web.RestPreconditions;
import org.rest.sec.model.dto.User;
import org.rest.sec.persistence.service.dto.IUserService;
import org.rest.sec.util.SecurityConstants;
import org.rest.util.SearchCommonUtil;
import org.rest.web.common.AbstractController;
import org.rest.web.common.ISortingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
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
@RequestMapping( value = "user" )
public class UserController extends AbstractController< User > implements ISortingController< User >{
	
	@Autowired private IUserService service;
	
	public UserController(){
		super( User.class );
	}
	
	// API
	
	// search
	
	@RequestMapping( params = { SearchCommonUtil.Q_PARAM },method = RequestMethod.GET )
	@ResponseBody
	public List< User > search( @RequestParam( SearchCommonUtil.Q_PARAM ) final String queryString ){
		return searchInternal( queryString );
	}
	
	// find - all/paginated
	
	@Override
	@RequestMapping( params = { QueryUtil.PAGE, QueryUtil.SIZE, QueryUtil.SORT_BY },method = RequestMethod.GET )
	@ResponseBody
	public List< User > findAllPaginatedAndSorted( @RequestParam( value = QueryUtil.PAGE ) final int page, @RequestParam( value = QueryUtil.SIZE ) final int size, @RequestParam( value = QueryUtil.SORT_BY ) final String sortBy, @RequestParam( value = QueryUtil.SORT_ORDER ) final String sortOrder, final UriComponentsBuilder uriBuilder, final HttpServletResponse response ){
		return findPaginatedAndSortedInternal( page, size, sortBy, sortOrder, uriBuilder, response );
	}
	@Override
	@RequestMapping( params = { QueryUtil.PAGE, QueryUtil.SIZE },method = RequestMethod.GET )
	@ResponseBody
	public List< User > findAllPaginated( @RequestParam( value = QueryUtil.PAGE ) final int page, @RequestParam( value = QueryUtil.SIZE ) final int size, final UriComponentsBuilder uriBuilder, final HttpServletResponse response ){
		return findPaginatedAndSortedInternal( page, size, null, null, uriBuilder, response );
	}
	@Override
	@RequestMapping( params = { QueryUtil.SORT_BY },method = RequestMethod.GET )
	@ResponseBody
	public List< User > findAllSorted( @RequestParam( value = QueryUtil.SORT_BY ) final String sortBy, @RequestParam( value = QueryUtil.SORT_ORDER ) final String sortOrder ){
		return findAllSortedInternal( sortBy, sortOrder );
	}
	
	@Override
	@RequestMapping( method = RequestMethod.GET )
	@ResponseBody
	public List< User > findAll( final HttpServletRequest request ){
		return findAllInternal( request );
	}
	
	// find - one
	
	@RequestMapping( value = "/{id}",method = RequestMethod.GET )
	@ResponseBody
	public User findOne( @PathVariable( "id" ) final Long id, final UriComponentsBuilder uriBuilder, final HttpServletResponse response ){
		return findOneInternal( id, uriBuilder, response );
	}
	
	@RequestMapping( params = "name",method = RequestMethod.GET )
	@ResponseBody
	public User findOneByName( @RequestParam( "name" ) final String name ){
		User resource = null;
		try{
			resource = RestPreconditions.checkNotNull( getService().findByName( name ) );
		}
		catch( final InvalidDataAccessApiUsageException ex ){
			logger.error( "InvalidDataAccessApiUsageException on find operation" );
			logger.warn( "InvalidDataAccessApiUsageException on find operation", ex );
			throw new ConflictException( ex );
		}
		
		return resource;
	}
	
	// create
	
	@RequestMapping( method = RequestMethod.POST )
	@ResponseStatus( HttpStatus.CREATED )
	public void create( @RequestBody final User resource, final UriComponentsBuilder uriBuilder, final HttpServletResponse response ){
		createInternal( resource, uriBuilder, response );
	}
	
	// update
	
	@RequestMapping( method = RequestMethod.PUT )
	@ResponseStatus( HttpStatus.OK )
	@Secured( SecurityConstants.CAN_USER_WRITE )
	public void update( @RequestBody final User resource ){
		updateInternal( resource );
	}
	
	// delete
	
	@RequestMapping( value = "/{id}",method = RequestMethod.DELETE )
	@ResponseStatus( HttpStatus.NO_CONTENT )
	@Secured( SecurityConstants.CAN_USER_WRITE )
	public void delete( @PathVariable( "id" ) final Long id ){
		deleteByIdInternal( id );
	}
	
	// Spring
	
	@Override
	protected final IUserService getService(){
		return service;
	}
	
}
