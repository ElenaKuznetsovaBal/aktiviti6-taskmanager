package com.aktiviti6.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    private LocalDateTime dueDate;

    private String priority;

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.PLANNED;

    private boolean completed = false;

    private LocalDateTime completedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Elena user;

    @ManyToOne
    @JoinColumn(name = "task_group_id")
    private TaskGroup taskGroup;

    public enum TaskStatus {
        PLANNED, IN_PROGRESS, DONE
    }

    // Конструкторы
    public Task() {}

    public Task(String title, String description, LocalDateTime dueDate, String priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
    }
}
