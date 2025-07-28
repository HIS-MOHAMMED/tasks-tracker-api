package com.hisham.tasks.mappers;

import com.hisham.tasks.domain.dto.TaskDto;
import com.hisham.tasks.domain.entities.Task;

public interface TaskMapper {

    Task fromDto(TaskDto taskDto);
    TaskDto toDto(Task task);
}
