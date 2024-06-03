package com.baeldung.rwsb.web.dto;

import java.util.Objects;

import com.baeldung.rwsb.web.dto.TaskDto.TaskUpdateAssigneeValidationData;
import com.baeldung.rwsb.web.dto.TaskDto.TaskUpdateValidationData;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class WorkerDto { // @formatter:off

    @NotNull(groups = { TaskUpdateValidationData.class, TaskUpdateAssigneeValidationData.class },
      message = "Worker id can't be null")
    private Long id;

    @NotBlank(message = "email can't be blank")
    @Email(message = "You must provide a valid email address")
    private String email;

    private String firstName;

    private String lastName; // @formatter:on

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

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkerDto workerDto = (WorkerDto) o;
        return Objects.equals(id, workerDto.id) && Objects.equals(email, workerDto.email) && Objects.equals(firstName, workerDto.firstName) && Objects.equals(lastName, workerDto.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstName, lastName);
    }

    public interface WorkerUpdateValidationData {
    }
}
