package org.rest.client.template;

import org.apache.commons.lang3.tuple.Pair;
import org.rest.client.template.impl.ClientOperations;
import org.rest.common.IEntity;

import com.jayway.restassured.response.Response;

public interface ITemplateAsResponse< T extends IEntity >{
	
	// find - one
	
	Response findOneAsResponse( final String uriOfResource ); // 19
	Response findOneAsResponse( final String uriOfResource, final String acceptMime ); // 5
	
	// find - all
	
	Response findAllAsResponse(); // 1
	
	// search
	
	Response searchAsResponse( final Pair< Long, ClientOperations > idOp, final Pair< String, ClientOperations > nameOp );
	/**
	 * This will make the following assumptions: the operator is positive equals <br>
	 * For more flexible operators (such as negation), see {@link ITemplateAsResponse#searchAsResponse(Pair, Pair)}
	 */
	Response searchAsResponse( final Long id, final String name );
	
	// create
	
	Response createAsResponse( final T resource ); // 14
	
	// update
	
	Response updateAsResponse( final T resource ); // 6
	
	// delete
	
	Response deleteAsResponse( final String uriOfResource ); // 5
	
}
