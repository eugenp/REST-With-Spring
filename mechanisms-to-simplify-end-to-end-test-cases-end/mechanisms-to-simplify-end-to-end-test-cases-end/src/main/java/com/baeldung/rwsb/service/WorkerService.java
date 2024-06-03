package com.baeldung.rwsb.service;

import java.util.Optional;

import com.baeldung.rwsb.domain.model.Worker;

public interface WorkerService {

    Optional<Worker> findById(Long id);

    Worker save(Worker worker);

    Optional<Worker> updateWorker(Long id, Worker worker);
}
