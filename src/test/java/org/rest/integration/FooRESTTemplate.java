package org.rest.integration;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.rest.model.Foo;
import org.rest.util.common.ExtractUtil;
import org.rest.util.json.ConvertUtil;
import org.rest.util.json.DecorateUtil;
import org.rest.util.json.RetrieveUtil;
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
	
	public FooRESTTemplate(){
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
	public final String getResourceAsJson( final long id ) throws ClientProtocolException, IOException{
		final HttpUriRequest request = new HttpGet( this.paths.getFooURL() + "/" + id );
		final HttpResponse response = new DefaultHttpClient().execute( request );
		final String jsonFromResponse = RetrieveUtil.retrieveJsonFromResponse( response );
		return jsonFromResponse;
	}
	public final < T >T getResource( final long id, final Class< T > clazz ) throws ClientProtocolException, IOException{
		final String resourceAsJson = this.getResourceAsJson( id );
		return ConvertUtil.convertJsonToResource( resourceAsJson, clazz );
	}
	
}
