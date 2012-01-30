package org.rest.poc.persistence.service.user;

import org.rest.persistence.service.IService;
import org.rest.poc.model.User;
import org.springframework.data.domain.Page;

public interface IUserService extends IService< User >{
	
	Page< User > findPaginated( final int page, final int size );
	
}
