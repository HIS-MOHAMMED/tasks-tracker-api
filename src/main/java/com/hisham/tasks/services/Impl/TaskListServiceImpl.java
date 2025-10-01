package com.hisham.tasks.services.Impl;

import com.hisham.tasks.domain.dto.TaskListDto;
import com.hisham.tasks.domain.entities.TaskList;
import com.hisham.tasks.exceptions.TaskListNotFoundException;
import com.hisham.tasks.repositories.TaskListRepository;
import com.hisham.tasks.services.TaskListService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class
TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository;

    public TaskListServiceImpl(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepository.findAll();
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
       if(taskList.getId() != null){
           throw new IllegalArgumentException("Task list already has an ID!");
       }
       if(taskList.getTitle() == null || taskList.getTitle().isBlank()){
           throw new IllegalArgumentException("Task list must have a title!");
       }
        LocalDateTime now = LocalDateTime.now();
       return taskListRepository.save(new TaskList(
               null,
               taskList.getTitle(),
               taskList.getDescription(),
               null,
               now,
               now

       ));
    }

    @Override
    public TaskList getTaskList(UUID id) {
        return taskListRepository.findById(id).orElseThrow(()-> new TaskListNotFoundException("Task list not found !"));
    }

    @Override
    public Optional<TaskList> updateTaskList(UUID taskListId, TaskList taskList) {
        if(taskList.getId() == null){
            throw new IllegalArgumentException("Task list must have an ID!");
        }
        if(!Objects.equals(taskListId,taskList.getId())){
            throw  new IllegalArgumentException("Attempting to change task list ID, this is not permitted!");
        }

        TaskList existingTaskList = taskListRepository.findById(taskListId).orElseThrow(
                ()-> new IllegalArgumentException("Task list not found!"));

        existingTaskList.setTitle(taskList.getTitle());
        existingTaskList.setDescription(taskList.getDescription());
        existingTaskList.setUpdatedDate(LocalDateTime.now());

        return Optional.of(taskListRepository.save(existingTaskList));
    }

    @Override
    public void deleteTaskList(UUID taskListId) {
        taskListRepository.deleteById(taskListId);
    }
}
