package org.baeldung.rest.client;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.baeldung.rest.client.util.SecBusinessPaths;
import org.baeldung.rest.common.client.template.AbstractNamedClientRestTemplate;
import org.baeldung.rest.model.Privilege;
import org.baeldung.rest.util.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("client")
public class PrivilegeClientRestTemplate extends AbstractNamedClientRestTemplate<Privilege> {

    @Autowired
    private SecBusinessPaths paths;

    public PrivilegeClientRestTemplate() {
        super(Privilege.class);
    }

    // API

    // template methods

    @Override
    public final String getUri() {
        return paths.getPrivilegeUri();
    }

    @Override
    public final Pair<String, String> getDefaultCredentials() {
        return new ImmutablePair<String, String>(SecurityConstants.ADMIN_EMAIL, SecurityConstants.ADMIN_PASS);
    }

    @Override
    protected void beforeReadOperation() {
        super.beforeReadOperation();
    }

}
