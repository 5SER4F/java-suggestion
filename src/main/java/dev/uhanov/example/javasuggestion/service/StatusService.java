package dev.uhanov.example.javasuggestion.service;

import dev.uhanov.example.javasuggestion.dao.StatusStorage;
import dev.uhanov.example.javasuggestion.model.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final StatusStorage statusStorage;

    public List<Status> getAll() {
        return statusStorage.getAll();
    }
}
