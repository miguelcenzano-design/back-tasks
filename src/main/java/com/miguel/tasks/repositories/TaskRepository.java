package com.miguel.tasks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miguel.tasks.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    boolean existsByName(String name);
    boolean existsById(Long id);
}
