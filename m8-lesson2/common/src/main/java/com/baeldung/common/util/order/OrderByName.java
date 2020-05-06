package com.baeldung.common.util.order;

import com.baeldung.common.interfaces.IWithName;
import com.google.common.collect.Ordering;

public final class OrderByName<T extends IWithName> extends Ordering<T> {

    public OrderByName() {
        super();
    }

    // API

    @Override
    public final int compare(final T left, final T right) {
        return left.getName()
            .compareTo(right.getName());
    }

}
