package com.baeldung.rws.web.v2_url.controller;

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

import com.baeldung.rws.domain.model.Worker;
import com.baeldung.rws.service.WorkerService;
import com.baeldung.rws.web.v2_url.dto.WorkerDto;
import com.baeldung.rws.web.v2_url.dto.WorkerDto.WorkerUpdateValidationData;

import jakarta.validation.Valid;

@RestController(value = "workerController.url.v2")
@RequestMapping(value = "/v2/workers")
public class WorkerController {

    private WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @GetMapping(value = "/{id}")
    public WorkerDto findOne(@PathVariable Long id) {
        Worker model = workerService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return WorkerDto.Mapper.toDto(model);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkerDto create(@RequestBody @Valid WorkerDto newWorker) {
        Worker model = WorkerDto.Mapper.toModel(newWorker);
        Worker createdModel = this.workerService.save(model);
        return WorkerDto.Mapper.toDto(createdModel);
    }

    @PutMapping(value = "/{id}")
    public WorkerDto update(@PathVariable Long id, @RequestBody @Validated(WorkerUpdateValidationData.class) WorkerDto updatedWorker) {
        Worker model = WorkerDto.Mapper.toModel(updatedWorker);
        Worker createdModel = this.workerService.updateWorker(id, model)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return WorkerDto.Mapper.toDto(createdModel);
    }
}
