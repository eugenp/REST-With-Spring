package org.rest.persistence.service;

import java.util.List;

import org.rest.common.IEntity;
import org.springframework.data.domain.Page;

public interface IService< T extends IEntity >{
	
	// find/get
	
	T findOne( final long id );
	
	List< T > findAll();
	
	Page< T > findPaginated( final int page, final int size, final String sortBy );
	
	// save/create/persist
	
	T create( final T entity );
	
	// update/merge
	
	void update( final T entity );
	
	// delete
	
	void delete( final long id );
	
	void delete( final List< T > entities );
	
	void deleteAll();
	
}
