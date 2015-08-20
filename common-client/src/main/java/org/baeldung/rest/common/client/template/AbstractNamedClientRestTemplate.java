package org.baeldung.rest.common.client.template;

import static org.baeldung.rest.common.search.ClientOperation.EQ;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.baeldung.rest.common.client.INameableDto;
import org.baeldung.rest.common.search.ClientOperation;
import org.baeldung.rest.common.util.SearchField;

@SuppressWarnings({ "unchecked" })
public abstract class AbstractNamedClientRestTemplate<T extends INameableDto> extends AbstractClientRestTemplate<T>implements IClient<T> {

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
