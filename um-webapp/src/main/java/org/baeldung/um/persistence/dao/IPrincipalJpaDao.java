package org.baeldung.um.persistence.dao;

import org.baeldung.common.interfaces.IByNameApi;
import org.baeldung.um.persistence.model.Principal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IPrincipalJpaDao extends JpaRepository<Principal, Long>, JpaSpecificationExecutor<Principal>, IByNameApi<Principal> {
    //
}
