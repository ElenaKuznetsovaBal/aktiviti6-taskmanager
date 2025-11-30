package com.aktiviti6.service;

import com.aktiviti6.model.Task;
import com.aktiviti6.model.TaskGroup;
import com.aktiviti6.model.Elena;
import com.aktiviti6.model.Task.TaskStatus;
import com.aktiviti6.repository.TaskRepository;
import com.aktiviti6.repository.TaskGroupRepository;
import com.aktiviti6.repository.ElenaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskGroupRepository taskGroupRepository;
    private final ElenaRepository elenaRepository;

    public TaskService(TaskRepository taskRepository,
                       TaskGroupRepository taskGroupRepository,
                       ElenaRepository elenaRepository) {
        this.taskRepository = taskRepository;
        this.taskGroupRepository = taskGroupRepository;
        this.elenaRepository = elenaRepository;
    }

    public List<Task> getUserTasks(Elena user) {
        return taskRepository.findByUser(user);
    }

    public Optional<Task> getTaskById(Long taskId, Elena user) {
        return taskRepository.findById(taskId)
                .filter(task -> task.getUser().equals(user));
    }

    public Task createTask(Task task, Elena user) {
        task.setUser(user);
        task.setCreatedAt(LocalDateTime.now());

        if (task.getTaskGroup() != null && task.getTaskGroup().getId() != null) {
            TaskGroup group = taskGroupRepository.findById(task.getTaskGroup().getId())
                    .filter(g -> g.getUser().equals(user))
                    .orElseThrow(() -> new RuntimeException("Group not found or access denied"));
            task.setTaskGroup(group);
        }

        return taskRepository.save(task);
    }

    public Task updateTask(Long taskId, Task taskDetails, Elena user) {
        return taskRepository.findById(taskId)
                .filter(task -> task.getUser().equals(user))
                .map(task -> {
                    task.setTitle(taskDetails.getTitle());
                    task.setDescription(taskDetails.getDescription());
                    task.setDueDate(taskDetails.getDueDate());
                    task.setPriority(taskDetails.getPriority());
                    task.setStatus(taskDetails.getStatus());
                    task.setCompleted(taskDetails.isCompleted());

                    if (taskDetails.getTaskGroup() != null) {
                        Optional<TaskGroup> group = taskGroupRepository.findById(taskDetails.getTaskGroup().getId());
                        group.ifPresent(task::setTaskGroup);
                    }

                    return taskRepository.save(task);
                })
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public Task updateTaskStatus(Long taskId, String status, Elena user) {
        return taskRepository.findById(taskId)
                .filter(task -> task.getUser().equals(user))
                .map(task -> {

                    TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
                    task.setStatus(taskStatus);

                    if (TaskStatus.DONE.equals(taskStatus)) {
                        task.setCompleted(true);
                        task.setCompletedAt(LocalDateTime.now());
                    }
                    return taskRepository.save(task);
                })
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public Task updateTaskCompletion(Long taskId, boolean completed, Elena user) {
        return taskRepository.findById(taskId)
                .filter(task -> task.getUser().equals(user))
                .map(task -> {
                    task.setCompleted(completed);
                    if (completed) {
                        task.setCompletedAt(LocalDateTime.now());
                        task.setStatus(TaskStatus.DONE);
                    }
                    return taskRepository.save(task);
                })
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public void deleteTask(Long taskId, Elena user) {
        Task task = taskRepository.findById(taskId)
                .filter(t -> t.getUser().equals(user))
                .orElseThrow(() -> new RuntimeException("Task not found"));
        taskRepository.delete(task);
    }

    public List<Task> getTasksByGroup(Long groupId, Elena user) {
        TaskGroup group = taskGroupRepository.findById(groupId)
                .filter(g -> g.getUser().equals(user))
                .orElseThrow(() -> new RuntimeException("Group not found"));
        return taskRepository.findByTaskGroup(group);
    }

    public List<Task> getTodayTasks(Elena user) {
        return taskRepository.findTodayTasks(user);
    }

    public List<Task> searchTasks(String query, Elena user) {
        return taskRepository.findByUserAndTitleContainingIgnoreCase(user, query);
    }

    public List<Task> getTasksByUserId(Long userId) {
        Elena user = elenaRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return taskRepository.findByUser(user);
    }
}
