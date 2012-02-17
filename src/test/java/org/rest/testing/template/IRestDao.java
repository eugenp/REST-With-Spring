package org.rest.testing.template;

import java.util.List;

import org.rest.common.IEntity;

public interface IRestDao< T extends IEntity >{
	
	// get
	
	T findOne( final long id );
	List< T > findAll();
	
	// create
	
	T create( final T resource );
	
	// update
	
	void update( final T resource );

	// delete
	
	void delete( final long id );
	
}
