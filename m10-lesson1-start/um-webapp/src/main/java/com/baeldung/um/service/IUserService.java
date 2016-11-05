package com.baeldung.um.service;

import com.baeldung.common.persistence.service.IService;
import com.baeldung.um.web.dto.UserDto;

public interface IUserService extends IService<UserDto> {

    UserDto getCurrentUser();

}
