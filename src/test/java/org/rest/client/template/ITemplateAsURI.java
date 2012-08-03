package org.rest.client.template;

import org.rest.common.IEntity;

public interface ITemplateAsURI< T extends IEntity >{
	
	// create
	
	String createAsURI( final T resource ); // 8
	
}
