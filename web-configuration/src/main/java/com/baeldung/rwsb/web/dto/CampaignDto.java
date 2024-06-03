package com.baeldung.rwsb.web.dto;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.baeldung.rwsb.domain.model.Campaign;

public record CampaignDto( // @formatter:off

    Long id,

    String code,

    String name,

    String description,

    Set<TaskDto> tasks) { // @formatter:on

    public static class Mapper {
        public static Campaign toModel(CampaignDto dto) {
            if (dto == null)
                return null;

            Campaign model = new Campaign(dto.code(), dto.name(), dto.description());
            if (!Objects.isNull(dto.id())) {
                model.setId(dto.id());
            }
            // we won't allow creating or modifying Tasks via the Campaign
            return model;
        }

        public static CampaignDto toDto(Campaign model) {
            Set<TaskDto> tasks = model.getTasks()
                .stream()
                .map(TaskDto.Mapper::toDto)
                .collect(Collectors.toSet());
            CampaignDto dto = new CampaignDto(model.getId(), model.getCode(), model.getName(), model.getDescription(), tasks);
            return dto;
        }
    }
}
