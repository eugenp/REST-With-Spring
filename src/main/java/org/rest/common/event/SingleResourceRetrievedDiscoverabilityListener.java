package org.rest.common.event;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rest.common.util.HttpConstants;
import org.rest.common.util.RESTURIUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

@Component
final class SingleResourceRetrievedDiscoverabilityListener implements ApplicationListener< SingleResourceRetrieved >{
	
	@Override
	public final void onApplicationEvent( final SingleResourceRetrieved resourceRetrievedEvent ){
		Preconditions.checkNotNull( resourceRetrievedEvent );
		
		final HttpServletRequest request = resourceRetrievedEvent.getRequest();
		final HttpServletResponse response = resourceRetrievedEvent.getResponse();
		
		this.addLinkHeaderOnSingleResourceRetrieval( request, response );
	}
	
	final void addLinkHeaderOnSingleResourceRetrieval( final HttpServletRequest request, final HttpServletResponse response ){
		final StringBuffer requestURL = request.getRequestURL();
		final int positionOfLastSlash = requestURL.lastIndexOf( "/" );
		final String uriForEntityCreation = requestURL.substring( 0, positionOfLastSlash );
		
		final String linkHeaderValue = RESTURIUtil.createLinkHeader( uriForEntityCreation, RESTURIUtil.REL_COLLECTION );
		response.addHeader( HttpConstants.LINK_HEADER, linkHeaderValue );
	}
	
}
