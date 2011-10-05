package org.rest.test.integration.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.common.base.Preconditions;

public final class MarshallingUtil{
	
	private MarshallingUtil(){
		throw new AssertionError();
	}
	
	// API - public
	
	public static long extractIdFromCreateResponse( final HttpResponse createResponse ) throws IOException{
		Preconditions.checkNotNull( createResponse );
		
		final InputStream contentFromServer = createResponse.getEntity().getContent();
		
		return Long.parseLong( IOUtils.toString( contentFromServer ) );
	}
	
	public static HttpEntityEnclosingRequest decorateJsonRequest( final HttpEntityEnclosingRequest request, final String json ) throws UnsupportedEncodingException{
		Preconditions.checkNotNull( request );
		Preconditions.checkNotNull( json );
		
		request.setHeader( HttpConstants.CONTENT_TYPE_HEADER, "application/json" );
		request.setEntity( new StringEntity( json ) );
		
		return request;
	}
	
	public static void decorateRequestWithAuthHeaders( final HttpRequest httpRequest, final String sessionId ){
		Preconditions.checkNotNull( httpRequest );
		Preconditions.checkNotNull( sessionId );
		
		httpRequest.setHeader( HttpConstants.COOKIE_HEADER, sessionId );
	}
	
	public static String convertResourceToJson( final Serializable entity ) throws IOException{
		Preconditions.checkNotNull( entity );
		
		final ObjectMapper jsonMapper = new ObjectMapper();
		return jsonMapper.writeValueAsString( entity );
	}
	
	public static String convertToJson( final Object value ) throws IOException{
		Preconditions.checkNotNull( value );
		
		final ObjectMapper jsonMapper = new ObjectMapper();
		return jsonMapper.writeValueAsString( value );
	}
	
	public static < T >T convertJsonToObject( final String json, final Class< T > clazz ) throws IOException{
		Preconditions.checkNotNull( json );
		Preconditions.checkNotNull( clazz );
		
		final ObjectMapper jsonMapper = new ObjectMapper();
		return jsonMapper.readValue( json, clazz );
	}
	
	public static String retrieveJsonFromResponse( final HttpResponse response ) throws IOException{
		Preconditions.checkNotNull( response );
		
		final InputStream contentFromServer = response.getEntity().getContent();
		
		return IOUtils.toString( contentFromServer );
	}
	
	public static < T >T retrieveResourceFromResponse( final HttpResponse response, final Class< T > clazz ) throws IOException{
		final String jsonFromResponse = retrieveJsonFromResponse( response );
		return convertJsonToObject( jsonFromResponse, clazz );
	}
	
}
