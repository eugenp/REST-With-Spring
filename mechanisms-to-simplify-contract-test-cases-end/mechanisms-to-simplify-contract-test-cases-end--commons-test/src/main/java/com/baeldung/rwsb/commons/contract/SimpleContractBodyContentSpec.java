package com.baeldung.rwsb.commons.contract;

import java.util.Map;
import java.util.stream.Stream;

import org.hamcrest.Matcher;
import org.springframework.test.web.reactive.server.WebTestClient;

public class SimpleContractBodyContentSpec {

    private WebTestClient.BodyContentSpec contentBodySpec;

    public SimpleContractBodyContentSpec(WebTestClient.BodyContentSpec contentBodySpec) {
        super();
        this.contentBodySpec = contentBodySpec;
    }

    public SimpleContractBodyContentSpec containsFields(String... fields) {
        Stream.of(fields)
            .forEach(field -> contentBodySpec.jsonPath("$.%s", field)
                .isNotEmpty());
        return this;
    }

    @SafeVarargs
    public final SimpleContractBodyContentSpec fieldsMatch(Map.Entry<String, Matcher<?>>... fields) {
        Stream.of(fields)
            .forEach(field -> contentBodySpec.jsonPath("$.%s", field.getKey())
                .value(field.getValue()));
        return this;
    }
    
    public SimpleContractBodyContentSpec listContainsFields(String... fields) {
        Stream.of(fields)
            .forEach(field -> contentBodySpec.jsonPath("$..%s", field)
                .isNotEmpty());
        return this;
    }
    
    @SafeVarargs
    public final SimpleContractBodyContentSpec listFieldsMatch(Map.Entry<String, Matcher<?>>... fields) {
        Stream.of(fields)
            .forEach(field -> contentBodySpec.jsonPath("$..%s", field.getKey())
                .value(field.getValue()));
        return this;
    }
}
