package dev.uhanov.example.javasuggestion.controller;

import dev.uhanov.example.javasuggestion.model.Status;
import dev.uhanov.example.javasuggestion.service.StatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statuses")
@RequiredArgsConstructor
@Slf4j
public class StatusController {
    private final StatusService statusService;

    @GetMapping
    public ResponseEntity<List<Status>> getAll() {
        log.info("Запрос на получение списка статусов");
        List<Status> statuses = statusService.getAll();
        log.info("Возвращен список статусов длиною {}", statuses.size());
        return new ResponseEntity<>(statuses, HttpStatus.OK);
    }
}
