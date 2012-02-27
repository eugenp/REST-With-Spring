package org.rest.client;

import org.rest.client.template.IEntityOperations;
import org.rest.common.IEntity;
import org.rest.common.IRestDao;

public interface IClientTemplate< T extends IEntity > extends IRestDao< T >, IEntityOperations< T >{
	//
}
