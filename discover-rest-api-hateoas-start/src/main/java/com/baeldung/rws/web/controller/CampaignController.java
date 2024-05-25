package com.baeldung.rws.web.controller;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.baeldung.rws.domain.model.Campaign;
import com.baeldung.rws.service.CampaignService;
import com.baeldung.rws.web.dto.CampaignDto;
import com.baeldung.rws.web.dto.CampaignDto.CampaignUpdateValidationData;
import com.baeldung.rws.web.dto.TaskDto;

@RestController
@RequestMapping(value = "/campaigns")
public class CampaignController {

    private CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping
    public List<CampaignDto> listCampaigns() {
        List<Campaign> models = campaignService.findCampaigns();
        List<CampaignDto> campaignDtos = models.stream()
            .map(Mapper::toDto)
            .collect(Collectors.toList());
        return campaignDtos;
    }

    @GetMapping(value = "/{id}")
    public CampaignDto findOne(@PathVariable Long id) {
        Campaign model = campaignService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return Mapper.toDto(model);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CampaignDto create(@RequestBody @Valid CampaignDto newCampaign) {
        Campaign model = Mapper.toModel(newCampaign);
        Campaign createdModel = this.campaignService.save(model);
        return Mapper.toDto(createdModel);
    }

    @PutMapping(value = "/{id}")
    public CampaignDto update(@PathVariable Long id, @RequestBody @Validated(CampaignUpdateValidationData.class) CampaignDto updatedCampaign) {
        Campaign model = Mapper.toModel(updatedCampaign);
        Campaign createdModel = this.campaignService.updateCampaign(id, model)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return Mapper.toDto(createdModel);
    }

    public static class Mapper {
        public static Campaign toModel(CampaignDto dto) {
            if (dto == null)
                return null;

            Campaign model = new Campaign(dto.getCode(), dto.getName(), dto.getDescription());
            if (!Objects.isNull(dto.getId())) {
                model.setId(dto.getId());
            }

            // we won't allow creating or modifying Tasks via the Campaign
            return model;
        }

        public static CampaignDto toDto(Campaign model) {
            if (model == null)
                return null;
            Set<TaskDto> tasks = model.getTasks()
                    .stream()
                    .map(TaskController.Mapper::toDto)
                    .collect(Collectors.toSet());
            CampaignDto dto = new CampaignDto(model.getId(), model.getCode(), model.getName(), model.getDescription(), tasks);
            return dto;
        }
    }
}
