package org.rest.common.client.marshall;

import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.mapper.Mapper;

public class CustomCollectionConverter extends CollectionConverter {

    public CustomCollectionConverter(final Mapper mapper) {
        super(mapper);
    }

}