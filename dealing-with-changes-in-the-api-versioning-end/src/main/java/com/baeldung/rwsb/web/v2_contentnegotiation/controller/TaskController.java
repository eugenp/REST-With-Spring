package com.baeldung.rwsb.web.v2_contentnegotiation.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.baeldung.rwsb.domain.model.Task;
import com.baeldung.rwsb.service.TaskService;
import com.baeldung.rwsb.web.v2_contentnegotiation.dto.TaskDto;
import com.baeldung.rwsb.web.v2_contentnegotiation.dto.WorkerDto;
import com.baeldung.rwsb.web.v2_contentnegotiation.dto.TaskDto.TaskUpdateAssigneeValidationData;
import com.baeldung.rwsb.web.v2_contentnegotiation.dto.TaskDto.TaskUpdateStatusValidationData;
import com.baeldung.rwsb.web.v2_contentnegotiation.dto.TaskDto.TaskUpdateValidationData;

@RestController(value = "taskController.contentNegotiation.v2")
@RequestMapping(value = "/tasks", produces = "application/vnd.rwsb.api.v2+json")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDto> searchTasks(@RequestParam(required = false) String name, @RequestParam(required = false) Long assigneeId) {
        List<Task> models = taskService.searchTasks(name, assigneeId);
        List<TaskDto> taskDtos = models.stream()
            .map(TaskDto.Mapper::toDto)
            .collect(Collectors.toList());
        return taskDtos;
    }

    @GetMapping(value = "/{id}")
    public TaskDto findOne(@PathVariable Long id) {
        Task model = taskService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return TaskDto.Mapper.toDto(model);
    }

    @PostMapping(consumes = "application/vnd.rwsb.api.v2+json")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto create(@RequestBody @Valid TaskDto newTask) {
        Task model = TaskDto.Mapper.toModel(newTask);
        Task createdModel = this.taskService.save(model);
        return TaskDto.Mapper.toDto(createdModel);
    }

    @PutMapping(value = "/{id}", consumes = "application/vnd.rwsb.api.v2+json")
    public TaskDto update(@PathVariable Long id, @RequestBody @Validated(TaskUpdateValidationData.class) TaskDto updatedTask) {
        Task model = TaskDto.Mapper.toModel(updatedTask);
        Task createdModel = this.taskService.updateTask(id, model)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return TaskDto.Mapper.toDto(createdModel);
    }

    @PutMapping(value = "/{id}/status", consumes = "application/vnd.rwsb.api.v2+json")
    public TaskDto updateStatus(@PathVariable Long id, @RequestBody @Validated(TaskUpdateStatusValidationData.class) TaskDto taskWithStatus) {
        Task updatedModel = this.taskService.updateStatus(id, taskWithStatus.status())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return TaskDto.Mapper.toDto(updatedModel);
    }

    @PutMapping(value = "/{id}/assignee", consumes = "application/vnd.rwsb.api.v2+json")
    public TaskDto updateAssignee(@PathVariable Long id, @RequestBody @Validated(TaskUpdateAssigneeValidationData.class) TaskDto taskWithAssignee) {
        Task updatedModel = this.taskService.updateAssignee(id, WorkerDto.Mapper.toModel(taskWithAssignee.assignee()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return TaskDto.Mapper.toDto(updatedModel);
    }
}
