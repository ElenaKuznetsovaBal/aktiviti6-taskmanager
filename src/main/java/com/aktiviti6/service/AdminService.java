package com.aktiviti6.service;

import com.aktiviti6.model.Elena;
import com.aktiviti6.repository.ElenaRepository;
import com.aktiviti6.repository.TaskRepository;
import com.aktiviti6.repository.TaskGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class AdminService {

    private final ElenaRepository elenaRepository;
    private final TaskRepository taskRepository;
    private final TaskGroupRepository taskGroupRepository;

    public AdminService(ElenaRepository elenaRepository,
                        TaskRepository taskRepository,
                        TaskGroupRepository taskGroupRepository) {
        this.elenaRepository = elenaRepository;
        this.taskRepository = taskRepository;
        this.taskGroupRepository = taskGroupRepository;
    }

    public List<Elena> getAllUsers() {
        return elenaRepository.findAll();
    }

    public List<Object[]> getSystemStatistics() {
        return elenaRepository.getUserStatistics();
    }

    public List<Object[]> getGroupStatistics() {
        return taskGroupRepository.getGroupStatistics();
    }

    public Map<String, Object> getAggregatedStatistics() {
        Map<String, Object> stats = new HashMap<>();

        // Статистика по пользователям
        List<Object[]> userStats = elenaRepository.getUserStatistics();
        stats.put("userStatistics", userStats);

        // Статистика по группам
        List<Object[]> groupStats = taskGroupRepository.getGroupStatistics();
        stats.put("groupStatistics", groupStats);

        // Общая статистика
        long totalUsers = elenaRepository.count();
        long totalTasks = taskRepository.count();
        long totalGroups = taskGroupRepository.count();
        long completedTasks = taskRepository.countByCompleted(true);

        stats.put("totalUsers", totalUsers);
        stats.put("totalTasks", totalTasks);
        stats.put("totalGroups", totalGroups);
        stats.put("completedTasks", completedTasks);
        stats.put("completionRate", totalTasks > 0 ? (double) completedTasks / totalTasks * 100 : 0);

        return stats;
    }
}
