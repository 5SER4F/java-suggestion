package dev.uhanov.example.javasuggestion.dao;

import dev.uhanov.example.javasuggestion.model.Status;

import java.util.List;

public interface StatusStorage {
    List<Status> getAll();
}
