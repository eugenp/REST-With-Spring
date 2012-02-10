package org.rest.web.event;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHeaders;
import org.rest.common.event.ResourceCreatedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.base.Preconditions;

@Component
@SuppressWarnings( "rawtypes" )
final class ResourceCreatedDiscoverabilityListener implements ApplicationListener< ResourceCreatedEvent >{
	
	@Override
	public final void onApplicationEvent( final ResourceCreatedEvent ev ){
		Preconditions.checkNotNull( ev );
		
		final long idOfNewResource = ev.getIdOfNewResource();
		addLinkHeaderOnEntityCreation( ev.getUriBuilder(), ev.getResponse(), idOfNewResource, ev.getClazz() );
	}
	
	final void addLinkHeaderOnEntityCreation( final UriComponentsBuilder uriBuilder, final HttpServletResponse response, final long idOfNewEntity, final Class clazz ){
		final String resourceName = clazz.getSimpleName().toString().toLowerCase();
		final String locationValue = uriBuilder.path( "/" + resourceName + "/{id}" ).build().expand( idOfNewEntity ).encode().toUriString();
		
		response.setHeader( HttpHeaders.LOCATION, locationValue );
	}
	
}
