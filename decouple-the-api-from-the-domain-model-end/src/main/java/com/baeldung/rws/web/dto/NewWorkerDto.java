package com.baeldung.rws.web.dto;

import java.util.Objects;

import com.baeldung.rws.domain.model.ServiceUser;
import com.baeldung.rws.domain.model.Worker;
import com.baeldung.rws.web.dto.TaskDto.TaskUpdateAssigneeValidationData;
import com.baeldung.rws.web.dto.TaskDto.TaskUpdateValidationData;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record NewWorkerDto( // @formatter:off

    @NotNull(groups = { TaskUpdateValidationData.class, TaskUpdateAssigneeValidationData.class },
      message = "Worker id can't be null")
    Long id,

    @NotNull(message = "user has to be present")
    @Valid
    UserDto user,

    String firstName,

    String lastName) { // @formatter:on

    public static class Mapper {
        public static Worker toModel(NewWorkerDto dto) {
            if (dto == null)
                return null;
            ServiceUser user = UserDto.Mapper.toModel(dto.user());
            Worker model = new Worker(user, dto.firstName(), dto.lastName());
            if (user != null) {
                user.setWorker(model);
            }
            if (!Objects.isNull(dto.id())) {
                model.setId(dto.id());
            }

            return model;
        }

        public static NewWorkerDto toDto(Worker model) {
            if (model == null)
                return null;
            NewWorkerDto dto = new NewWorkerDto(model.getId(), UserDto.Mapper.toDto(model.getUser()), model.getFirstName(), model.getLastName());
            return dto;
        }
    }

    public interface WorkerUpdateValidationData {
    }
}
