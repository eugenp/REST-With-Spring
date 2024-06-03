package com.baeldung.rwsb.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.baeldung.rwsb.domain.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t WHERE (:name IS NULL or t.name like %:name%) and (:assigneeId IS NULL or t.assignee.id = :assigneeId)")
    List<Task> findByNameContainingAndAssigneeId(@Param("name") String name, @Param("assigneeId") Long assigneeId);
}
