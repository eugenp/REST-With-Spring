package org.rest.common.client.marshall;

import java.util.List;

public interface IMarshaller {

    <T> String encode(final T resource);

    <T> T decode(final String resourceAsString, final Class<T> clazz);

    <T> List<T> decodeList(final String resourcesAsString, final Class<T> clazz);

    String getMime();

}
