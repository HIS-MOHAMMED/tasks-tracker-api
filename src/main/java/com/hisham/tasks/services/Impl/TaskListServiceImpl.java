package com.hisham.tasks.services.Impl;

import com.hisham.tasks.domain.entities.TaskList;
import com.hisham.tasks.repositories.TaskListRepository;
import com.hisham.tasks.services.TaskListService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class TaskListServiceImpl implements TaskListService {

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
}
