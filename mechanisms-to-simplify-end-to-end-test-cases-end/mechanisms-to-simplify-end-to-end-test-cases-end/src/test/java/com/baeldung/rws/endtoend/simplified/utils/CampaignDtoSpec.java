package com.baeldung.rws.endtoend.simplified.utils;

import static com.baeldung.rws.commons.endtoend.spec.DtoFieldSpec.from;

import java.util.List;

import org.hamcrest.Matcher;

import com.baeldung.rws.commons.endtoend.spec.DtoFieldSpec;
import com.baeldung.rws.commons.endtoend.spec.DtoSpec;
import com.baeldung.rws.web.dto.CampaignDto;
import com.baeldung.rws.web.dto.TaskDto;

public final class CampaignDtoSpec implements DtoSpec<CampaignDto> {

    private DtoFieldSpec<CampaignDto, Long> id = from("id", CampaignDto::id);

    private DtoFieldSpec<CampaignDto, String> code = from("code", CampaignDto::code);

    private DtoFieldSpec<CampaignDto, String> name = from("name", CampaignDto::name);

    private DtoFieldSpec<CampaignDto, String> description = from("description", CampaignDto::description);

    private DtoFieldSpec<CampaignDto, Iterable<? extends TaskDto>> tasks = from("tasks", CampaignDto::tasks);

    public CampaignDtoSpec(Matcher<Long> id, Matcher<String> code, Matcher<String> name, Matcher<String> description, Matcher<Iterable<? extends TaskDto>> tasks) {
        super();
        this.id.define(id);
        this.code.define(code);
        this.name.define(name);
        this.description.define(description);
        this.tasks.define(tasks);
    }

    @Override
    public List<DtoFieldSpec<CampaignDto, ?>> defineSpecs() {
        return List.of(this.id, this.code, this.name, this.description, this.tasks);
    }
}
