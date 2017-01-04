package com.baeldung.test.common.client.security;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private String expiresIn;

    @JsonProperty("scope")
    private String scope;

    // API

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    public void setTokenType(final String tokenType) {
        this.tokenType = tokenType;
    }

    public void setExpiresIn(final String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setScope(final String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "TokenResponse [accessToken=" + accessToken + ", tokenType=" + tokenType + ", expiresIn=" + expiresIn + ", scope=" + scope + "]";
    }

}