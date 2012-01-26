package org.rest.persistence.service;

import java.util.List;

import org.rest.common.IEntity;

public interface IService< T extends IEntity >{
	
	// find/get
	
	T findOne( final long id );
	
	List< T > findAll();
	
	// save/create/persist

	T save( final T entity );
	
	// update/merge
	
	void update( final T entity );
	
	// delete
	
	void delete( final long id );
	
	void delete( final List< T > entities );
	
	void deleteAll();
	
}
