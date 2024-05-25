package com.baeldung.rws.service;

import java.util.List;
import java.util.Optional;

import com.baeldung.rws.domain.model.Task;
import com.baeldung.rws.domain.model.TaskStatus;
import com.baeldung.rws.domain.model.Worker;

public interface TaskService {

    List<Task> searchTasks(Long campaignId, String nameSubstring, Long assigneeId);

    Optional<Task> findById(Long id);

    Task save(Task task);

    Optional<Task> updateTask(Long id, Task task);

    Optional<Task> updateStatus(Long id, TaskStatus status);

    Optional<Task> updateAssignee(Long id, Worker assignee);
}
