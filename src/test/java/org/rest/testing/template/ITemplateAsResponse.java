package org.rest.testing.template;

import org.rest.common.IEntity;

import com.jayway.restassured.response.Response;

public interface ITemplateAsResponse< T extends IEntity >{
	
	// find - one
	
	Response findOneAsResponse( final String uriOfResource ); // 19
	Response findOneAsResponse( final String uriOfResource, final String acceptMime ); // 5
	
	// find - all
	
	Response findAllAsResponse(); // 1
	
	// create
	
	Response createAsResponse( final T resource ); // 14
	
	// update
	
	Response updateAsResponse( final T resource ); // 6

	// delete
	
	Response deleteAsResponse( final String uriOfResource ); // 5
	
}
