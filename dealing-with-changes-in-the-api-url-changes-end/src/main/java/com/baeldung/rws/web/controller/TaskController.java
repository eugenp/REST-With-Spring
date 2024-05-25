package com.baeldung.rws.web.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.baeldung.rws.domain.model.Task;
import com.baeldung.rws.service.TaskService;
import com.baeldung.rws.web.dto.TaskDto;
import com.baeldung.rws.web.dto.TaskDto.TaskUpdateAssigneeValidationData;
import com.baeldung.rws.web.dto.TaskDto.TaskUpdateStatusValidationData;
import com.baeldung.rws.web.dto.TaskDto.TaskUpdateValidationData;
import com.baeldung.rws.web.dto.WorkerDto;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // We can map two URLs to the same method - optional params
    // @GetMapping(value = {"/tasks", "campaigns/{campaignId}/tasks"})
    // public List<TaskDto> searchTasksForMultiplePaths(@PathVariable(required = false) Long campaignId, @RequestParam(required = false) String name, @RequestParam(required = false) Long assigneeId) {
    // List<Task> models = taskService.searchTasks(campaignId, name, assigneeId);
    // List<TaskDto> taskDtos = models.stream()
    // .map(TaskDto.Mapper::toDto)
    // .collect(Collectors.toList());
    // return taskDtos;
    // }

    // @Deprecated
    @Operation(deprecated = true, description = "Transitioning to'/campaigns/{campaignId}/tasks'")
    @GetMapping(value = "/tasks")
    public ResponseEntity<CollectionModel<EntityModel<TaskDto>>> searchTasks(@RequestParam(required = false) String name, @RequestParam(required = false) Long assigneeId) {
        HttpHeaders headers = new HttpHeaders();
        ZonedDateTime sunsetDateTime = ZonedDateTime.of(2050, 12, 30, 0, 0, 0, 0, ZoneOffset.UTC);
        String sunsetHeaderValue = sunsetDateTime.format(DateTimeFormatter.RFC_1123_DATE_TIME);
        headers.add("Sunset", sunsetHeaderValue);

        CollectionModel<EntityModel<TaskDto>> taskDtos = processSearch(null, name, assigneeId);
        return ResponseEntity.ok()
            .headers(headers)
            .body(taskDtos);

    }

    @GetMapping(value = "campaigns/{campaignId}/tasks")
    public CollectionModel<EntityModel<TaskDto>> searchTasksByCampaignId(@PathVariable Long campaignId, @RequestParam(required = false) String name, @RequestParam(required = false) Long assigneeId) {
        return processSearch(campaignId, name, assigneeId);
    }

    @GetMapping(value = "/tasks/{id}")
    public TaskDto findOne(@PathVariable Long id) {
        Task model = taskService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return TaskDto.Mapper.toDto(model);
    }

    @PostMapping(value = "tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto create(@RequestBody @Valid TaskDto newTask) {
        Task model = TaskDto.Mapper.toModel(newTask);
        Task createdModel = this.taskService.save(model);
        return TaskDto.Mapper.toDto(createdModel);
    }

    @PutMapping(value = "/tasks/{id}")
    public TaskDto update(@PathVariable Long id, @RequestBody @Validated(TaskUpdateValidationData.class) TaskDto updatedTask) {
        Task model = TaskDto.Mapper.toModel(updatedTask);
        Task createdModel = this.taskService.updateTask(id, model)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return TaskDto.Mapper.toDto(createdModel);
    }

    @PutMapping(value = "/tasks/{id}/status")
    public TaskDto updateStatus(@PathVariable Long id, @RequestBody @Validated(TaskUpdateStatusValidationData.class) TaskDto taskWithStatus) {
        Task updatedModel = this.taskService.updateStatus(id, taskWithStatus.getStatus())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return TaskDto.Mapper.toDto(updatedModel);
    }

    @PutMapping(value = "/tasks/{id}/assignee")
    public TaskDto updateAssignee(@PathVariable Long id, @RequestBody @Validated(TaskUpdateAssigneeValidationData.class) TaskDto taskWithAssignee) {
        Task updatedModel = this.taskService.updateAssignee(id, WorkerDto.Mapper.toModel(taskWithAssignee.getAssignee()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return TaskDto.Mapper.toDto(updatedModel);
    }

    private CollectionModel<EntityModel<TaskDto>> processSearch(Long campaignId, String name, Long assigneeId) {
        List<Task> models = taskService.searchTasks(campaignId, name, assigneeId);
        List<EntityModel<TaskDto>> taskDtos = models.stream()
            .map(TaskDto.Mapper::toDto)
            .map(this::toHateoasDto)
            .collect(Collectors.toList());
        return CollectionModel.of(taskDtos)
            .add(linkTo(methodOn(TaskController.class).searchTasks(name, assigneeId)).withSelfRel()
                .withDeprecation("https://my-site/further-info-on-deprecation"))
            .add(linkTo(methodOn(TaskController.class).searchTasksByCampaignId(null, name, assigneeId)).withRel("searchTasksByCampaign"));
    }

    private EntityModel<TaskDto> toHateoasDto(TaskDto dto) {
        EntityModel<TaskDto> hateoasModel = EntityModel.of(dto, linkTo(methodOn(TaskController.class).findOne(dto.getId())).withSelfRel());

        if (dto.getAssignee() != null) {
            hateoasModel.add(linkTo(methodOn(WorkerController.class).findOne(dto.getAssignee()
                .getId())).withRel("assignee"));
        }
        return hateoasModel;
    }
}
