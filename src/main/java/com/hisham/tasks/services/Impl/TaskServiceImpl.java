package com.hisham.tasks.services.Impl;

import com.hisham.tasks.domain.entities.Task;
import com.hisham.tasks.domain.entities.TaskList;
import com.hisham.tasks.domain.entities.TaskStatus;
import com.hisham.tasks.repositories.TaskListRepository;
import com.hisham.tasks.repositories.TaskRepository;
import com.hisham.tasks.services.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class TaskServiceImpl implements TaskService {

   private final TaskRepository taskRepository;
   private final TaskListRepository taskListRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;}

    @Override
    public List<Task> listTasks(UUID taskListId) {
      return taskRepository.findByTaskListId(taskListId);
    }

    @Override
    public Task createTask(UUID taskListId, Task task) {
        TaskList taskList = taskListRepository.findById(taskListId).orElseThrow(
                ()-> new IllegalArgumentException("Task list not found!"));
        LocalDateTime now = LocalDateTime.now();
        return taskRepository.save(new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getTaskPriority(),
                TaskStatus.open,
                taskList,
                now,
                now
        ));
    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID taskId) {
           return taskRepository.findByTaskListIdAndId(taskListId, taskId);
    }
}
