package com.aktiviti6.controller;

import com.aktiviti6.dto.ApiResponse;
import com.aktiviti6.model.Elena;
import com.aktiviti6.service.AdminService;
import com.aktiviti6.service.ElenaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final ElenaService elenaService;

    public AdminController(AdminService adminService, ElenaService elenaService) {
        this.adminService = adminService;
        this.elenaService = elenaService;
    }

    // 1. Просмотр списка пользователей
    @GetMapping("/users")
    public ResponseEntity<List<Elena>> getAllUsers() {
        List<Elena> users = adminService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // 2. Просмотр всех групп задач и всех задач в системе
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getSystemStatistics() {
        Map<String, Object> statistics = adminService.getAggregatedStatistics();
        return ResponseEntity.ok(statistics);
    }

    // 3. Просмотр задач выбранного пользователя
    @GetMapping("/users/{userId}/tasks")
    public ResponseEntity<?> getUserTasks(@PathVariable Long userId) {
        try {
            Elena user = elenaService.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            // Здесь нужно добавить метод в TaskService для получения задач по ID пользователя
            // List<Task> tasks = taskService.getTasksByUserId(userId);
            // return ResponseEntity.ok(tasks);
            return ResponseEntity.ok(ApiResponse.success("Tasks for user: " + user.getEmail()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    // 4. Просмотр простой агрегированной статистики
    @GetMapping("/stats/users")
    public ResponseEntity<List<Object[]>> getUserStatistics() {
        List<Object[]> userStats = adminService.getSystemStatistics();
        return ResponseEntity.ok(userStats);
    }

    @GetMapping("/stats/groups")
    public ResponseEntity<List<Object[]>> getGroupStatistics() {
        List<Object[]> groupStats = adminService.getGroupStatistics();
        return ResponseEntity.ok(groupStats);
    }
}
