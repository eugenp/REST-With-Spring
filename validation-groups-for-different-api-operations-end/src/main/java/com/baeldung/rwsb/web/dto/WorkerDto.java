package com.baeldung.rwsb.web.dto;

import java.util.Objects;

import com.baeldung.rwsb.domain.model.Worker;
import com.baeldung.rwsb.web.dto.TaskDto.TaskUpdateAssigneeValidationData;
import com.baeldung.rwsb.web.dto.TaskDto.TaskUpdateValidationData;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WorkerDto( // @formatter:off

    @NotNull(groups = { TaskUpdateValidationData.class, TaskUpdateAssigneeValidationData.class },
      message = "Worker id can't be null")
    Long id,

    @NotBlank(message = "email can't be blank")
    @Email(message = "You must provide a valid email address")
    String email,

    String firstName,

    String lastName) { // @formatter:on

    public static class Mapper {
        public static Worker toModel(WorkerDto dto) {
            if (dto == null)
                return null;
            Worker model = new Worker(dto.email(), dto.firstName(), dto.lastName());
            if (!Objects.isNull(dto.id())) {
                model.setId(dto.id());
            }
            return model;
        }

        public static WorkerDto toDto(Worker model) {
            if (model == null)
                return null;
            WorkerDto dto = new WorkerDto(model.getId(), model.getEmail(), model.getFirstName(), model.getLastName());
            return dto;
        }
    }

    public interface WorkerUpdateValidationData {
    }
}
