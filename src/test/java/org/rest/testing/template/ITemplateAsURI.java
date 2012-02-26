package org.rest.testing.template;

import org.rest.common.IEntity;

public interface ITemplateAsURI< T extends IEntity >{
	
	// create
	
	String createResourceAsURI( final T resource ); // 8
	
}
