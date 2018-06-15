package com.baeldung.um.util;

public final class UmMappings {

    public static final String API = "api/";
    public static final String USERS = "api/users";
    public static final String PRIVILEGES = "api/privileges";
    public static final String ROLES = "api/roles";

    // discoverability

    public static final class Singular {

        public static final String USER = "api/user";
        public static final String PRIVILEGE = "api/privilege";
        public static final String ROLE = "api/role";
    }

    public static final String AUTHENTICATION = "authentication";

    private UmMappings() {
        throw new AssertionError();
    }

    // API
}
