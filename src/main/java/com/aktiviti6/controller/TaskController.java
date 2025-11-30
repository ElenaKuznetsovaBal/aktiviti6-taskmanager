package com.aktiviti6.controller;

import com.aktiviti6.model.Task;
import com.aktiviti6.model.Elena;
import com.aktiviti6.service.TaskService;
import com.aktiviti6.service.ElenaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final ElenaService elenaService;

    public TaskController(TaskService taskService, ElenaService elenaService) {
        this.taskService = taskService;
        this.elenaService = elenaService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getUserTasks(@AuthenticationPrincipal String userEmail) {
        Elena user = elenaService.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Task> tasks = taskService.getUserTasks(user);
        return ResponseEntity.ok(tasks);
    }
}
