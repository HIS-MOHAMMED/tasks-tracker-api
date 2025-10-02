package com.hisham.tasks.services.Impl;

import com.hisham.tasks.domain.entities.Task;
import com.hisham.tasks.domain.entities.TaskList;
import com.hisham.tasks.domain.entities.TaskPriority;
import com.hisham.tasks.domain.entities.TaskStatus;
import com.hisham.tasks.exceptions.TaskAlreadyHasIdException;
import com.hisham.tasks.exceptions.TaskListNotFoundException;
import com.hisham.tasks.exceptions.TaskNotFoundException;
import com.hisham.tasks.repositories.TaskListRepository;
import com.hisham.tasks.repositories.TaskRepository;
import com.hisham.tasks.services.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
        if(task.getId() != null){
            throw new TaskAlreadyHasIdException("task already has an ID!");
        }
        if(task.getTitle() == null || task.getTitle().isBlank()){
            throw new IllegalArgumentException("task must have a title!");
        }

        TaskPriority taskPriority = Optional.ofNullable(task.getTaskPriority()).orElse(TaskPriority.MEDIUM);
        TaskStatus taskStatus = TaskStatus.open;

        TaskList taskList = taskListRepository.findById(taskListId).orElseThrow(() -> new TaskListNotFoundException("Invalid task list ID provided!"));

        LocalDateTime now = LocalDateTime.now();
        return taskRepository.save(new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskPriority,
                taskStatus,
                taskList,
                now,
                now
        ));
    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID taskId) {
           return taskRepository.findByTaskListIdAndId(taskListId, taskId);
    }

    @Transactional
    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {
        if(task.getId() == null){
            throw new IllegalArgumentException("Task must have an ID!");
        }
        if(!Objects.equals(task.getId(), taskId)){
            throw new IllegalArgumentException("Task IDs must match!");
        }
        if(task.getTaskPriority() == null){
            throw new IllegalArgumentException("Task must have a valid priority!");
        }
        if(task.getTaskStatus() == null){
            throw new IllegalArgumentException("Task must have a valid status");
        }

        Task existingTask = taskRepository.findByTaskListIdAndId(taskListId, taskId)
                .orElseThrow(()-> new TaskNotFoundException("Task not found!"));

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setTaskPriority(task.getTaskPriority());
        existingTask.setTaskStatus(task.getTaskStatus());
        existingTask.setUpdatedDate(LocalDateTime.now());

        return taskRepository.save(existingTask);
    }

    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(taskListId, taskId);
    }
}
