package com.eric.zadanieRekrutacyjne.repository;

import com.eric.zadanieRekrutacyjne.entity.Task;
import com.eric.zadanieRekrutacyjne.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findAllByStatus(TaskStatus status, Pageable pageable);
}
