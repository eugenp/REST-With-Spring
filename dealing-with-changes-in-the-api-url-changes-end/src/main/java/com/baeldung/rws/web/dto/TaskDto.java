package com.baeldung.rws.web.dto;

import java.time.LocalDate;
import java.util.Objects;

import com.baeldung.rws.domain.model.Campaign;
import com.baeldung.rws.domain.model.Task;
import com.baeldung.rws.domain.model.TaskStatus;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;

public class TaskDto {
    private Long id;

    private String uuid;

    @NotBlank(groups = { TaskUpdateValidationData.class, Default.class }, message = "name can't be blank")
    private String name;

    @Size(groups = { TaskUpdateValidationData.class, Default.class }, min = 10, max = 50, message = "description must be between 10 and 50 characters long")
    private String description;

    @Future(message = "dueDate must be in the future")
    private LocalDate dueDate;

    @NotNull(groups = { TaskUpdateStatusValidationData.class, TaskUpdateValidationData.class }, message = "status can't be null")
    private TaskStatus status;

    @NotNull(groups = { TaskUpdateValidationData.class, Default.class }, message = "campaignId can't be null")
    private Long campaignId;

    @Valid
    @ConvertGroup(from = Default.class, to = WorkerOnTaskCreateValidationData.class)
    private WorkerDto assignee;

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

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public WorkerDto getAssignee() {
        return assignee;
    }

    public static class Mapper {
        public static Task toModel(TaskDto dto) {
            if (dto == null)
                return null;

            Campaign campaign = new Campaign();
            campaign.setId(dto.getCampaignId());

            Task model = new Task(dto.getName(), dto.getDescription(), dto.getDueDate(), campaign, dto.getStatus(), WorkerDto.Mapper.toModel(dto.getAssignee()), dto.getUuid());
            if (!Objects.isNull(dto.getId())) {
                model.setId(dto.getId());
            }

            // we won't allow creating or modifying Campaigns via a Task
            return model;
        }

        public static TaskDto toDto(Task model) {
            if (model == null)
                return null;
            TaskDto dto = new TaskDto(model.getId(), model.getUuid(), model.getName(), model.getDescription(), model.getDueDate(), model.getStatus(), model.getCampaign()
                .getId(), WorkerDto.Mapper.toDto(model.getAssignee()));
            return dto;
        }
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
