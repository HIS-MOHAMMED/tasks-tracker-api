package com.hisham.tasks.controllers;

import com.hisham.tasks.domain.dto.TaskDto;
import com.hisham.tasks.mappers.TaskMapper;
import com.hisham.tasks.services.TaskListService;
import com.hisham.tasks.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/task-lists/{task_list_id}/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final TaskListService taskListService;

    public TaskController(TaskService taskService, TaskMapper taskMapper, TaskListService taskListService) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
        this.taskListService = taskListService;
    }

    @GetMapping
    public List<TaskDto> listTasks(@PathVariable("task_list_id")UUID taskListId){
        return taskService.listTasks(taskListId)
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @PostMapping
    public TaskDto createTask(@PathVariable("task_list_id") UUID taskListId, @RequestBody TaskDto taskDto){
        return taskMapper.toDto(taskService
                .createTask(taskListId, taskMapper
                        .fromDto(taskDto)));
    }
    @GetMapping(path = "/{task_id}")
    public ResponseEntity<Optional<TaskDto>> getTask(@PathVariable("task_list_id") UUID taskListId, @PathVariable("task_id") UUID taskId){
        return ResponseEntity.ok(taskService
                .getTask(taskListId, taskId)
                .map(taskMapper::toDto));
    }
}
