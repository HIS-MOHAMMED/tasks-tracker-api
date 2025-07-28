package com.hisham.tasks.mappers;

import com.hisham.tasks.domain.dto.TaskListDto;
import com.hisham.tasks.domain.entities.TaskList;

public interface TaskListMapper {

    TaskList fromDto(TaskListDto taskListDto);
    TaskListDto toDto(TaskList taskList);
}
