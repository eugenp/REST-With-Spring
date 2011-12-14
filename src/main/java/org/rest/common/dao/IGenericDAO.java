package org.rest.common.dao;

import java.io.Serializable;


public interface IGenericDAO< T extends Serializable > extends ICommonOperations< T >{
	
	void setClazz( final Class< T > clazzToSet );

}
