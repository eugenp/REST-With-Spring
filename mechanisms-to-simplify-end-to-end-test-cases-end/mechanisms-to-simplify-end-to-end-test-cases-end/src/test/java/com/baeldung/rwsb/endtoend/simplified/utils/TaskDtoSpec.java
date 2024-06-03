package com.baeldung.rwsb.endtoend.simplified.utils;

import static com.baeldung.rwsb.commons.endtoend.spec.DtoFieldSpec.from;
import static org.hamcrest.Matchers.equalTo;

import java.time.LocalDate;
import java.util.List;

import org.hamcrest.Matcher;

import com.baeldung.rwsb.commons.endtoend.spec.DtoFieldSpec;
import com.baeldung.rwsb.commons.endtoend.spec.DtoSpec;
import com.baeldung.rwsb.domain.model.TaskStatus;
import com.baeldung.rwsb.web.dto.TaskDto;
import com.baeldung.rwsb.web.dto.WorkerDto;

public final class TaskDtoSpec implements DtoSpec<TaskDto> {

    private DtoFieldSpec<TaskDto, Long> id = from("id", TaskDto::id);

    private DtoFieldSpec<TaskDto, String> uuid = from("uuid", TaskDto::uuid);

    private DtoFieldSpec<TaskDto, String> name = from("name", TaskDto::name);

    private DtoFieldSpec<TaskDto, String> description = from("description", TaskDto::description);

    private DtoFieldSpec<TaskDto, LocalDate> dueDate = from("dueDate", TaskDto::dueDate);

    private DtoFieldSpec<TaskDto, TaskStatus> status = from("status", TaskDto::status);

    private DtoFieldSpec<TaskDto, Long> campaignId = from("campaignId", TaskDto::campaignId);

    private DtoFieldSpec<TaskDto, WorkerDto> assignee = from("assignee", TaskDto::assignee);

    public TaskDtoSpec(Matcher<Long> id, TaskDto dto) {
        super();
        this.id.define(id);
        this.uuid.define(equalTo(dto.uuid()));
        this.name.define(equalTo(dto.name()));
        this.description.define(equalTo(dto.description()));
        this.dueDate.define(equalTo(dto.dueDate()));
        this.status.define(equalTo(dto.status()));
        this.campaignId.define(equalTo(dto.campaignId()));
        this.assignee.define(equalTo(dto.assignee()));
    }

    public TaskDtoSpec(Matcher<Long> id, Matcher<String> uuid, Matcher<String> name, Matcher<String> description, Matcher<LocalDate> dueDate, Matcher<TaskStatus> status, Matcher<Long> campaignId, Matcher<WorkerDto> assignee) {
        super();
        this.id.define(id);
        this.uuid.define(uuid);
        this.name.define(name);
        this.description.define(description);
        this.dueDate.define(dueDate);
        this.status.define(status);
        this.campaignId.define(campaignId);
        this.assignee.define(assignee);
    }

    @Override
    public List<DtoFieldSpec<TaskDto, ?>> defineSpecs() {
        return List.of(this.id, this.uuid, this.name, this.description, this.dueDate, this.status, this.campaignId, this.assignee);
    }

}
