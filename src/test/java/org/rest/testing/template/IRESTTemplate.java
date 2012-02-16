package org.rest.testing.template;

import org.rest.common.IEntity;
import org.rest.testing.marshaller.IMarshaller;

import com.jayway.restassured.specification.RequestSpecification;

public interface IRESTTemplate< T extends IEntity > extends ITemplateAsResponse< T >, IRestDao< T >, ITemplateAsEntity< T >, ITemplateAsURI< T >{
	
	// new entity
	
	T createNewEntity();
	void makeEntityInvalid( final T entity );
	
	// get
	
	String getResourceAsMime( final String uriOfResource, final String mime );
	
	// create and get
	
	String createResourceAndGetAsMime( final String mime );
	
	// URI
	
	String getURI();
	
	// authentication
	
	RequestSpecification givenAuthenticated();
	IMarshaller getMarshaller();
	
}
