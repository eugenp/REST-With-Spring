package com.baeldung.rws.commons.endtoend.client;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.springframework.test.web.reactive.server.WebTestClient.BodySpec;

import com.baeldung.rws.commons.endtoend.spec.DtoSpec;

public class SimpleBodySpec<B> {

    private final BodySpec<B, ?> wrappedBodySpec;

    public SimpleBodySpec(BodySpec<B, ?> wrappedBodySpec) {
        super();
        this.wrappedBodySpec = wrappedBodySpec;
    }

    public BodySpec<B, ?> getWrappedBodySpec() {
        return wrappedBodySpec;
    }

    public BodySpec<B, ?> valueMatches(DtoSpec<B> spec) {
        return wrappedBodySpec.value(body -> {
            List<String> mismatchingFields = spec.matches(body);
            assertThat(mismatchingFields).as("Mismatching fields: %s", mismatchingFields)
                .isEmpty();
        });
    }
}
