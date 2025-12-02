package com.miguel.tasks.exceptions;

public class DuplicateTaskNameException extends RuntimeException {
    public DuplicateTaskNameException(String name) {
        super("Ya existe una tarea con el nombre: " + name);
    }
}
