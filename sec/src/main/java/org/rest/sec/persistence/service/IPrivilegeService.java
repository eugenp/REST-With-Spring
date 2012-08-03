package org.rest.sec.persistence.service;

import org.rest.persistence.service.IService;
import org.rest.sec.model.Privilege;

public interface IPrivilegeService extends IService< Privilege >{
	
	Privilege findByName( final String name );
	
}
