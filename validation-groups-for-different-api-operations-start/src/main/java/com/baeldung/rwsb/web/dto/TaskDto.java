package com.baeldung.rwsb.web.dto;

import java.time.LocalDate;
import java.util.Objects;

import com.baeldung.rwsb.domain.model.Campaign;
import com.baeldung.rwsb.domain.model.Task;
import com.baeldung.rwsb.domain.model.TaskStatus;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TaskDto( // @formatter:off
    Long id,

    String uuid,

    @NotBlank(message = "name can't be blank")
    String name,

    @Size(min = 10, max = 50,
    message = "description must be between 10 and 50 characters long")
    String description,

    @Future(message = "dueDate must be in the future")
    LocalDate dueDate,

    TaskStatus status,

    @NotNull(message = "campaignId can't be null")
    Long campaignId,

    WorkerDto assignee,

    @Min(value = 1, message = "estimatedHours can't be less than 1")
    @Max(value = 40, message = "estimatedHours can't exceed 40")
    Integer estimatedHours) { // @formatter:on

    public static class Mapper {
        public static Task toModel(TaskDto dto) {
            if (dto == null)
                return null;

            Campaign campaign = new Campaign();
            campaign.setId(dto.campaignId());

            Task model = new Task(dto.name(), dto.description(), dto.dueDate(), campaign, dto.status(), WorkerDto.Mapper.toModel(dto.assignee()), dto.uuid(), dto.estimatedHours());
            if (!Objects.isNull(dto.id())) {
                model.setId(dto.id());
            }

            // we won't allow creating or modifying Campaigns via a Task
            return model;
        }

        public static TaskDto toDto(Task model) {
            if (model == null)
                return null;
            TaskDto dto = new TaskDto(model.getId(), model.getUuid(), model.getName(), model.getDescription(), model.getDueDate(), model.getStatus(), model.getCampaign()
                .getId(), WorkerDto.Mapper.toDto(model.getAssignee()), model.getEstimatedHours());
            return dto;
        }
    }
}
