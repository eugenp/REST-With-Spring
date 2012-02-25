package org.rest.testing.template;

import org.rest.common.IEntity;

import com.jayway.restassured.response.Response;

public interface ITemplateAsResponse< T extends IEntity >{
	
	// find - one
	
	Response findOneAsResponse( final String uriOfResource );
	Response findOneAsResponse( final String uriOfResource, final String acceptMime );
	
	// find - all
	
	Response findAllAsResponse();

	// create
	
	Response createResourceAsResponse();
	Response createResourceAsResponse( final T resource );
	
	// create and get
	
	Response createResourceAndGetAsResponse();
	Response createResourceAndGetAsResponse( final T resource );
	
	// update
	
	Response updateAsResponse( final T resource );
	Response updateAsResponse( final String resourceAsMime );
	
	// delete
	
	Response delete( final String uriOfResource );
	
}
