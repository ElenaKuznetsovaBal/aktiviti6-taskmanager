package com.aktiviti6.controller;

import com.aktiviti6.model.TaskGroup;
import com.aktiviti6.model.Elena;
import com.aktiviti6.service.TaskGroupService;
import com.aktiviti6.service.ElenaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class TaskGroupController {

    private final TaskGroupService taskGroupService;
    private final ElenaService elenaService;

    public TaskGroupController(TaskGroupService taskGroupService, ElenaService elenaService) {
        this.taskGroupService = taskGroupService;
        this.elenaService = elenaService;
    }

    @GetMapping
    public ResponseEntity<List<TaskGroup>> getUserGroups(@AuthenticationPrincipal String userEmail) {
        Elena user = elenaService.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<TaskGroup> groups = taskGroupService.getUserGroups(user);
        return ResponseEntity.ok(groups);
    }
}
