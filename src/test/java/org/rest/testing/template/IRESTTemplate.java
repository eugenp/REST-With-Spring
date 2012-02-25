package org.rest.testing.template;

import org.rest.common.IEntity;
import org.rest.testing.marshaller.IMarshaller;

import com.jayway.restassured.specification.RequestSpecification;

public interface IRESTTemplate< T extends IEntity > extends ITemplateAsResponse< T >, IRestDao< T >, ITemplateAsURI< T >, IEntityOperations< T >{
	
	String getURI();
	
	// authentication
	
	RequestSpecification givenAuthenticated();
	IMarshaller getMarshaller();
	
}
