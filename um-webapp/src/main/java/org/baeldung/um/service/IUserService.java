package org.baeldung.um.service;

import org.baeldung.common.persistence.service.IService;
import org.baeldung.um.web.dto.UserDto;

public interface IUserService extends IService<UserDto> {

    UserDto getCurrentUser();

}
