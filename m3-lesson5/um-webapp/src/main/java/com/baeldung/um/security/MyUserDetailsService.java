package com.baeldung.um.security;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.baeldung.um.persistence.model.User;
import com.baeldung.um.persistence.model.Privilege;
import com.baeldung.um.persistence.model.Role;
import com.baeldung.um.service.IUserService;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Preconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;

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

        final Set<Role> rolesOfUser = user.getRoles();
        final Set<Privilege> privileges = Sets.newHashSet();
        for (final Role roleOfUser : rolesOfUser) {
            privileges.addAll(roleOfUser.getPrivileges());
        }
        final Function<Object, String> toStringFunction = Functions.toStringFunction();
        final Collection<String> rolesToString = Collections2.transform(privileges, toStringFunction);
        final String[] roleStringsAsArray = rolesToString.toArray(new String[rolesToString.size()]);
        final List<GrantedAuthority> auths = AuthorityUtils.createAuthorityList(roleStringsAsArray);

        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), auths);
    }

}
