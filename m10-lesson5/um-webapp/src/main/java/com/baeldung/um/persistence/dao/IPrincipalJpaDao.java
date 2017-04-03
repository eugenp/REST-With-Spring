package com.baeldung.um.persistence.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.annotation.Secured;

import com.baeldung.common.interfaces.IByNameApi;
import com.baeldung.um.persistence.model.Principal;
import com.baeldung.um.util.Um.Privileges;

@RepositoryRestResource(path = "users", collectionResourceRel = "users", itemResourceRel = "user")
public interface IPrincipalJpaDao extends JpaRepository<Principal, Long>, JpaSpecificationExecutor<Principal>, IByNameApi<Principal> {
    @Override
    @Secured(Privileges.CAN_USER_READ)
    Page<Principal> findAll(Pageable pageable);

    @Override
    @Secured(Privileges.CAN_USER_READ)
    Principal findOne(Long id);

    @Override
    @Secured(Privileges.CAN_USER_WRITE)
    Principal save(Principal privilege);

    @Override
    @Secured(Privileges.CAN_USER_WRITE)
    void delete(Long id);
}
