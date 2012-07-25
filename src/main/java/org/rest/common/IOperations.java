package org.rest.common;

import java.util.List;

public interface IOperations< T extends IEntity >{
	
	// get
	
	T findOne( final long id );
	
	List< T > findAll();
	List< T > findAll( final String sortBy, final String sortOrder );
	
	// create
	
	T create( final T resource );
	
	// update
	
	void update( final T resource );
	
	// delete
	
	void delete( final long id );
	
	void deleteAll();
	
	// count
	
	long count();

}
