package com.baeldung.rws.service;

import java.util.List;
import java.util.Optional;

import com.baeldung.rws.domain.model.Worker;

public interface WorkerService {

    List<Worker> findWorkers();

    Optional<Worker> findById(Long id);

    Worker save(Worker worker);

    Optional<Worker> updateWorker(Long id, Worker worker);
}
