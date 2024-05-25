package com.baeldung.rws.web.dto;

import java.util.Objects;

import com.baeldung.rws.domain.model.Worker;
import com.baeldung.rws.web.dto.TaskDto.TaskUpdateAssigneeValidationData;
import com.baeldung.rws.web.dto.TaskDto.TaskUpdateValidationData;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class WorkerDto {

    @NotNull(groups = { TaskUpdateValidationData.class, TaskUpdateAssigneeValidationData.class }, message = "Worker id can't be null")
    private Long id;

    @NotBlank(message = "email can't be blank")
    @Email(message = "You must provide a valid email address")
    private String email;

    private String firstName;

    private String lastName;

    public WorkerDto() {
        super();
    }

    public WorkerDto(@NotNull(groups = { TaskUpdateValidationData.class, TaskUpdateAssigneeValidationData.class }, message = "Worker id can't be null") Long id,
        @NotBlank(message = "email can't be blank") @Email(message = "You must provide a valid email address") String email, String firstName, String lastName) {
        super();
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public static class Mapper {
        public static Worker toModel(WorkerDto dto) {
            if (dto == null)
                return null;
            Worker model = new Worker(dto.getEmail(), dto.getFirstName(), dto.getLastName());
            if (!Objects.isNull(dto.getId())) {
                model.setId(dto.getId());
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