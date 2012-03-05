package org.rest.sec.persistence.service;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.rest.persistence.service.IService;
import org.rest.sec.model.Role;

public interface IRoleService extends IService< Role >{
	
	Role findByName( final String name );
	
	List< Role > search( final Long id );
	
	List< Role > search( final ImmutablePair< String, ? >... constraints );
	
}
