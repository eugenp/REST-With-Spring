package org.rest.poc.persistence.service.bar;

import org.rest.persistence.service.IService;
import org.rest.poc.model.Bar;
import org.springframework.data.domain.Page;

public interface IBarService extends IService< Bar >{
	
	Page< Bar > findPaginated( final int page, final int size );

}
