package org.rest.test.integration.common;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.rest.model.Hello;

import com.google.common.base.Preconditions;

public final class RESTTemplate{
	
	private RESTTemplate(){
		throw new AssertionError();
	}
	
	//
	public static long createHello() throws ClientProtocolException, IOException{
		return createHello( new Hello( randomAlphabetic( 6 ) ) );
	}
	public static long createHello( final Hello entity ) throws ClientProtocolException, IOException{
		Preconditions.checkNotNull( entity );
		
		final HttpPost request = new HttpPost( RESTPaths.HELLO_WORLD_PATH );
		final String localResourceAsJson = MarshallingUtil.convertResourceToJson( entity );
		MarshallingUtil.decorateJsonRequest( request, localResourceAsJson );
		
		final HttpResponse response = new DefaultHttpClient().execute( request );
		
		final long idOfNewEntity = MarshallingUtil.extractIdFromCreateResponse( response );
		return idOfNewEntity;
	}
	
	public static String getHello( final long id ) throws ClientProtocolException, IOException{
		final HttpUriRequest request = new HttpGet( RESTPaths.HELLO_WORLD_PATH + "/" + id );
		final HttpResponse response = new DefaultHttpClient().execute( request );
		final String jsonFromResponse = MarshallingUtil.retrieveJsonFromResponse( response );
		return jsonFromResponse;
	}
	
}
