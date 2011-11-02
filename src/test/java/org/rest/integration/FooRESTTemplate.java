package org.rest.integration;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.rest.constants.HttpConstants;
import org.rest.integration.security.SecurityComponent;
import org.rest.model.Foo;
import org.rest.util.common.ExtractUtil;
import org.rest.util.common.SecurityUtil;
import org.rest.util.json.ConvertUtil;
import org.rest.util.json.DecorateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

/**
 * Template for the consumption of the REST API <br>
 */
@Component
public final class FooRESTTemplate{
	
	@Autowired
	ExamplePaths paths;
	
	@Autowired
	SecurityComponent securityComponent;
	
	public FooRESTTemplate(){
		super();
	}
	
	//
	public final long createResource() throws ClientProtocolException, IOException{
		return this.createResource( new Foo( randomAlphabetic( 6 ) ) );
	}
	public final long createResource( final Foo resource ) throws ClientProtocolException, IOException{
		Preconditions.checkNotNull( resource );
		
		final String cookie = this.securityComponent.authenticateAsAdmin();
		
		final HttpPost request = new HttpPost( this.paths.getFooURL() );
		SecurityUtil.decorateRequestWithAuthHeaders( request, cookie );
		DecorateUtil.setResourceOnRequestAsJson( request, resource );
		
		final HttpResponse response = new DefaultHttpClient().execute( request );
		
		final long idOfNewResource = ExtractUtil.extractIdFromCreateResponse( response );
		return idOfNewResource;
	}
	
	public final < T >T getResourceViaJson( final long id, final Class< T > clazz, final String cookie ) throws ClientProtocolException, IOException{
		final String resourceAsJson = this.getResourceAsJson( id, cookie );
		return ConvertUtil.convertJsonToResource( resourceAsJson, clazz );
	}
	public final String getResourceAsJson( final long id, final String cookie ){
		return this.securityComponent.givenAuthenticated( cookie ).header( HttpHeaders.ACCEPT, HttpConstants.MIME_JSON ).get( this.paths.getFooURL() + "/" + id ).asString();
	}
	
}
