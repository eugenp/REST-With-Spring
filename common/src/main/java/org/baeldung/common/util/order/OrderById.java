package org.baeldung.common.util.order;

import org.baeldung.common.interfaces.IWithLongId;

import com.google.common.collect.Ordering;

public final class OrderById<T extends IWithLongId> extends Ordering<T> {

    public OrderById() {
        super();
    }

    // API

    @Override
    public final int compare(final T left, final T right) {
        return left.getId().compareTo(right.getId());
    }

}
