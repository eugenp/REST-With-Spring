package com.baeldung.um.web;

import java.io.IOException;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;

/**
 * An {@code HttpMessageConverter} that can read and write Kryo messages.
 */
public class KryoHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    public static final MediaType KRYO = new MediaType("application", "x-kryo");

    public KryoHttpMessageConverter() {
        super(KRYO);
    }

    @Override
    protected boolean supports(final Class<?> clazz) {
        return Object.class.isAssignableFrom(clazz);
    }

    @Override
    protected Object readInternal(final Class<? extends Object> clazz, final HttpInputMessage inputMessage) throws IOException {
        return null;
    }

    @Override
    protected void writeInternal(final Object object, final HttpOutputMessage outputMessage) throws IOException {
        //
    }

    @Override
    protected MediaType getDefaultContentType(final Object object) {
        return KRYO;
    }
}
