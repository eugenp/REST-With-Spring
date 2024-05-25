package com.baeldung.rws.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.baeldung.rws.domain.model.Worker;
import com.baeldung.rws.persistence.repository.WorkerRepository;
import com.baeldung.rws.service.WorkerService;

@Service
public class DefaultWorkerService implements WorkerService {
    private WorkerRepository workerRepository;

    public DefaultWorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @Override
    public Optional<Worker> findById(Long id) {
        return workerRepository.findById(id);
    }

    @Override
    public Worker save(Worker worker) {
        worker.setId(null);
        return workerRepository.save(worker);
    }

    @Override
    public Optional<Worker> updateWorker(Long id, Worker worker) {
        return workerRepository.findById(id)
            .map(base -> updateFields(base, worker))
            .map(workerRepository::save);
    }

    private Worker updateFields(Worker base, Worker updatedWorker) {
        base.setFirstName(updatedWorker.getFirstName());
        base.setLastName(updatedWorker.getLastName());
        return base;
    }
}
