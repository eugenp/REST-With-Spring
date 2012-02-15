package org.rest.testing.template;

import org.rest.common.IEntity;

public interface IRestDao< T extends IEntity >{
	
	// get
	
	T findOne( final long id );
	
	// create
	
	T create( final T resource );

	// delete
	
	void delete( final long id );

}
