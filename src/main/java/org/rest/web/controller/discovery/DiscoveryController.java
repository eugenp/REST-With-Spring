package org.rest.web.controller.discovery;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rest.common.util.HttpConstants;
import org.rest.common.util.RESTURIUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriTemplate;

/**
 * @author eugenp
 */
@Controller
final class DiscoveryController{
	
	// API
	
	@RequestMapping( value = "admin",method = RequestMethod.GET )
	@ResponseStatus( value = HttpStatus.NO_CONTENT )
	public final void adminRoot( final HttpServletRequest request, final HttpServletResponse response ){
		final String rootUri = request.getRequestURL().toString();
		
		final URI fooUri = new UriTemplate( "{rootUri}/{resource}" ).expand( rootUri, "foo" );
		final String linkToFoo = RESTURIUtil.createLinkHeader( fooUri.toASCIIString(), RESTURIUtil.REL_COLLECTION );
		final String linkToTest = RESTURIUtil.createLinkHeader( "test", RESTURIUtil.REL_COLLECTION );
		
		response.addHeader( HttpConstants.LINK_HEADER, RESTURIUtil.gatherLinkHeaders( linkToFoo, linkToTest ) );
	}
	
}
