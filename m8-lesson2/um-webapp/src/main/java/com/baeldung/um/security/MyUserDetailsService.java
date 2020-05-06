package com.baeldung.um.security;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.baeldung.um.persistence.model.User;
import com.baeldung.um.service.IUserService;
import com.google.common.base.Preconditions;

/**
 * Database user authentication service.
 */
@Component
public final class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserService userService;

    public MyUserDetailsService() {
        super();
    }

    // API - public

    /**
     * Loads the user from the datastore, by it's user name <br>
     */
    @Override
    public final UserDetails loadUserByUsername(final String username) {
        Preconditions.checkNotNull(username);

        final User user = userService.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username was not found: " + username);
        }

        final List<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles()
            .forEach(role -> {
                if (role != null) {
                    authorities.addAll(role.getPrivileges()
                        .stream()
                        .map(priv -> new SimpleGrantedAuthority(priv.getName()))
                        .distinct()
                        .collect(Collectors.toList()));
                }
            });

        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), authorities);
    }

}
