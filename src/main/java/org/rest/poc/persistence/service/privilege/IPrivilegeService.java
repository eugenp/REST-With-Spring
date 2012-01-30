package org.rest.poc.persistence.service.privilege;

import org.rest.persistence.service.IService;
import org.rest.poc.model.Privilege;
import org.springframework.data.domain.Page;

public interface IPrivilegeService extends IService< Privilege >{
	
	Page< Privilege > findPaginated( final int page, final int size );

}
