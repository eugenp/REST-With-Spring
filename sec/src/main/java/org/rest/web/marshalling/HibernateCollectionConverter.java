package org.rest.web.marshalling;

import org.hibernate.collection.internal.PersistentList;
import org.hibernate.collection.internal.PersistentSet;

import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.mapper.Mapper;

public class HibernateCollectionConverter extends CollectionConverter {

    public HibernateCollectionConverter(final Mapper mapper) {
        super(mapper);
    }

    @Override
    public boolean canConvert(@SuppressWarnings("rawtypes") final Class type) {
        return super.canConvert(type) || PersistentList.class.equals(type) || PersistentSet.class.equals(type);
    }
}
