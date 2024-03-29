package com.farion.onlinebookstore.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message, Throwable e) {
        super(message, e);
    }
}
