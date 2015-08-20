package org.baeldung.rest.common;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.baeldung.rest.model.Principal;
import org.baeldung.rest.model.Privilege;
import org.baeldung.rest.model.Role;
import org.baeldung.rest.model.User;

import com.google.common.collect.Sets;

public class FixtureEntityFactory {

    private FixtureEntityFactory() {
        throw new AssertionError();
    }

    // user DTO

    public static User createNewUser() {
        return createNewUser(randomAlphabetic(8), randomAlphabetic(8));
    }

    public static User createNewUser(final String name, final String pass) {
        return new User(name, pass, Sets.<Role> newHashSet());
    }

    // principal

    public static Principal createNewPrincipal() {
        return createNewPrincipal(randomAlphabetic(8), randomAlphabetic(8));
    }

    public static Principal createNewPrincipal(final String name, final String pass) {
        return new Principal(name, pass, Sets.<Role> newHashSet());
    }

    // role

    public static Role createNewRole() {
        return createNewRole(randomAlphabetic(8));
    }

    public static Role createNewRole(final String name) {
        return new Role(name, Sets.<Privilege> newHashSet());
    }

    // privilege

    public static Privilege createNewPrivilege() {
        return createNewPrivilege(randomAlphabetic(8));
    }

    public static Privilege createNewPrivilege(final String name) {
        return new Privilege(name);
    }

}
