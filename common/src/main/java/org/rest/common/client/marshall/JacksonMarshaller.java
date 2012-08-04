package org.rest.common.client.marshall;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

@Component
@Profile("client")
public final class JacksonMarshaller implements IMarshaller {
    private static final Logger logger = LoggerFactory.getLogger(JacksonMarshaller.class);

    ObjectMapper objectMapper;

    public JacksonMarshaller() {
	super();

	objectMapper = new ObjectMapper();
    }

    // API

    @Override
    public final <T> String encode(final T entity) {
	Preconditions.checkNotNull(entity);
	String entityAsJSON = null;
	try {
	    entityAsJSON = objectMapper.writeValueAsString(entity);
	} catch (final JsonParseException parseEx) {
	    logger.error("", parseEx);
	} catch (final JsonMappingException mappingEx) {
	    logger.error("", mappingEx);
	} catch (final IOException ioEx) {
	    logger.error("", ioEx);
	}

	return entityAsJSON;
    }

    @Override
    public final <T> T decode(final String entityAsString, final Class<T> clazz) {
	Preconditions.checkNotNull(entityAsString);

	T entity = null;
	try {
	    entity = objectMapper.readValue(entityAsString, clazz);
	} catch (final JsonParseException parseEx) {
	    logger.error("", parseEx);
	} catch (final JsonMappingException mappingEx) {
	    logger.error("", mappingEx);
	} catch (final IOException ioEx) {
	    logger.error("", ioEx);
	}

	return entity;
    }

    public final <T> List<T> decodeList(final String entitiesAsString) {
	try {
	    return objectMapper.readValue(entitiesAsString, new TypeReference<List<T>>() {
		// ...
	    });
	} catch (final JsonParseException parseEx) {
	    logger.error("", parseEx);
	} catch (final JsonMappingException mappingEx) {
	    logger.error("", mappingEx);
	} catch (final IOException ioEx) {
	    logger.error("", ioEx);
	}

	return Lists.newArrayList();
    }

    @Override
    public final String getMime() {
	return MediaType.APPLICATION_JSON.toString();
    }

}
