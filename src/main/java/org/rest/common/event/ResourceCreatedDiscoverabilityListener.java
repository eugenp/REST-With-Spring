package org.rest.common.event;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHeaders;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriTemplate;

import com.google.common.base.Preconditions;

@Component
final class ResourceCreatedDiscoverabilityListener implements ApplicationListener< ResourceCreated >{
	
	@Override
	public final void onApplicationEvent( final ResourceCreated resourceCreatedEvent ){
		Preconditions.checkNotNull( resourceCreatedEvent );
		
		final HttpServletRequest request = resourceCreatedEvent.getRequest();
		final HttpServletResponse response = resourceCreatedEvent.getResponse();
		final long idOfNewResource = resourceCreatedEvent.getIdOfNewResource();
		
		this.addLinkHeaderOnEntityCreation( request, response, idOfNewResource );
	}
	
	final void addLinkHeaderOnEntityCreation( final HttpServletRequest request, final HttpServletResponse response, final long idOfNewEntity ){
		final String requestUrl = request.getRequestURL().toString();
		final URI uri = new UriTemplate( "{requestUrl}/{idOfNewEntity}" ).expand( requestUrl, idOfNewEntity );
		response.setHeader( HttpHeaders.LOCATION, uri.toASCIIString() );
	}
	
}
