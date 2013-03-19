package org.rest.common.client.template;

import org.apache.commons.lang3.tuple.Pair;
import org.rest.common.client.marshall.IMarshaller;
import org.rest.common.client.web.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import com.google.common.base.Preconditions;

public abstract class AbstractBaseClientRestTemplate {

    @Autowired
    protected IMarshaller marshaller;

    public AbstractBaseClientRestTemplate() {
        super();
    }

    // find - one

    // util

    // template method

    protected abstract Pair<String, String> getDefaultCredentials();

    protected Pair<String, String> getReadDefaultCredentials() {
        return getDefaultCredentials();
    }

    protected Pair<String, String> getWriteDefaultCredentials() {
        return getDefaultCredentials();
    }

    /**
     * - this is a hook that executes before read operations, in order to allow custom security work to happen for read operations; similar to: AbstractRestTemplate.findRequest
     */
    protected void beforeReadOperation() {
        //
    }

    /**
     * - note: hook to be able to customize the find headers if needed
     */
    protected HttpHeaders findHeaders() {
        return HeaderUtil.createAcceptHeaders(marshaller);
    }

    protected HttpHeaders findHeadersWithAuth() {
        final Pair<String, String> defaultCredentials = getReadDefaultCredentials();
        return findHeadersWithAuth(defaultCredentials.getLeft(), defaultCredentials.getRight());
    }

    protected HttpHeaders findHeadersWithAuth(final Pair<String, String> credentials) {
        if (credentials == null) {
            return findHeadersWithAuth(null, null);
        }
        return findHeadersWithAuth(credentials.getLeft(), credentials.getRight());
    }

    /**
     * - note: hook to be able to customize the find headers if needed
     */
    protected HttpHeaders findHeadersWithAuth(final String username, final String password) {
        if (username == null || password == null) {
            Preconditions.checkState(username == null && password == null);
            final Pair<String, String> defaultCredentials = getReadDefaultCredentials();
            return HeaderUtil.createAcceptAndBasicAuthHeaders(marshaller, defaultCredentials.getLeft(), defaultCredentials.getRight());
        }
        return HeaderUtil.createAcceptAndBasicAuthHeaders(marshaller, username, password);
    }

    // write

    protected HttpHeaders writeHeadersWithAuth() {
        final Pair<String, String> defaultCredentials = getWriteDefaultCredentials();
        return writeHeadersWithAuth(defaultCredentials.getLeft(), defaultCredentials.getRight());
    }

    protected HttpHeaders writeHeadersWithAuth(final Pair<String, String> credentials) {
        if (credentials == null) {
            return writeHeadersWithAuth(null, null);
        }
        return writeHeadersWithAuth(credentials.getLeft(), credentials.getRight());
    }

    private HttpHeaders writeHeadersWithAuth(final String username, final String password) {
        if (username == null || password == null) {
            Preconditions.checkState(username == null && password == null);
            final Pair<String, String> defaultCredentials = getWriteDefaultCredentials();
            return HeaderUtil.createContentTypeAndBasicAuthHeaders(marshaller, defaultCredentials.getLeft(), defaultCredentials.getRight());
        }
        return HeaderUtil.createContentTypeAndBasicAuthHeaders(marshaller, username, password);
    }

}
