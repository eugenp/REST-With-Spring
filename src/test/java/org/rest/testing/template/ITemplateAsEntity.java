package org.rest.testing.template;

import org.rest.common.IEntity;

public interface ITemplateAsEntity< T extends IEntity >{
	
	// get
	
	T getResourceAsEntity( final String uriOfResource );
	
	// create and get
	
	T createResourceAndGetAsEntity();
	
	T createResourceAndGetAsEntity( final T resource );
	
	// update
	
	T updateResourceAndGetAsEntity( final T resource );
	
}
