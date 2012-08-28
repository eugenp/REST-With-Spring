package org.rest.sec.client.template;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.rest.common.client.template.AbstractRESTTemplate;
import org.rest.common.util.SearchField;
import org.rest.sec.client.SecBusinessPaths;
import org.rest.sec.model.Privilege;
import org.rest.sec.model.Role;
import org.rest.sec.util.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;
import com.jayway.restassured.specification.RequestSpecification;

@Component
@Profile("client")
public final class RoleRESTTemplateImpl extends AbstractRESTTemplate<Role> {

    @Autowired
    protected SecBusinessPaths paths;

    public RoleRESTTemplateImpl() {
        super(Role.class);
    }

    // API

    public final Role findByName(final String name) {
        final String resourceAsXML = findOneByUriAsString(getURI() + "?name=" + name);
        return marshaller.decode(resourceAsXML, clazz);
    }

    // TODO: solve the marshalling issue and replace
    public final Role findByNameNew(final String name) {
        return findOneByAttributes(SearchField.name.name(), name);
    }

    // template method

    @Override
    public final String getURI() {
        return paths.getRoleUri();
    }

    @Override
    public final RequestSpecification givenAuthenticated(final Role resource) {
        return auth.givenBasicAuthenticated(SecurityConstants.ADMIN_USERNAME, SecurityConstants.ADMIN_PASSWORD);
    }

    @Override
    public final Role createNewEntity() {
        return new Role(randomAlphabetic(8), Sets.<Privilege> newHashSet());
    }

    @Override
    public final void invalidate(final Role entity) {
        entity.setName(null);
    }

    @Override
    public final void change(final Role resource) {
        resource.setName(randomAlphabetic(8));
    }

}
