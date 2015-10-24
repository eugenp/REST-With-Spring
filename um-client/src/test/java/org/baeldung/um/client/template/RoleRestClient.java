package org.baeldung.um.client.template;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.baeldung.test.common.client.template.AbstractRestClient;
import org.baeldung.um.client.UmPaths;
import org.baeldung.um.persistence.model.Role;
import org.baeldung.um.util.Um;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

@Component
@Profile("client")
public final class RoleRestClient extends AbstractRestClient<Role> {

    @Autowired
    protected UmPaths paths;

    public RoleRestClient() {
        super(Role.class);
    }

    // API

    public final Role findByName(final String name) {
        final String resourcesAsRepresentation = findOneByUriAsString(getUri() + "?q=name=" + name);
        final List<Role> resources = marshaller.decodeList(resourcesAsRepresentation, clazz);
        if (resources.isEmpty()) {
            return null;
        }
        Preconditions.checkState(resources.size() == 1);
        return resources.get(0);
    }

    // template method

    @Override
    public final String getUri() {
        return paths.getRoleUri();
    }

    @Override
    public final Pair<String, String> getDefaultCredentials() {
        return new ImmutablePair<String, String>(Um.ADMIN_EMAIL, Um.ADMIN_PASS);
    }

}
