package org.baeldung.rest.service;

import org.baeldung.rest.common.persistence.service.IService;
import org.baeldung.rest.model.Principal;

public interface IPrincipalService extends IService<Principal> {

    Principal getCurrentPrincipal();

}
