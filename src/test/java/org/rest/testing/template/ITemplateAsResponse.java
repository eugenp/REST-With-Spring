package org.rest.testing.template;

import org.rest.common.IEntity;

import com.jayway.restassured.response.Response;

public interface ITemplateAsResponse< T extends IEntity >{
	
	// get
	
	Response getResourceAsResponse( final String uriOfResource );
	Response getResourceAsResponse( final String uriOfResource, final String acceptMime );
	
	// create
	
	Response createResourceAsResponse();
	Response createResourceAsResponse( final T resource );
	
	// create and get
	
	Response createResourceAndGetAsResponse();
	Response createResourceAndGetAsResponse( final T resource );
	
	// update
	
	Response updateResourceAsResponse( final T resource );
	Response updateResourceAsResponse( final String resourceAsMime );
	
	// delete
	
	Response delete( final String uriOfResource );
	
}
