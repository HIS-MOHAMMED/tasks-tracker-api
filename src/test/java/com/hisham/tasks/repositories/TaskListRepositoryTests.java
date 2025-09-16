package com.hisham.tasks.repositories;

import com.hisham.tasks.domain.entities.TaskList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TaskListRepositoryTests {
    @Autowired
    private TaskListRepository taskListRepository;

    @Test
    public void TaskListRepository_Save_ReturnSavedTaskList(){
        TaskList taskList = TaskList.builder()
                .title("Study")
                .description("This is study task list.")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now()).build();

        TaskList savedTaskList = taskListRepository.save(taskList);

        Assertions.assertThat(savedTaskList).isNotNull();
        Assertions.assertThat(savedTaskList.getTitle()).isEqualTo("Study");
        Assertions.assertThat(savedTaskList.getDescription()).isEqualTo("This is study task list.");
    }

    @Test
    public void TaskListRepository_FindAll_returnMoreThanOneTaskList(){
        TaskList taskList1 = TaskList.builder()
                .title("Cook")
                .description("This is Cook task list.")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now()).build();

        TaskList taskList2 = TaskList.builder()
                .title("Study")
                .description("This is Study task list.")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now()).build();

        taskListRepository.save(taskList1);
        taskListRepository.save(taskList2);

        List<TaskList> taskLists = taskListRepository.findAll();

        Assertions.assertThat(taskLists).isNotNull();
        Assertions.assertThat(taskLists.size()).isEqualTo(2);
    }

    @Test
    public void TaskListRepository_FindById_returnTaskList(){
        TaskList taskList = TaskList.builder()
                .title("Cook")
                .description("This is Cook task list.")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now()).build();

        taskListRepository.save(taskList);
        TaskList returnedTaskList = taskListRepository.findById(taskList.getId()).get();

        Assertions.assertThat(returnedTaskList).isNotNull();
    }

    @Test
    public void TaskListRepository_UpdateTaskList_ReturnTaskListNotNull(){
        TaskList taskList = TaskList.builder()
                .title("Cook")
                .description("This is Cook task list.")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now()).build();
        taskListRepository.save(taskList);

        TaskList taskListSave = taskListRepository.findById(taskList.getId()).get();
        taskListSave.setTitle("Praying");
        taskListSave.setDescription("This is Praying task list");

        TaskList updatedTaskList = taskListRepository.save(taskListSave);

        Assertions.assertThat(updatedTaskList.getTitle()).isNotNull();
        Assertions.assertThat(updatedTaskList.getTitle()).isEqualTo("Praying");
        Assertions.assertThat(updatedTaskList.getDescription()).isNotNull();
        Assertions.assertThat(updatedTaskList.getDescription()).isEqualTo("This is Praying task list");
    }

    @Test
    public void TaskListRepository_DeleteById_ReturnTaskListIsEmpty(){
        TaskList taskList = TaskList.builder()
                .title("Cook")
                .description("This is Cook task list.")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now()).build();

        taskListRepository.save(taskList);
        taskListRepository.deleteById(taskList.getId());
        Optional<TaskList> deletedTaskList = taskListRepository.findById(taskList.getId());

        Assertions.assertThat(deletedTaskList).isEmpty();
    }
}
