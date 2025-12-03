package com.miguel.tasks.services;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miguel.tasks.entities.Task;
import com.miguel.tasks.repositories.TaskRepository;
import com.miguel.tasks.exceptions.DuplicateTaskNameException;
import com.miguel.tasks.exceptions.TaskNotFoundException;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task save(Task task) {
        if (taskRepository.existsByName(task.getName())) {
            throw new DuplicateTaskNameException(task.getName());
        }
        return taskRepository.save(task);
    }

    public Task updatePartial(Long id, Map<String, Object> updates) {
        Task taskToUpdate = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        Set<String> allowedFields = Set.of("name", "description", "status");
        updates.forEach((key, value) -> {
            try {
                if (!allowedFields.contains(key)) {
                    throw new IllegalArgumentException("campo invalido: " + key);
                }
                Field field = Task.class.getDeclaredField(key);
                field.setAccessible(true);
                field.set(taskToUpdate, value); 
            } catch (NoSuchFieldException e) {
                throw new IllegalArgumentException("campo invalido: " + key);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return taskRepository.save(taskToUpdate);
    }

    public void delete(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.delete(task);
    }
}
