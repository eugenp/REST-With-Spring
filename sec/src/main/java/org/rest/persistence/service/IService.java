package org.rest.persistence.service;

import org.apache.commons.lang3.tuple.Triple;
import org.rest.common.ClientOperation;
import org.rest.common.IEntity;
import org.rest.common.IOperations;
import org.springframework.data.domain.Page;

public interface IService< T extends IEntity > extends IOperations< T >{
	
	// search
	
	Page< T > searchPaged( final int page, final int size, final Triple< String, ClientOperation, String >... constraints );
	
	Page< T > findAllPaginatedAndSorted( final int page, final int size, final String sortBy, final String sortOrder );
	
}
