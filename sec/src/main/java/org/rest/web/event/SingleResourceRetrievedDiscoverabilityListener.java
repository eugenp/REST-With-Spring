package org.rest.web.event;

import javax.servlet.http.HttpServletResponse;

import org.rest.common.event.SingleResourceRetrievedEvent;
import org.rest.common.util.RESTURIUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;

@SuppressWarnings( "rawtypes" )
@Component
final class SingleResourceRetrievedDiscoverabilityListener implements ApplicationListener< SingleResourceRetrievedEvent >{
	
	@Override
	public final void onApplicationEvent( final SingleResourceRetrievedEvent ev ){
		Preconditions.checkNotNull( ev );
		
		discoverGetAllURI( ev.getUriBuilder(), ev.getResponse(), ev.getClazz() );
	}
	
	final void discoverGetAllURI( final UriComponentsBuilder uriBuilder, final HttpServletResponse response, final Class clazz ){
		final String resourceName = clazz.getSimpleName().toString().toLowerCase();
		final String uriForResourceCreation = uriBuilder.path( "/" + resourceName ).build().encode().toUriString();
		
		final String linkHeaderValue = RESTURIUtil.createLinkHeader( uriForResourceCreation, RESTURIUtil.REL_COLLECTION );
		response.addHeader( HttpHeaders.LINK, linkHeaderValue );
	}
	
}
