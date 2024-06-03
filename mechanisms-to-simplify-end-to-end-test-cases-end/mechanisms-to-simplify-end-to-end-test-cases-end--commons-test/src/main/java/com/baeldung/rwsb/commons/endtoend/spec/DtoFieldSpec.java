package com.baeldung.rwsb.commons.endtoend.spec;

import java.util.function.Function;

import org.hamcrest.Matcher;

public class DtoFieldSpec<T, U> {

    String fieldName;
    Function<T, U> extractor;
    Matcher<U> matcher;

    private DtoFieldSpec(String fieldName, Function<T, U> extractor) {
        this.fieldName = fieldName;
        this.extractor = extractor;
    }

    public static <V, W> DtoFieldSpec<V, W> from(String fieldName, Function<V, W> extractor) {
        return new DtoFieldSpec<>(fieldName, extractor);
    }

    public String getFieldName() {
        return fieldName;
    }

    public void define(Matcher<U> matcher) {
        this.matcher = matcher;
    }

    public boolean matches(T expectedDto) {
        return this.matcher.matches(this.extractor.apply(expectedDto));
    }
}
