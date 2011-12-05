package org.rest.dao.foo.impl;

import org.rest.common.dao.AbstractDAO;
import org.rest.dao.foo.IFooDAO;
import org.rest.model.Foo;
import org.springframework.stereotype.Repository;

@Repository
public class FooDAO extends AbstractDAO< Foo > implements IFooDAO{
	
	public FooDAO(){
		super( Foo.class );
	}
	
}
