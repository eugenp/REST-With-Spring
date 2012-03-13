package org.rest.sec.persistence.service;

import org.rest.persistence.service.IService;
import org.rest.sec.model.Role;

public interface IRoleService extends IService<Role> {

    Role findByName(final String name);

}
