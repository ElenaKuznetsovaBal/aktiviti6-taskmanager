package com.aktiviti6.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task_groups")
@Data
public class TaskGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Elena user;

    @OneToMany(mappedBy = "taskGroup", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

    // Конструкторы
    public TaskGroup() {}

    public TaskGroup(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
