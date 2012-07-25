package org.rest.web.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rest.common.IEntity;
import org.springframework.web.util.UriComponentsBuilder;

public interface ISortingController< T extends IEntity >{
	
	public List< T > findPaginatedAndSorted( final int page, final int size, final String sortBy, final String sortOrder, final UriComponentsBuilder uriBuilder, final HttpServletResponse response );
	public List< T > findPaginated( final int page, final int size, final UriComponentsBuilder uriBuilder, final HttpServletResponse response );
	public List< T > findSorted( final String sortBy, final String sortOrder );
	public List< T > findAll( final HttpServletRequest request );
	
}
