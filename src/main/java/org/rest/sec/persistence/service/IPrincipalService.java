package org.rest.sec.persistence.service;

import org.rest.persistence.service.IService;
import org.rest.sec.model.Principal;

public interface IPrincipalService extends IService< Principal >{
	
	Principal findByName( final String name );
	
}
