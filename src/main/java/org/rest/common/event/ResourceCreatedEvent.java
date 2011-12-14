package org.rest.common.event;

import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.base.Preconditions;

public final class ResourceCreatedEvent< T extends Serializable > extends ApplicationEvent{
	private final long idOfNewResource;
	private final HttpServletResponse response;
	private final UriComponentsBuilder uriBuilder;
	
	public ResourceCreatedEvent( final Class< T > clazz, final UriComponentsBuilder uriBuilderToSet, final HttpServletResponse responseToSet, final long idOfNewResourceToSet ){
		super( clazz );
		
		Preconditions.checkNotNull( uriBuilderToSet );
		Preconditions.checkNotNull( responseToSet );
		Preconditions.checkNotNull( idOfNewResourceToSet );

		this.uriBuilder = uriBuilderToSet;
		this.response = responseToSet;
		this.idOfNewResource = idOfNewResourceToSet;
	}
	
	//
	
	public final UriComponentsBuilder getUriBuilder(){
		return this.uriBuilder;
	}
	public final HttpServletResponse getResponse(){
		return this.response;
	}
	public final long getIdOfNewResource(){
		return this.idOfNewResource;
	}
	@SuppressWarnings( "unchecked" )
	public final Class< T > getClazz(){
		return (Class< T >) this.getSource();
	}
	
}
