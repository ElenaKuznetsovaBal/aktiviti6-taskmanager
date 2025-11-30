package com.aktiviti6.repository;

import com.aktiviti6.model.TaskGroup;
import com.aktiviti6.model.Elena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskGroupRepository extends JpaRepository<TaskGroup, Long> {

    // Группы Elena
    List<TaskGroup> findByUser(Elena user);

    Optional<TaskGroup> findByNameAndUser(String name, Elena user);

    // Проверить существование группы по имени Elena
    boolean existsByNameAndUser(String name, Elena user);

    // Найти группы с подсчетом количества задач - JPQL ЗАПРОС
    @Query("SELECT tg, COUNT(t) FROM TaskGroup tg LEFT JOIN tg.tasks t WHERE tg.user = :user GROUP BY tg")
    List<Object[]> findGroupsWithTaskCount(@Param("user") Elena user);

    // Получить статистику по группам для администратора - JPQL ЗАПРОС
    @Query("SELECT tg.name, COUNT(t), SUM(CASE WHEN t.completed = true THEN 1 ELSE 0 END) " +
            "FROM TaskGroup tg LEFT JOIN tg.tasks t GROUP BY tg.id, tg.name")
    List<Object[]> getGroupStatistics();
}
