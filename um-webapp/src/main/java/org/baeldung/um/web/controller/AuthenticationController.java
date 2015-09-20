package org.baeldung.um.web.controller;

import java.util.Collection;

import org.baeldung.common.security.SpringSecurityUtil;
import org.baeldung.um.persistence.model.Privilege;
import org.baeldung.um.persistence.model.Role;
import org.baeldung.um.util.UmMappings;
import org.baeldung.um.web.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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
    public UserDto createAuthentication() {
        final Authentication authenticationInSpring = SpringSecurityUtil.getCurrentAuthentication();

        final Function<GrantedAuthority, Privilege> springAuthorityToPrivilegeFunction = new Function<GrantedAuthority, Privilege>() {
            @Override
            public final Privilege apply(final GrantedAuthority springAuthority) {
                return new Privilege(springAuthority.getAuthority());
            }
        };
        final Collection<Privilege> privileges = Collections2.transform(authenticationInSpring.getAuthorities(), springAuthorityToPrivilegeFunction);
        final Role defaultRole = new Role("defaultRole", Sets.<Privilege> newHashSet(privileges));

        final UserDto authenticationResource = new UserDto(authenticationInSpring.getName(), (String) authenticationInSpring.getCredentials(), Sets.<Role> newHashSet(defaultRole));
        return authenticationResource;
    }

}
