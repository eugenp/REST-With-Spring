package org.baeldung.um.service;

import org.baeldung.common.persistence.service.IService;
import org.baeldung.um.model.Principal;

public interface IPrincipalService extends IService<Principal> {

    Principal getCurrentPrincipal();

}
