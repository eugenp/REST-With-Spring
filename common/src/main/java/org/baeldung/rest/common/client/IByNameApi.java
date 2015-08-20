package org.baeldung.rest.common.client;

public interface IByNameApi<T extends IWithName> {

    T findByName(final String name);

}
