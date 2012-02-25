package org.rest.testing.template;

import org.rest.common.IEntity;

public interface ITemplateAsEntity< T extends IEntity >{
	
	// get
	
	T getResourceAsEntity( final String uriOfResource );
	
}
