package org.rest.common.client.template;

import static org.rest.common.search.ClientOperation.EQ;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.rest.common.persistence.model.INameableEntity;
import org.rest.common.search.ClientOperation;
import org.rest.common.util.SearchField;

@SuppressWarnings({ "unchecked" })
public abstract class AbstractNamedClientRestTemplate<T extends INameableEntity> extends AbstractClientRestTemplate<T> implements IClientTemplate<T> {

    public AbstractNamedClientRestTemplate(final Class<T> clazzToSet) {
        super(clazzToSet);
    }

    // find one - by attributes

    @Override
    public T findByName(final String name) {
        beforeReadOperation();
        return searchOne(new ImmutableTriple<String, ClientOperation, String>(SearchField.name.name(), EQ, name));
    }

}
