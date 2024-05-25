package com.baeldung.rws.web.mapper;

import java.util.Optional;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.baeldung.rws.domain.model.Worker;
import com.baeldung.rws.web.dto.OldWorkerDto;

@Mapper(componentModel = "spring")
public interface OldWorkerMapper {
    @Mapping(target = "email", source = "model.user.email")
    OldWorkerDto toDto(Worker model);

    @Mapping(target = "user.id", source = "dto.id")
    @Mapping(target = "user.email", source = "dto.email")
    Worker toModel(OldWorkerDto dto);

    @AfterMapping
    default void setWorker(@MappingTarget Worker worker) {
        Optional.ofNullable(worker.getUser())
            .ifPresent(it -> it.setWorker(worker));
    }
}
