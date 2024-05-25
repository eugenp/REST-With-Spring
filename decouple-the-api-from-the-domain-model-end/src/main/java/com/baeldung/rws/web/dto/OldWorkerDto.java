package com.baeldung.rws.web.dto;

import java.util.Objects;

import com.baeldung.rws.domain.model.ServiceUser;
import com.baeldung.rws.domain.model.Worker;
import com.baeldung.rws.web.dto.TaskDto.TaskUpdateAssigneeValidationData;
import com.baeldung.rws.web.dto.TaskDto.TaskUpdateValidationData;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Deprecated
@Schema(deprecated = true)
public record OldWorkerDto( // @formatter:off

    @NotNull(groups = { TaskUpdateValidationData.class, TaskUpdateAssigneeValidationData.class },
      message = "Worker id can't be null")
    Long id,

    @NotBlank(message = "email can't be blank")
    @Email(message = "You must provide a valid email address")
    String email,

    String firstName,

    String lastName) { // @formatter:on
    public static class Mapper {
          public static Worker toModel(OldWorkerDto dto) {
              if (dto == null)
                  return null;
              ServiceUser user = new ServiceUser(dto.email());
              Worker model = new Worker(user, dto.firstName(), dto.lastName());
              user.setWorker(model);
              if (!Objects.isNull(dto.id())) {
                  model.setId(dto.id());
              }

              return model;
          }

          public static OldWorkerDto toDto(Worker model) {
              if (model == null)
                  return null;
              OldWorkerDto dto = new OldWorkerDto(model.getId(), model.getUser().getEmail(), model.getFirstName(), model.getLastName());
              return dto;
          }
      }
    
      public interface WorkerUpdateValidationData {
      }
}
