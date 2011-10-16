package org.rest.dao.foo;

import org.rest.common.dao.ICommonOperations;
import org.rest.model.Foo;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * @author eugenp
 */
public interface IFooDAO extends ICommonOperations< Foo >{
	
	void setHibernateTemplate( final HibernateTemplate hibernateTemplate );
	
}
