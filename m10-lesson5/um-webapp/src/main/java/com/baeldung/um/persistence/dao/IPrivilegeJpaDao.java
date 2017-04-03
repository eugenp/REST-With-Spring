package com.baeldung.um.persistence.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.annotation.Secured;

import com.baeldung.common.interfaces.IByNameApi;
import com.baeldung.um.persistence.model.Privilege;
import com.baeldung.um.util.Um.Privileges;

@RepositoryRestResource(path = "privileges")
public interface IPrivilegeJpaDao extends JpaRepository<Privilege, Long>, JpaSpecificationExecutor<Privilege>, IByNameApi<Privilege> {

    @Override
    @Secured(Privileges.CAN_PRIVILEGE_READ)
    Page<Privilege> findAll(Pageable pageable);

    @Override
    @Secured(Privileges.CAN_PRIVILEGE_READ)
    Privilege findOne(Long id);

    @Override
    @Secured(Privileges.CAN_PRIVILEGE_WRITE)
    Privilege save(Privilege privilege);

    @Override
    @Secured(Privileges.CAN_PRIVILEGE_WRITE)
    void delete(Long id);
}
