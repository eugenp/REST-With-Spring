package org.rest.client;

import org.rest.common.IEntity;
import org.rest.common.IRestDao;
import org.rest.testing.template.IEntityOperations;

public interface IClientTemplate< T extends IEntity > extends IRestDao< T >, IEntityOperations< T >{
	//
}
