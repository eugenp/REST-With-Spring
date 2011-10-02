package org.rest.common.dao;

import java.io.Serializable;

/**
 * @author eugenp
 */
public interface ICommonOperations< T extends Serializable >{
	
	T getById( final Long id );
	T getByIdExperimental( final Long id );
	
}
