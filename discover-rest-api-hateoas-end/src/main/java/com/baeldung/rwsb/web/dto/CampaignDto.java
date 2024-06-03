package com.baeldung.rwsb.web.dto;

import java.util.Objects;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;

import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "campaignList", itemRelation = "campaign")
public class CampaignDto { // @formatter:off

    private Long id;

    @NotBlank(message = "code can't be null")
    private String code;

    @NotBlank(groups = { CampaignUpdateValidationData.class, Default.class },
      message = "name can't be blank")
    private String name;

    @Size(groups = { CampaignUpdateValidationData.class, Default.class },
      min = 10, max = 50,
      message = "description must be between 10 and 50 characters long")
    private String description;

    private Set<TaskDto> tasks; // @formatter:on

    public CampaignDto(Long id, @NotBlank(message = "code can't be null") String code,
                      @NotBlank(groups = { CampaignUpdateValidationData.class, Default.class }, message = "name can't be blank") String name,
                      @Size(groups = { CampaignUpdateValidationData.class, Default.class }, min = 10, max = 50, message = "description must be between 10 and 50 characters long") String description,
                      Set<TaskDto> tasks) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.tasks = tasks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<TaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(Set<TaskDto> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CampaignDto that = (CampaignDto) o;
        return Objects.equals(id, that.id) && Objects.equals(code, that.code) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(tasks, that.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, description, tasks);
    }

    public interface CampaignUpdateValidationData {
    }

}
