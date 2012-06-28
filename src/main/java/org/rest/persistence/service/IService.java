package org.rest.persistence.service;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.rest.common.ClientOperation;
import org.rest.common.IEntity;
import org.rest.common.IOperations;
import org.springframework.data.domain.Page;

public interface IService< T extends IEntity > extends IOperations< T >{
	
	// search
	
	List< T > search( final ImmutableTriple< String, ClientOperation, String >... constraints );
	Page< T > searchPaged( final int page, final int size, final String sortBy, final ImmutableTriple< String, ClientOperation, String >... constraints );
	
	// find - all
	
	Page< T > findPaginated( final int page, final int size, final String sortBy );
	
}
