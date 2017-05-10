package com.baeldung.um.util;

public final class UmMappings {

    // public static final String BASE = WebConstants.PATH_SEP;
    public static final String BASE = "/api/";

    public static final String USERS = "api/users";
    public static final String PRIVILEGES = "api/privileges";
    public static final String ROLES = "api/roles";

    // discoverability

    public static final class Singular {

        public static final String USER = "user";
        public static final String PRIVILEGE = "privilege";
        public static final String ROLE = "role";

    }

    public static final String AUTHENTICATION = "api/authentication";

    private UmMappings() {
        throw new AssertionError();
    }

    // API

}
