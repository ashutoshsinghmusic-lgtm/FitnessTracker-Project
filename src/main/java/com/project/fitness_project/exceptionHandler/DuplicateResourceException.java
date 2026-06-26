package com.project.fitness_project.exceptionHandler;


public class DuplicateResourceException extends RuntimeException{

    public DuplicateResourceException(String message) {
        super(message);
    }
}
