package com.baeldung.rws.commons.endtoend.client;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.springframework.test.web.reactive.server.WebTestClient.BodySpec;

import com.baeldung.rws.commons.endtoend.spec.DtoFieldSpec;

public class FieldSpecBodySpec<B> {

    private final BodySpec<B, ?> wrappedBodySpec;

    public FieldSpecBodySpec(BodySpec<B, ?> wrappedBodySpec) {
        super();
        this.wrappedBodySpec = wrappedBodySpec;
    }

    @SafeVarargs
    final public BodySpec<B, ?> valueMatches(DtoFieldSpec<B, ?>... specs) {
        return wrappedBodySpec.value(body -> {
            List<String> mismatchingFields = Arrays.stream(specs)
                .filter(fieldSpec -> !fieldSpec.matches(body))
                .map(fieldSpec -> fieldSpec.getFieldName())
                .toList();
            assertThat(mismatchingFields).as("Mismatching fields: %s", mismatchingFields)
                .isEmpty();
        });
    }
}
