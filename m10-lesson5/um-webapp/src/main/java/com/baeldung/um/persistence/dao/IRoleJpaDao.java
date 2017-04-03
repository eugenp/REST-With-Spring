package com.baeldung.um.persistence.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.annotation.Secured;

import com.baeldung.common.interfaces.IByNameApi;
import com.baeldung.um.persistence.model.Role;
import com.baeldung.um.util.Um.Privileges;

@RepositoryRestResource(path = "roles")
public interface IRoleJpaDao extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role>, IByNameApi<Role> {
    @Override
    @Secured(Privileges.CAN_ROLE_READ)
    Page<Role> findAll(Pageable pageable);

    @Override
    @Secured(Privileges.CAN_ROLE_READ)
    Role findOne(Long id);

    @Override
    @Secured(Privileges.CAN_ROLE_WRITE)
    Role save(Role privilege);

    @Override
    @Secured(Privileges.CAN_ROLE_WRITE)
    void delete(Long id);
}
