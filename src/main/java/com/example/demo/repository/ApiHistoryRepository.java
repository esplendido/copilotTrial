package com.example.demo.repository;

import com.example.demo.model.ApiHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiHistoryRepository extends JpaRepository<ApiHistory, Long> {

    @Query("SELECT a FROM ApiHistory a ORDER BY a.timestamp DESC")
    List<ApiHistory> findLatestApiHistories(@Param("limit") int limit);
}
