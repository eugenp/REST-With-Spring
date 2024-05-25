package com.baeldung.rws.web.dto;

import java.time.LocalDate;
import java.util.Objects;

import com.baeldung.rws.domain.model.TaskStatus;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "taskList", itemRelation = "task")
public class TaskDto extends RepresentationModel<TaskDto> { // @formatter:off
    private Long id;

    private String uuid;

    @NotBlank(groups = { TaskUpdateValidationData.class, Default.class },
      message = "name can't be blank")
    private String name;

    @Size(groups = { TaskUpdateValidationData.class, Default.class },
      min = 10, max = 50,
      message = "description must be between 10 and 50 characters long")
    private String description;

    @Future(message = "dueDate must be in the future")
    private LocalDate dueDate;

    @NotNull(groups = { TaskUpdateStatusValidationData.class, TaskUpdateValidationData.class },
      message = "status can't be null")
    private TaskStatus status;

    @NotNull(groups = { TaskUpdateValidationData.class, Default.class },
      message = "campaignId can't be null")
    private Long campaignId;

    @Valid
    @ConvertGroup(from = Default.class, to = WorkerOnTaskCreateValidationData.class)
    WorkerDto assignee; // @formatter:on

    public TaskDto(Long id, String uuid, @NotBlank(groups = { TaskUpdateValidationData.class, Default.class }, message = "name can't be blank") String name,
                   @Size(groups = { TaskUpdateValidationData.class, Default.class }, min = 10, max = 50, message = "description must be between 10 and 50 characters long") String description, @Future(message = "dueDate must be in the future") LocalDate dueDate,
                   @NotNull(groups = { TaskUpdateStatusValidationData.class, TaskUpdateValidationData.class }, message = "status can't be null") TaskStatus status,
                   @NotNull(groups = { TaskUpdateValidationData.class, Default.class }, message = "campaignId can't be null") Long campaignId, @Valid @ConvertGroup(from = Default.class, to = WorkerOnTaskCreateValidationData.class) WorkerDto assignee) {
        super();
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.campaignId = campaignId;
        this.assignee = assignee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public WorkerDto getAssignee() {
        return assignee;
    }

    public void setAssignee(WorkerDto assignee) {
        this.assignee = assignee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDto taskDto = (TaskDto) o;
        return Objects.equals(id, taskDto.id) && Objects.equals(uuid, taskDto.uuid) && Objects.equals(name, taskDto.name) && Objects.equals(description, taskDto.description) && Objects.equals(dueDate, taskDto.dueDate) && status == taskDto.status && Objects.equals(campaignId, taskDto.campaignId) && Objects.equals(assignee, taskDto.assignee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, name, description, dueDate, status, campaignId, assignee);
    }

    public interface TaskUpdateValidationData {
    }

    public interface TaskUpdateStatusValidationData {
    }

    public interface TaskUpdateAssigneeValidationData {
    }

    public interface WorkerOnTaskCreateValidationData {
    }
}
