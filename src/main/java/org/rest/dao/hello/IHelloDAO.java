package org.rest.dao.hello;

import org.rest.common.dao.ICommonOperations;
import org.rest.model.Hello;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * @author eugenp
 */
public interface IHelloDAO extends ICommonOperations< Hello >{
	
	void setHibernateTemplate( final HibernateTemplate hibernateTemplate );
	
}
