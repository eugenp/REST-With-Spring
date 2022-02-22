package com.baeldung.um.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public final class UmUser extends User {

    private long id;

    public UmUser(final String username, final String password, final boolean enabled, final boolean accountNonExpired, final boolean credentialsNonExpired, final boolean accountNonLocked, final Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public UmUser(final String name, final String password, final List<GrantedAuthority> auths, final long userId) {
        super(name, password, auths);

        id = userId;
    }

    // API

    public final long getId() {
        return id;
    }

    public final void setId(final long id) {
        this.id = id;
    }

}
