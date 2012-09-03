package org.rest.sec.persistence.service.dto;

import org.rest.common.persistence.service.IService;
import org.rest.sec.model.dto.User;

public interface IUserService extends IService<User> {

    User getCurrentUser();

}
