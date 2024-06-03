package com.baeldung.rwsb.commons.endtoend.client;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.springframework.test.web.reactive.server.WebTestClient.ListBodySpec;

import com.baeldung.rwsb.commons.endtoend.spec.DtoSpec;

public class SimpleListBodySpec<E> {

    private final ListBodySpec<E> wrappedListBodySpec;

    public SimpleListBodySpec(ListBodySpec<E> wrappedListBodySpec) {
        super();
        this.wrappedListBodySpec = wrappedListBodySpec;
    }

    @SafeVarargs
    final public ListBodySpec<E> contains(DtoSpec<E>... specs) {
        List<DtoSpec<E>> specsList = List.of(specs);
        return wrappedListBodySpec.value(list -> {
            assertThat(specsList).as("Response list doesn't contain all specified elements")
                .allMatch(spec -> list.stream()
                    .anyMatch(dto -> spec.matches(dto)
                        .isEmpty()));
        });
    }
}
