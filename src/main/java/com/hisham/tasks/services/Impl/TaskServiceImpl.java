package com.hisham.tasks.services.Impl;

import com.hisham.tasks.domain.entities.Task;
import com.hisham.tasks.domain.entities.TaskList;
import com.hisham.tasks.repositories.TaskListRepository;
import com.hisham.tasks.repositories.TaskRepository;
import com.hisham.tasks.services.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class TaskServiceImpl implements TaskService {

   private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> listTasks(UUID taskListId) {
      return taskRepository.findByTaskListId(taskListId);
    }
}
