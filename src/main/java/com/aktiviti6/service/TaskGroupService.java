package com.aktiviti6.service;

import com.aktiviti6.model.TaskGroup;
import com.aktiviti6.model.Elena;
import com.aktiviti6.repository.TaskGroupRepository;
import com.aktiviti6.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskGroupService {

    private final TaskGroupRepository taskGroupRepository;
    private final TaskRepository taskRepository;

    public TaskGroupService(TaskGroupRepository taskGroupRepository, TaskRepository taskRepository) {
        this.taskGroupRepository = taskGroupRepository;
        this.taskRepository = taskRepository;
    }

    public List<TaskGroup> getUserGroups(Elena user) {
        return taskGroupRepository.findByUser(user);
    }

    public Optional<TaskGroup> getGroupById(Long groupId, Elena user) {
        return taskGroupRepository.findById(groupId)
                .filter(group -> group.getUser().equals(user));
    }

    public TaskGroup createGroup(TaskGroup taskGroup, Elena user) {
        if (taskGroupRepository.existsByNameAndUser(taskGroup.getName(), user)) {
            throw new RuntimeException("Group with this name already exists");
        }

        taskGroup.setUser(user);
        return taskGroupRepository.save(taskGroup);
    }

    public TaskGroup updateGroup(Long groupId, TaskGroup groupDetails, Elena user) {
        return taskGroupRepository.findById(groupId)
                .filter(group -> group.getUser().equals(user))
                .map(group -> {
                    if (!group.getName().equals(groupDetails.getName()) &&
                            taskGroupRepository.existsByNameAndUser(groupDetails.getName(), user)) {
                        throw new RuntimeException("Group with this name already exists");
                    }

                    group.setName(groupDetails.getName());
                    group.setDescription(groupDetails.getDescription());
                    return taskGroupRepository.save(group);
                })
                .orElseThrow(() -> new RuntimeException("Group not found"));
    }

    public void deleteGroup(Long groupId, Elena user) {
        TaskGroup group = taskGroupRepository.findById(groupId)
                .filter(g -> g.getUser().equals(user))
                .orElseThrow(() -> new RuntimeException("Group not found"));

        // Отвязываем задачи от группы перед удалением
        taskRepository.findByTaskGroup(group).forEach(task -> {
            task.setTaskGroup(null);
            taskRepository.save(task);
        });

        taskGroupRepository.delete(group);
    }

    public List<Object[]> getGroupsWithTaskCount(Elena user) {
        return taskGroupRepository.findGroupsWithTaskCount(user);
    }
}
