package org.rest.testing.marshaller;

public interface IMarshaller {

    <T> String encode(final T entity);

    <T> T decode(final String entityAsString, final Class<T> clazz);

    String getMime();

}
