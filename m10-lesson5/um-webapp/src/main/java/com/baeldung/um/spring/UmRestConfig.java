package com.baeldung.um.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import com.baeldung.um.persistence.model.Principal;
import com.baeldung.um.persistence.model.Privilege;
import com.baeldung.um.persistence.model.Role;

@Configuration
public class UmRestConfig extends RepositoryRestConfigurerAdapter    {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Principal.class);
        config.exposeIdsFor(Role.class);
        config.exposeIdsFor(Privilege.class);
    }
}