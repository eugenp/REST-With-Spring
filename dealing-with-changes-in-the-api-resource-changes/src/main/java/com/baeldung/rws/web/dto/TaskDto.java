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

public record TaskDto( // @formatter:off
    Long id,

    String uuid,

    @NotBlank(groups = { TaskUpdateValidationData.class, Default.class },
      message = "name can't be blank")
    String name,

    @Size(groups = { TaskUpdateValidationData.class, Default.class },
      min = 10, max = 50,
      message = "description must be between 10 and 50 characters long")
    @Deprecated
    String description,

    @Future(message = "dueDate must be in the future")
    LocalDate dueDate,

    @NotNull(groups = { TaskUpdateStatusValidationData.class, TaskUpdateValidationData.class },
      message = "status can't be null")
    TaskStatus status,

    // Breaking change
    @NotNull(groups = { TaskUpdateValidationData.class, Default.class },
      message = "campaignId can't be null")
    String campaignId,

    @Valid
    @ConvertGroup(from = Default.class, to = WorkerOnTaskCreateValidationData.class)
    NewWorkerDto assignee,
    
    // Output: non-breaking change (can be ignored) / Input: breaking change (not optional)
//    @NotNull(groups = { TaskUpdateValidationData.class, Default.class },
//      message = "task requires estimated hours")
    Integer estimatedHours) { // @formatter:on

    public static class Mapper {
        public static Task toModel(TaskDto dto) {
            if (dto == null)
                return null;

            Campaign campaign = new Campaign();
            campaign.setId(dto.campaignId());

            Task model = new Task(dto.name(), dto.description(), dto.dueDate(), campaign, dto.status(), NewWorkerDto.Mapper.toModel(dto.assignee()), dto.uuid(), dto.estimatedHours());
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
                .getId(), NewWorkerDto.Mapper.toDto(model.getAssignee()), model.getEstimatedHours());
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
