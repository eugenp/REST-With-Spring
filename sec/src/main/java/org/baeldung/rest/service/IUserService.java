package org.baeldung.rest.service;

import org.baeldung.rest.common.persistence.service.IService;
import org.baeldung.rest.model.User;

public interface IUserService extends IService<User> {

    User getCurrentUser();

}
