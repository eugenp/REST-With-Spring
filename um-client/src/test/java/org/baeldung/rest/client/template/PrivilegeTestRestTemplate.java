package org.baeldung.rest.client.template;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.baeldung.rest.client.util.SecBusinessPaths;
import org.baeldung.rest.common.client.template.AbstractTestRestTemplate;
import org.baeldung.rest.model.Privilege;
import org.baeldung.rest.util.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("client")
public final class PrivilegeTestRestTemplate extends AbstractTestRestTemplate<Privilege> {

    @Autowired
    protected SecBusinessPaths paths;

    public PrivilegeTestRestTemplate() {
        super(Privilege.class);
    }

    // template method

    @Override
    public final String getUri() {
        return paths.getPrivilegeUri();
    }

    @Override
    public final Pair<String, String> getDefaultCredentials() {
        return new ImmutablePair<String, String>(SecurityConstants.ADMIN_EMAIL, SecurityConstants.ADMIN_PASS);
    }

}
