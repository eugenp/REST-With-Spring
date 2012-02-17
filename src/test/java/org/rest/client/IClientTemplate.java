package org.rest.client;

import org.rest.common.IEntity;
import org.rest.testing.template.IEntityOperations;
import org.rest.testing.template.IRestDao;

public interface IClientTemplate< T extends IEntity > extends IRestDao< T >, IEntityOperations< T >{
	//
}
