package org.rest.testing.template;

import org.rest.common.IEntity;

public interface IEntityOperations< T extends IEntity >{
	
	T createNewEntity();

	void makeEntityInvalid( final T entity );

	void change( final T resource );
	
}
