package com.miguel.tasks.exceptions;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("Tarea no encontrada con el ID: " + id);
    }
}
