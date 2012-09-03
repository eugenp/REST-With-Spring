package org.rest.sec.persistence.service;

import org.rest.common.persistence.service.IService;
import org.rest.sec.model.Principal;

public interface IPrincipalService extends IService<Principal> {

    Principal getCurrentPrincipal();

}
