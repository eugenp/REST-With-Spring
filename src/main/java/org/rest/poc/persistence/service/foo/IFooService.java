package org.rest.poc.persistence.service.foo;

import org.rest.persistence.service.IService;
import org.rest.poc.model.Foo;
import org.springframework.data.domain.Page;

public interface IFooService extends IService< Foo >{
	
	Page< Foo > findPaginated( final int page, final int size );
	
}
