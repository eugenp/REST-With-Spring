package org.rest.common.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author eugenp
 */
public interface ICommonOperations< T extends Serializable >{
	
	// get
	
	T getById( final Long id );
	T getByIdExperimental( final Long id );
	
	List< T > getAll();
	
	// create
	
	Long create( final T entity );
	
	// update
	
	void update( final T entity );
	
	// delete
	
	void delete( final T entity );
	
}
