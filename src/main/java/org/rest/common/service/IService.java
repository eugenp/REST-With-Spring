package org.rest.common.service;

import java.io.Serializable;

import org.rest.common.dao.ICommonOperations;

/**
 * @author eugenp
 */
public interface IService< T extends Serializable > extends ICommonOperations< T >{
	
	// get
	
	// create
	
	// delete
	
	void deleteById( final Long id );
	
}
