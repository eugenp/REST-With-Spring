package com.baeldung.rws.web.mapper;

import java.util.Optional;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.baeldung.rws.domain.model.ServiceUser;
import com.baeldung.rws.domain.model.Worker;
import com.baeldung.rws.web.dto.NewWorkerDto;
import com.baeldung.rws.web.dto.UserDto;

@Mapper(componentModel = "spring")
public interface NewWorkerMapper {

    NewWorkerDto toDto(Worker model);

    Worker toModel(NewWorkerDto dto);

    UserDto toUserDto(ServiceUser model);

    ServiceUser toUserModel(UserDto dto);

    @AfterMapping
    default void setWorker(@MappingTarget Worker worker) {
        Optional.ofNullable(worker.getUser())
            .ifPresent(it -> it.setWorker(worker));
    }
}
