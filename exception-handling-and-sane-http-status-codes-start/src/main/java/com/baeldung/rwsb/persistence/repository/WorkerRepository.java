package com.baeldung.rwsb.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.rwsb.domain.model.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

}
