package org.rest.testing.template;

import org.rest.common.IEntity;
import org.rest.testing.marshaller.IMarshaller;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public interface ITemplate< T extends IEntity >{
	
	// new entity
	
	T createNewEntity();
	void makeEntityInvalid( final T entity );
	
	// get
	
	T getResourceAsEntity( final String uriOfResource );
	
	String getResourceAsMime( final String uriOfResource, final String mime );
	
	Response getResourceAsResponse( final String uriOfResource );
	Response getResourceAsResponse( final String uriOfResource, final String acceptMime );
	
	// create
	
	String createResourceAsURI();
	
	String createResourceAsURI( final T resource );
	
	Response createResourceAsResponse();
	Response createResourceAsResponse( final T resource );
	
	// create and get
	
	String createResourceAndGetAsMime( final String mime );
	
	T createResourceAndGetAsEntity();
	
	T createResourceAndGetAsEntity( final T resource );
	
	Response createResourceAndGetAsResponse();
	Response createResourceAndGetAsResponse( final T resource );
	
	// update
	
	Response updateResourceAsResponse( final T resource );
	Response updateResourceAsResponse( final String resourceAsMime );
	
	T updateResourceAndGetAsEntity( final T resource );
	
	// delete
	
	Response delete( final String uriOfResource );
	
	// URI
	
	String getURI();
	
	// authentication
	
	RequestSpecification givenAuthenticated();
	IMarshaller getMarshaller();
	
}
