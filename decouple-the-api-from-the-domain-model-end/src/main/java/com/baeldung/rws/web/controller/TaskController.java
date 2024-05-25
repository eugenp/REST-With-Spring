package com.baeldung.rws.web.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.baeldung.rws.domain.model.Task;
import com.baeldung.rws.service.TaskService;
import com.baeldung.rws.web.dto.TaskDto;
import com.baeldung.rws.web.dto.TaskDto.TaskUpdateAssigneeValidationData;
import com.baeldung.rws.web.dto.TaskDto.TaskUpdateStatusValidationData;
import com.baeldung.rws.web.dto.TaskDto.TaskUpdateValidationData;
import com.baeldung.rws.web.mapper.NewWorkerMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {

    private TaskService taskService;
    private NewWorkerMapper newWorkerMapper;

    public TaskController(TaskService taskService, NewWorkerMapper newWorkerMapper) {
        this.taskService = taskService;
        this.newWorkerMapper = newWorkerMapper;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto create(@RequestBody @Valid TaskDto newTask) {
        Task model = TaskDto.Mapper.toModel(newTask);
        Task createdModel = this.taskService.save(model);
        return TaskDto.Mapper.toDto(createdModel);
    }

    @PutMapping(value = "/{id}")
    public TaskDto update(@PathVariable Long id, @RequestBody @Validated(TaskUpdateValidationData.class) TaskDto updatedTask) {
        Task model = TaskDto.Mapper.toModel(updatedTask);
        Task createdModel = this.taskService.updateTask(id, model)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return TaskDto.Mapper.toDto(createdModel);
    }

    @PutMapping(value = "/{id}/status")
    public TaskDto updateStatus(@PathVariable Long id, @RequestBody @Validated(TaskUpdateStatusValidationData.class) TaskDto taskWithStatus) {
        Task updatedModel = this.taskService.updateStatus(id, taskWithStatus.status())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return TaskDto.Mapper.toDto(updatedModel);
    }

    @PutMapping(value = "/{id}/assignee")
    public TaskDto updateAssignee(@PathVariable Long id, @RequestBody @Validated(TaskUpdateAssigneeValidationData.class) TaskDto taskWithAssignee) {
        Task updatedModel = this.taskService.updateAssignee(id, newWorkerMapper.toModel(taskWithAssignee.assignee()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return TaskDto.Mapper.toDto(updatedModel);
    }
}
