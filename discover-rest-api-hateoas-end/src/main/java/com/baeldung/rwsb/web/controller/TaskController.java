package com.baeldung.rwsb.web.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.hateoas.CollectionModel;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.baeldung.rwsb.domain.model.Campaign;
import com.baeldung.rwsb.domain.model.Task;
import com.baeldung.rwsb.service.TaskService;
import com.baeldung.rwsb.web.dto.TaskDto;
import com.baeldung.rwsb.web.dto.WorkerDto;
import com.baeldung.rwsb.web.dto.TaskDto.TaskUpdateAssigneeValidationData;
import com.baeldung.rwsb.web.dto.TaskDto.TaskUpdateStatusValidationData;
import com.baeldung.rwsb.web.dto.TaskDto.TaskUpdateValidationData;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public CollectionModel<TaskDto> searchTasks(@RequestParam(required = false) String name, @RequestParam(required = false) Long assigneeId) {
        List<Task> models = taskService.searchTasks(name, assigneeId);
        List<TaskDto> taskDtos = models.stream()
            .map(Mapper::toDto)
            .collect(Collectors.toList());
        return CollectionModel.of(taskDtos)
                .add(generateTaskCollectionSelfLink(name, assigneeId));
    }

    @GetMapping(value = "/{id}")
    public TaskDto findOne(@PathVariable Long id) {
        Task model = taskService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return Mapper.toDto(model);
    }

    @PostMapping
    public ResponseEntity<TaskDto> create(@RequestBody @Valid TaskDto newTask) {
        Task model = Mapper.toModel(newTask);
        Task createdModel = this.taskService.save(model);
        TaskDto dto = Mapper.toDto(createdModel);
        return ResponseEntity.created(generateTaskSelfLink(dto).toUri())
                .body(dto);
    }

    @PutMapping(value = "/{id}")
    public TaskDto update(@PathVariable Long id, @RequestBody @Validated(TaskUpdateValidationData.class) TaskDto updatedTask) {
        Task model = Mapper.toModel(updatedTask);
        Task createdModel = this.taskService.updateTask(id, model)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return Mapper.toDto(createdModel);
    }

    @PutMapping(value = "/{id}/status")
    public TaskDto updateStatus(@PathVariable Long id, @RequestBody @Validated(TaskUpdateStatusValidationData.class) TaskDto taskWithStatus) {
        Task updatedModel = this.taskService.updateStatus(id, taskWithStatus.getStatus())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return Mapper.toDto(updatedModel);
    }

    @PutMapping(value = "/{id}/assignee")
    public TaskDto updateAssignee(@PathVariable Long id, @RequestBody @Validated(TaskUpdateAssigneeValidationData.class) TaskDto taskWithAssignee) {
        Task updatedModel = this.taskService.updateAssignee(id, WorkerController.Mapper.toModel(taskWithAssignee.getAssignee()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return Mapper.toDto(updatedModel);
    }

    private static Link generateTaskSelfLink(TaskDto dto) {
        return linkTo(methodOn(TaskController.class).findOne(dto.getId())).withSelfRel();
    }

    private static Link generateTaskCollectionSelfLink(String name, Long assigneeId) {
        return linkTo(methodOn(TaskController.class).searchTasks(name, assigneeId)).withSelfRel();
    }

    public static class Mapper {
        public static Task toModel(TaskDto dto) {
            if (dto == null)
                return null;

            Campaign campaign = new Campaign();
            campaign.setId(dto.getCampaignId());

            Task model = new Task(dto.getName(), dto.getDescription(), dto.getDueDate(), campaign, dto.getStatus(),
                    WorkerController.Mapper.toModel(dto.getAssignee()), dto.getUuid());
            if (!Objects.isNull(dto.getId())) {
                model.setId(dto.getId());
            }

            // we won't allow creating or modifying Campaigns via a Task
            return model;
        }

        public static TaskDto toDto(Task model) {
            if (model == null)
                return null;
            WorkerController.WorkerModelAssembler workerAssembler = new WorkerController.WorkerModelAssembler();
            WorkerDto workerDto = workerAssembler.toModel(model.getAssignee());
            TaskDto dto = new TaskDto(model.getId(), model.getUuid(), model.getName(), model.getDescription(), model.getDueDate(),
                    model.getStatus(), model.getCampaign().getId(), workerDto);
            return dto.add(generateTaskSelfLink(dto));
        }
    }
}
