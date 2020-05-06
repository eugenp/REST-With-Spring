package com.baeldung.common.util.order;

import com.baeldung.common.interfaces.IWithId;
import com.google.common.collect.Ordering;

public final class OrderById<T extends IWithId> extends Ordering<T> {

    public OrderById() {
        super();
    }

    // API

    @Override
    public final int compare(final T left, final T right) {
        return left.getId()
            .compareTo(right.getId());
    }

}
