package com.hisham.tasks.services;

import com.hisham.tasks.domain.entities.TaskList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskListService {
    List<TaskList>  listTaskLists();
    TaskList createTaskList(TaskList taskList);
}
