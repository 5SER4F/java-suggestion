package dev.uhanov.example.javasuggestion.exception;

public class SQLAccessException extends RuntimeException {
    public SQLAccessException() {
    }

    public SQLAccessException(String message) {
        super(message);
    }
}
