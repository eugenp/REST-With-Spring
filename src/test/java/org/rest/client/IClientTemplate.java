package org.rest.client;

import java.util.List;

import org.rest.common.IEntity;
import org.rest.common.IOperations;

public interface IClientTemplate< T extends IEntity > extends IOperations< T >{
	
	IClientTemplate< T > givenAuthenticated();
	T findOneByAttributes( final String... attributes );
	List< T > findAllByAttributes( final String... attributes );
	T findOneByName( final String name );
	T findOneByURI( final String uri );
	String createAsURI( final T resource );
	
}
