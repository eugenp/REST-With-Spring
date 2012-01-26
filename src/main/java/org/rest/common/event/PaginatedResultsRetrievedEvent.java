package org.rest.common.event;

import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

public final class PaginatedResultsRetrievedEvent< T extends Serializable > extends ApplicationEvent{
	private final UriComponentsBuilder uriBuilder;
	private final HttpServletResponse response;
	private final int page;
	private final int totalPages;
	private final int pageSize;
	
	public PaginatedResultsRetrievedEvent( final Class< T > clazz, final UriComponentsBuilder uriBuilderToSet, final HttpServletResponse responseToSet, final int pageToSet, final int totalPagesToSet, final int pageSizeToSet ){
		super( clazz );

		this.uriBuilder = uriBuilderToSet;
		this.response = responseToSet;
		this.page = pageToSet;
		this.totalPages = totalPagesToSet;
		this.pageSize = pageSizeToSet;
	}
	
	// API
	
	public final UriComponentsBuilder getUriBuilder(){
		return this.uriBuilder;
	}
	public final HttpServletResponse getResponse(){
		return this.response;
	}
	public final int getPage(){
		return this.page;
	}
	public final int getTotalPages(){
		return this.totalPages;
	}
	public final int getPageSize(){
		return this.pageSize;
	}
	
	@SuppressWarnings( "unchecked" )
	public final Class< T > getClazz(){
		return (Class< T >) getSource();
	}
	
}
