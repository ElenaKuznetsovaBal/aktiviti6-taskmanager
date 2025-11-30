package com.aktiviti6.repository;

import com.aktiviti6.model.Elena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElenaRepository extends JpaRepository<Elena, Long> {

    Optional<Elena> findByEmail(String email);
    boolean existsByEmail(String email);


    @Query("SELECT u.email, COUNT(t), " +
            "SUM(CASE WHEN t.completed = true THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN t.completed = false THEN 1 ELSE 0 END) " +
            "FROM Elena u LEFT JOIN u.tasks t GROUP BY u.id, u.email")
    List<Object[]> getUserStatistics();
}
