package com.baeldung.um.service;

import com.baeldung.common.persistence.service.IService;
import com.baeldung.um.persistence.model.Principal;

public interface IPrincipalService extends IService<Principal> {

    Principal getCurrentPrincipal();

}
