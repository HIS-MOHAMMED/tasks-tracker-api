package com.hisham.tasks.domain.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="task_lists")
@AllArgsConstructor
@Data
public class TaskList {

    @Id
    @Column(name="id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="description")
    private String description;

    @OneToMany(mappedBy = "task_list", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Task> tasks;

    @Column(name="created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name="updated_date", nullable = false)
    private LocalDateTime updatedDate;
}
