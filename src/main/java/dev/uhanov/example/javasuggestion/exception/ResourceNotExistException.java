package dev.uhanov.example.javasuggestion.exception;

public class ResourceNotExistException extends RuntimeException {
    public ResourceNotExistException() {
    }

    public ResourceNotExistException(String message) {
        super(message);
    }
}
