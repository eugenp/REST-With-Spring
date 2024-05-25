package com.baeldung.rws.commons.contract;

import static java.util.stream.Collectors.joining;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

public class SimpleRequestBodyBuilder {

    private final ObjectNode jsonNodeTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    private SimpleRequestBodyBuilder(String jsonTemplate) throws JsonMappingException, JsonProcessingException {
        super();
        this.jsonNodeTemplate = (ObjectNode) mapper.readTree(jsonTemplate);
    }

    public static SimpleRequestBodyBuilder fromResource(Resource inputJsonFile) throws IOException {
        String jsonTemplate = readResource(inputJsonFile);
        return new SimpleRequestBodyBuilder(jsonTemplate);
    }

    public SimpleRequestBodyBuilder with(String field, String value) throws IOException {
        JsonNode nodeValue = TextNode.valueOf(value);
        this.jsonNodeTemplate.set(field, nodeValue);
        return this;
    }

    public SimpleRequestBodyBuilder with(String field, Long value) throws IOException {
        JsonNode nodeValue = LongNode.valueOf(value);
        this.jsonNodeTemplate.set(field, nodeValue);
        return this;
    }

    public SimpleRequestBodyBuilder withNull(String field) throws IOException {
        this.jsonNodeTemplate.putNull(field);
        return this;
    }

    public SimpleRequestBodyBuilder with(String field, Resource resource) throws IOException {
        Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
        String jsonTemplate = FileCopyUtils.copyToString(reader);
        return this.with(field, jsonTemplate);
    }

    public SimpleRequestBodyBuilder with(String field, Collection<Resource> resources) throws IOException {
        String arrayResourcesJson = resources.stream()
            .map(SimpleRequestBodyBuilder::readResource)
            .collect(joining(",", "[", "]"));
        JsonNode listJsonNode = mapper.readTree(arrayResourcesJson);
        this.jsonNodeTemplate.set(field, listJsonNode);
        return this;
    }

    public SimpleRequestBodyBuilder withRandom(String field) throws IOException {
        String randomString = UUID.randomUUID()
            .toString();
        return this.with(field, randomString);
    }

    public SimpleRequestBodyBuilder withJsonString(String field, String value) throws IOException {
        JsonNode nodeValue = mapper.readTree(value);
        this.jsonNodeTemplate.set(field, nodeValue);
        return this;
    }

    private static String readResource(Resource resource) {
        try {
            Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public String build() {
        return jsonNodeTemplate.toString();
    }
}
