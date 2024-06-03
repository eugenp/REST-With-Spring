package com.baeldung.rwsb.domain.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TaskStatus {
    //@formatter:off
    TO_DO("To Do"), 
    IN_PROGRESS("In Progress"), 
    ON_HOLD("On Hold"), 
    DONE("Done");
    //@formatter:on

    private final String label;

    private TaskStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @JsonValue
    public String toValue() {
        return this.getLabel();
    }
}