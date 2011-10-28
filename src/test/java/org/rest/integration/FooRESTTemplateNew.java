package org.rest.integration;

import static com.jayway.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.rest.constants.HttpConstants;
import org.rest.model.Foo;
import org.rest.util.common.ExtractUtil;
import org.rest.util.json.ConvertUtil;
import org.rest.util.json.DecorateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

/**
 * Template for the consumption of the REST API <br>
 */
@Component
public final class FooRESTTemplateNew{
	
	@Autowired
	ExamplePaths paths;
	
	public FooRESTTemplateNew(){
		super();
	}
	
	//
	public final long createResource() throws ClientProtocolException, IOException{
		return this.createResource( new Foo( randomAlphabetic( 6 ) ) );
	}
	public final long createResource( final Foo resource ) throws ClientProtocolException, IOException{
		Preconditions.checkNotNull( resource );
		
		final HttpPost request = new HttpPost( this.paths.getFooURL() );
		DecorateUtil.setResourceOnRequestAsJson( request, resource );
		
		final HttpResponse response = new DefaultHttpClient().execute( request );
		
		final long idOfNewResource = ExtractUtil.extractIdFromCreateResponse( response );
		return idOfNewResource;
	}
	
	/**
	 * - note: usually, the
	 */
	public final String getResourceAsJson( final long id ){
		return given().header( HttpHeaders.ACCEPT, HttpConstants.MIME_JSON ).get( this.paths.getFooURL() + "/" + id ).asString();
	}
	public final < T >T getResourceViaJson( final long id, final Class< T > clazz ) throws ClientProtocolException, IOException{
		final String resourceAsJson = this.getResourceAsJson( id );
		return ConvertUtil.convertJsonToResource( resourceAsJson, clazz );
	}
	
}
