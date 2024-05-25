package com.baeldung.rws.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.rws.domain.model.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    Worker findByUserEmail(String email);
}
