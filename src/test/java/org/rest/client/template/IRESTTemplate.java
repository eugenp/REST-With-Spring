package org.rest.client.template;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.rest.client.marshall.IMarshaller;
import org.rest.common.ClientOperation;
import org.rest.common.IEntity;
import org.rest.common.IOperations;

import com.jayway.restassured.specification.RequestSpecification;

public interface IRESTTemplate< T extends IEntity > extends IOperations< T >, IEntityOperations< T >, ITemplateAsResponse< T >, ITemplateAsURI< T >{
	
	String getURI();
	
	// authentication
	
	RequestSpecification givenAuthenticated();
	
	IMarshaller getMarshaller();
	
	// search
	
	List< T > search( final Pair< Long, ClientOperation > idOp, final Pair< String, ClientOperation > nameOp );
	
	List< T > searchPaged( final Pair< Long, ClientOperation > idOp, final Pair< String, ClientOperation > nameOp, final int page, final int size );
	
}
