package com.hisham.tasks.services;

import com.hisham.tasks.domain.entities.TaskList;
import com.hisham.tasks.repositories.TaskListRepository;
import com.hisham.tasks.services.Impl.TaskListServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskListServiceTests {

    @Mock
    private TaskListRepository taskListRepository;

    @InjectMocks
    private TaskListServiceImpl taskListService;

    @Test
    public void taskListService_CreateTaskList_ReturnTaskListNotNull(){
        TaskList taskList = TaskList.builder()
                .title("Study")
                .description("This is a study task list.").build();

        when(taskListRepository.save(Mockito.any(TaskList.class))).thenReturn(taskList);
        TaskList savedTaskList = taskListService.createTaskList(taskList);

        Assertions.assertThat(savedTaskList).isNotNull();
    }

    @Test
    public void taskListService_ListTaskLists_ReturnTaskLists(){
        TaskList taskList1 = TaskList.builder()
                .title("Study")
                .description("This is a study task list.").build();
        TaskList taskList2 = TaskList.builder()
                .title("Cook")
                .description("This is a Cook task list.").build();
        List<TaskList> taskListList = new ArrayList<>();
        taskListList.add(taskList1);
        taskListList.add(taskList2);

        when(taskListRepository.findAll()).thenReturn(taskListList);
        List<TaskList> returnedTaskLists = taskListService.listTaskLists();

        Assertions.assertThat(returnedTaskLists).isNotNull();
        Assertions.assertThat(returnedTaskLists.size()).isEqualTo(2);
        Assertions.assertThat(returnedTaskLists)
                .extracting(TaskList::getTitle)
                .containsExactly("Study","Cook");
        verify(taskListRepository, times(1)).findAll();
    }

    @Test
    public void taskListService_GetTaskList_ReturnOptionalTaskList(){
        TaskList taskList = TaskList.builder()
                .id(UUID.randomUUID())
                .title("Study")
                .description("This is a study task list.").build();

        when(taskListRepository.findById(any(UUID.class))).thenReturn(Optional.of(taskList));
        TaskList returnedTaskList = taskListService.getTaskList(taskList.getId());

        Assertions.assertThat(returnedTaskList).isNotNull();
        Assertions.assertThat(returnedTaskList.getTitle()).isEqualTo("Study");

    }

    @Test
    public void TaskListService_UpdateTaskList_ReturnTaskList(){
        UUID randomUUID = UUID.randomUUID();
        TaskList taskList1 = TaskList.builder()
                .id(randomUUID)
                .title("Study")
                .description("This is a study task list.").build();
        TaskList taskList2 = TaskList.builder()
                .id(randomUUID)
                .title("English")
                .description("This is a English task list.").build();

        when(taskListRepository.findById(any(UUID.class))).thenReturn(Optional.of(taskList1));
        when(taskListRepository.save(Mockito.any(TaskList.class))).thenReturn(taskList1);

        Optional<TaskList> updatedTaskList = taskListService.updateTaskList(taskList1.getId(),taskList2);

        Assertions.assertThat(updatedTaskList).isNotNull();
        Assertions.assertThat(updatedTaskList.get().getTitle()).isEqualTo("English");
        Assertions.assertThat(updatedTaskList.get().getDescription()).isEqualTo("This is a English task list.");
        verify(taskListRepository, times(1)).findById(taskList1.getId());
        verify(taskListRepository, times(1)).save(taskList1);
    }

    @Test
    public void taskListService_DeleteTaskList_ShouldCallRepositoryDelete(){
        UUID randomId = UUID.randomUUID();

        taskListService.deleteTaskList(randomId);

        verify(taskListRepository, times(1)).deleteById(randomId);
    }
}
