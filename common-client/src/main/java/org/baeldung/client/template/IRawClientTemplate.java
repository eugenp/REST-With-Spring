package org.baeldung.client.template;

import org.apache.commons.lang3.tuple.Pair;
import org.baeldung.common.interfaces.IDto;
import org.baeldung.common.interfaces.IOperations;

public interface IRawClientTemplate<T extends IDto> extends IOperations<T>, IRestClientWithUri<T> {

    //

    String getUri();

    // create

    T create(final T resource, final Pair<String, String> credentials);

    T findOne(final long id, final Pair<String, String> credentials);

}
