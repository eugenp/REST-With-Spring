package org.baeldung.rest.common.client.template;

import org.apache.commons.lang3.tuple.Pair;
import org.baeldung.rest.common.IOperations;
import org.baeldung.rest.common.client.IDto;

public interface IRawClientTemplate<T extends IDto> extends IOperations<T>, ITemplateWithUri<T> {

    //

    String getUri();

    // create

    T create(final T resource, final Pair<String, String> credentials);

    T findOne(final long id, final Pair<String, String> credentials);

}
