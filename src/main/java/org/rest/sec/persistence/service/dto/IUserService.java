package org.rest.sec.persistence.service.dto;

import org.rest.persistence.service.IService;
import org.rest.sec.dto.User;

public interface IUserService extends IService< User >{
	
	User findByName( final String name );
	
}
