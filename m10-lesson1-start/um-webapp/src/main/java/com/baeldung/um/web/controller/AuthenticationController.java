package com.baeldung.um.web.controller;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.baeldung.common.security.SpringSecurityUtil;
import com.baeldung.um.persistence.model.Privilege;
import com.baeldung.um.persistence.model.Role;
import com.baeldung.um.persistence.model.User;
import com.baeldung.um.util.UmMappings;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;

/**
 * - note: this controller will start working with the User model and, if necessary, will move to a Authentication resource (which is the way it should work)
 */
@Controller
public class AuthenticationController {

    public AuthenticationController() {
        super();
    }

    // API

    @RequestMapping(method = RequestMethod.GET, value = UmMappings.AUTHENTICATION)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public User createAuthentication() {
        final Authentication auth = SpringSecurityUtil.getCurrentAuthentication();

        final Function<GrantedAuthority, Privilege> springAuthorityToPrivilegeFunction = new Function<GrantedAuthority, Privilege>() {
            @Override
            public final Privilege apply(final GrantedAuthority springAuthority) {
                return new Privilege(springAuthority.getAuthority());
            }
        };
        final Collection<Privilege> privileges = Collections2.transform(auth.getAuthorities(), springAuthorityToPrivilegeFunction);
        final Role defaultRole = new Role("defaultRole", Sets.<Privilege> newHashSet(privileges));

        final User authenticationResource = new User(auth.getName(), auth.getName(), (String) auth.getCredentials(), Sets.<Role> newHashSet(defaultRole));
        return authenticationResource;
    }

}
