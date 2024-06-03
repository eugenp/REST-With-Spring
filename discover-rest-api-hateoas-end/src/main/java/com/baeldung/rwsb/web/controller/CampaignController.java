package com.baeldung.rwsb.web.controller;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.baeldung.rwsb.domain.model.Campaign;
import com.baeldung.rwsb.service.CampaignService;
import com.baeldung.rwsb.web.dto.CampaignDto;
import com.baeldung.rwsb.web.dto.TaskDto;
import com.baeldung.rwsb.web.dto.CampaignDto.CampaignUpdateValidationData;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/campaigns")
public class CampaignController {

    private CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping
    public CollectionModel<EntityModel<CampaignDto>> listCampaigns() {
        List<Campaign> models = campaignService.findCampaigns();
        List<EntityModel<CampaignDto>> campaignDtos = models.stream()
            .map(Mapper::toDto)
            .collect(Collectors.toList());
        return CollectionModel.of(campaignDtos)
                .add(generateCampaignCollectionSelfLink());
    }

    @GetMapping(value = "/{id}")
    public EntityModel<CampaignDto> findOne(@PathVariable Long id) {
        Campaign model = campaignService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return Mapper.toDto(model);
    }

    @PostMapping
    public ResponseEntity<EntityModel<CampaignDto>> create(@RequestBody @Valid CampaignDto newCampaign) {
        Campaign model = Mapper.toModel(newCampaign);
        Campaign createdModel = this.campaignService.save(model);
        EntityModel<CampaignDto> dto = Mapper.toDto(createdModel);
        return ResponseEntity.created(generateCampaignSelfLink(dto.getContent()).toUri())
                .body(dto);
    }

    @PutMapping(value = "/{id}")
    public EntityModel<CampaignDto> update(@PathVariable Long id, @RequestBody @Validated(CampaignUpdateValidationData.class) CampaignDto updatedCampaign) {
        Campaign model = Mapper.toModel(updatedCampaign);
        Campaign createdModel = this.campaignService.updateCampaign(id, model)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return Mapper.toDto(createdModel);
    }

    private static Link generateCampaignSelfLink(CampaignDto dto) {
        return linkTo(methodOn(CampaignController.class).findOne(dto.getId())).withSelfRel();
    }

    private static Link generateCampaignCollectionSelfLink() {
        return linkTo(methodOn(CampaignController.class).listCampaigns()).withSelfRel();
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

        public static EntityModel<CampaignDto> toDto(Campaign model) {
            if (model == null)
                return null;
            Set<TaskDto> tasks = model.getTasks()
                    .stream()
                    .map(TaskController.Mapper::toDto)
                    .collect(Collectors.toSet());
            CampaignDto dto = new CampaignDto(model.getId(), model.getCode(), model.getName(), model.getDescription(), tasks);
            return EntityModel.of(dto, generateCampaignSelfLink(dto));
        }
    }
}
