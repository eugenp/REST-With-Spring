package org.rest.common.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public final class SpringSecurityPrincipal extends User {

    private final String uuid;

    public SpringSecurityPrincipal(final String username, final String password, final Collection<? extends GrantedAuthority> authorities, final String uuidToSet) {
        super(username, password, authorities);

        uuid = uuidToSet;
    }

    // API

    public final String getUuid() {
        return uuid;
    }

}
