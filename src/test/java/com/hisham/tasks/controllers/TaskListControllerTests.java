package com.hisham.tasks.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hisham.tasks.domain.dto.TaskListDto;
import com.hisham.tasks.domain.entities.TaskList;
import com.hisham.tasks.exceptions.TaskListNotFoundException;
import com.hisham.tasks.mappers.TaskListMapper;
import com.hisham.tasks.services.Impl.TaskListServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.expression.spel.ast.OpPlus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TaskListController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TaskListControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskListServiceImpl taskListService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TaskListMapper taskListMapper;

    private TaskList taskList;
    private TaskListDto taskListDto;


    @BeforeEach
    public void init(){
        taskList = TaskList.builder()
                .id(UUID.randomUUID())
                .title("Study")
                .description("This is a study task list.").build();
        taskListDto = TaskListDto.builder()
                .id(taskList.getId())
                .title("Study")
                .description("This is a study task list.").build();
    }

    @Test
    public  void taskListController_CreateTaskList_ReturnTaskListDto() throws Exception{
        TaskListDto taskListDto1 = TaskListDto.builder()
                .id(taskList.getId())
                        .title("Study")
                                .description("This is a study task list.").build();

        given(taskListMapper.fromDto(any(TaskListDto.class))).willReturn(taskList);
        given(taskListService.createTaskList(any(TaskList.class))).willReturn(taskList);
        given(taskListMapper.toDto(any(TaskList.class))).willReturn(taskListDto1);


        ResultActions resultActions = mockMvc.perform(post("/api/task-lists")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskListDto)));

        resultActions.andExpect(MockMvcResultMatchers
                .status().isOk())
                .andExpect(jsonPath("$.title").value("Study"))
                .andExpect(jsonPath("$.description").value("This is a study task list."));;
    }

    @Test
    public void TaskListController_ListTaskLists_ReturnListOfTaskLists() throws Exception{
        List<TaskList> taskListsList = new ArrayList<>();
        taskListsList.add(taskList);

        given(taskListService.listTaskLists()).willReturn(taskListsList);
        given(taskListMapper.toDto(any(TaskList.class))).willReturn(taskListDto);

        ResultActions resultActions = mockMvc.perform(get("/api/task-lists")
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers
                .status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Study"))
                .andExpect(jsonPath("$[0].description").value("This is a study task list."));
    }

    @Test
    public void TaskListController_GetTaskList_ReturnOptionalOfTaskList() throws  Exception{
        given(taskListMapper.toDto(any(TaskList.class))).willReturn(taskListDto);
        given(taskListService.getTaskList(any(UUID.class))).willReturn(Optional.of(taskList));

        ResultActions resultActions = mockMvc.perform(get("/api/task-lists/" + taskList.getId())
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.title").value("Study"))
                .andExpect(jsonPath("$.description").value("This is a study task list."));
    }

    @Test
    public void TaskListController_GetTaskList_ReturnNotFound() throws  Exception{
        given(taskListService.getTaskList(any(UUID.class))).willReturn(Optional.empty());

        ResultActions resultActions = mockMvc.perform(get("/api/task-lists/" + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void TaskListController_UpdateTaskList_ReturnTaskListDto() throws Exception{
        TaskList updatedTaskList = TaskList.builder()
                .id(taskList.getId())
                .title("Learn")
                .description("This is a learn task list.").build();

        TaskListDto updatedTaskListDto = TaskListDto.builder()
                 .id(taskList.getId())
                 .title("Learn")
                 .description("This is a learn task list.").build();

        given(taskListMapper.fromDto(any(TaskListDto.class))).willReturn(taskList);
        given(taskListService.updateTaskList(any(UUID.class),any(TaskList.class)))
                .willReturn(Optional.ofNullable(updatedTaskList));
        given(taskListMapper.toDto(any(TaskList.class))).willReturn(updatedTaskListDto);

        ResultActions resultActions = mockMvc.perform(put("/api/task-lists/" + taskList.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskListDto))
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.title").value("Learn"))
                .andExpect(jsonPath("$.description").value("This is a learn task list."));
    }

    @Test
    public void TaskListController_UpdateTaskList_ReturnNotFound() throws Exception{
        given(taskListService.updateTaskList(any(UUID.class),any(TaskList.class)))
                .willReturn(Optional.empty());

        ResultActions resultActions = mockMvc.perform(put("/api/task-lists/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskListDto))
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void TaskListController_DeleteTaskList_ReturnNotContent() throws  Exception{
        UUID randomId = UUID.randomUUID();
        doNothing().when(taskListService).deleteTaskList(randomId);

        mockMvc.perform(delete("/api/task-lists/" + randomId))
                .andExpect(status().isNoContent());

        verify(taskListService, times(1)).deleteTaskList(randomId);
    }

    @Test
    public void TaskListController_DeleteTaskList_ReturnNotFound() throws  Exception{
        UUID randomId = UUID.randomUUID();
        doThrow(new TaskListNotFoundException("task list not found!")).when(taskListService).deleteTaskList(randomId);

        mockMvc.perform(delete("/api/task-lists/" + randomId))
                .andExpect(status().isNotFound());

        verify(taskListService, times(1)).deleteTaskList(randomId);
    }
}
