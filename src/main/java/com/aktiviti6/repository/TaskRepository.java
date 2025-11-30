package com.aktiviti6.repository;

import com.aktiviti6.model.Task;
import com.aktiviti6.model.TaskGroup;
import com.aktiviti6.model.Elena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Задачи Elena
    List<Task> findByUser(Elena user);

    // Задачи по группе
    List<Task> findByTaskGroup(TaskGroup taskGroup);

    // Задачи по статусу выполнения
    List<Task> findByCompleted(boolean completed);

    // Задачи Elena по статусу выполнения
    List<Task> findByUserAndCompleted(Elena user, boolean completed);

    // Задачи с истекшим сроком
    List<Task> findByDueDateBeforeAndCompleted(LocalDateTime dueDate, boolean completed);

    // Задачи по приоритету
    List<Task> findByUserAndPriority(Elena user, String priority);

    // Задачи по названию (без учета регистра)
    List<Task> findByUserAndTitleContainingIgnoreCase(Elena user, String title);

    // Задачи на сегодня - JPQL ЗАПРОС
    @Query("SELECT t FROM Task t WHERE t.user = :user AND DATE(t.dueDate) = CURRENT_DATE")
    List<Task> findTodayTasks(@Param("user") Elena user);

    // Задачи на неделю вперед - JPQL ЗАПРОС
    @Query("SELECT t FROM Task t WHERE t.user = :user AND t.dueDate BETWEEN :startDate AND :endDate ORDER BY t.dueDate ASC")
    List<Task> findUpcomingTasks(@Param("user") Elena user,
                                 @Param("startDate") LocalDateTime startDate,
                                 @Param("endDate") LocalDateTime endDate);

    // Статистика по задачам Elena - ВЫПОЛНЯЕМ ТРЕБОВАНИЕ ЯВНОГО JPQL ЗАПРОСА
    @Query("SELECT COUNT(t) FROM Task t WHERE t.user = :user AND t.completed = true")
    Long countCompletedTasksByUser(@Param("user") Elena user);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.user = :user AND t.completed = false")
    Long countPendingTasksByUser(@Param("user") Elena user);

    // Подсчет задач Elena
    Long countByUser(Elena user);

    long countByCompleted(boolean completed);

    // Задачи по статусу (запланирована, в работе, сделана)
    List<Task> findByUserAndStatus(Elena user, String status);
}
