package com.baeldung.rwsb.web.controller;

import java.util.List;
import java.util.Objects;

import jakarta.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
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

import com.baeldung.rwsb.domain.model.Worker;
import com.baeldung.rwsb.service.WorkerService;
import com.baeldung.rwsb.web.dto.WorkerDto;
import com.baeldung.rwsb.web.dto.WorkerDto.WorkerUpdateValidationData;

@RestController
@RequestMapping(value = "/workers")
public class WorkerController {

    private WorkerService workerService;

    private WorkerModelAssembler assembler = new WorkerModelAssembler();

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @GetMapping(value = "/{id}")
    public WorkerDto findOne(@PathVariable Long id) {
        Worker model = workerService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return assembler.toModel(model);
    }

    @GetMapping
    public CollectionModel<WorkerDto> listWorkers() {
        List<Worker> models = workerService.findWorkers();
        return assembler.toCollectionModel(models);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkerDto create(@RequestBody @Valid WorkerDto newWorker) {
        Worker model = Mapper.toModel(newWorker);
        Worker createdModel = this.workerService.save(model);
        return assembler.toModel(createdModel);
    }

    @PutMapping(value = "/{id}")
    public WorkerDto update(@PathVariable Long id, @RequestBody @Validated(WorkerUpdateValidationData.class) WorkerDto updatedWorker) {
        Worker model = Mapper.toModel(updatedWorker);
        Worker createdModel = this.workerService.updateWorker(id, model)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return assembler.toModel(createdModel);
    }

    public static class WorkerModelAssembler extends RepresentationModelAssemblerSupport<Worker, WorkerDto> {

        public WorkerModelAssembler() {
            super(WorkerController.class, WorkerDto.class);
        }

        @Override
        public WorkerDto toModel(Worker worker) {
            if (worker == null)
                return null;
            return this.createModelWithId(worker.getId(), worker);
        }

        @Override
        protected WorkerDto instantiateModel(Worker worker) {
            return Mapper.toDto(worker);
        }
    }

    public static class Mapper {
        public static Worker toModel(WorkerDto dto) {
            if (dto == null)
                return null;
            Worker model = new Worker(dto.getEmail(), dto.getFirstName(), dto.getLastName());
            if (!Objects.isNull(dto.getId())) {
                model.setId(dto.getId());
            }

            return model;
        }

        public static WorkerDto toDto(Worker model) {
            if (model == null)
                return null;
            WorkerDto dto = new WorkerDto(model.getId(), model.getEmail(), model.getFirstName(), model.getLastName());
            return dto;
        }
    }
}
