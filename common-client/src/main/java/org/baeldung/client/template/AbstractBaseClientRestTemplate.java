package org.baeldung.client.template;

import org.apache.commons.lang3.tuple.Pair;
import org.baeldung.client.marshall.IMarshaller;
import org.baeldung.client.util.HeaderUtil;
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

    protected Pair<String, String> getReadCredentials() {
        return getDefaultCredentials();
    }

    protected Pair<String, String> getReadExtendedCredentials() {
        return getReadCredentials();
    }

    protected Pair<String, String> getWriteCredentials() {
        return getDefaultCredentials();
    }

    /**
     * - this is a hook that executes before read operations, in order to allow custom security work to happen for read operations; similar to: AbstractRestTemplate.findRequest
     */
    protected void beforeReadOperation() {
        //
    }

    // read

    /**
     * - note: hook to be able to customize the find headers if needed
     */
    protected HttpHeaders readHeaders() {
        return HeaderUtil.createAcceptHeaders(marshaller);
    }

    protected HttpHeaders readHeadersWithAuth() {
        final Pair<String, String> defaultCredentials = getReadCredentials();
        return readHeadersWithAuth(defaultCredentials.getLeft(), defaultCredentials.getRight());
    }

    protected HttpHeaders readHeadersWithAuth(final Pair<String, String> credentials) {
        if (credentials == null) {
            final Pair<String, String> readCredentials = getReadCredentials();
            return readHeadersWithAuth(readCredentials.getLeft(), readCredentials.getRight());
        }
        return readHeadersWithAuth(credentials.getLeft(), credentials.getRight());
    }

    private final HttpHeaders readHeadersWithAuth(final String username, final String password) {
        Preconditions.checkNotNull(username);
        Preconditions.checkNotNull(password);
        return HeaderUtil.createAcceptAndBasicAuthHeaders(marshaller, username, password);
    }

    // write

    protected HttpHeaders writeHeadersWithAuth() {
        final Pair<String, String> defaultCredentials = getWriteCredentials();
        return writeHeadersWithAuth(defaultCredentials.getLeft(), defaultCredentials.getRight());
    }

    protected HttpHeaders writeHeadersWithAuth(final Pair<String, String> credentials) {
        if (credentials == null) {
            final Pair<String, String> writeCredentials = getWriteCredentials();
            return writeHeadersWithAuth(writeCredentials.getLeft(), writeCredentials.getRight());
        }
        return writeHeadersWithAuth(credentials.getLeft(), credentials.getRight());
    }

    private final HttpHeaders writeHeadersWithAuth(final String username, final String password) {
        Preconditions.checkNotNull(username);
        Preconditions.checkNotNull(password);
        return HeaderUtil.createContentTypeAndBasicAuthHeaders(marshaller, username, password);
    }

}
