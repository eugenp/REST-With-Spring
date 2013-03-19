package org.rest.sec.client.template;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.rest.common.client.template.AbstractNamedClientRestTemplate;
import org.rest.sec.client.SecBusinessPaths;
import org.rest.sec.model.Privilege;
import org.rest.sec.util.SecurityConstants;
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
