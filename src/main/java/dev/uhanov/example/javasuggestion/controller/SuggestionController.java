package dev.uhanov.example.javasuggestion.controller;

import dev.uhanov.example.javasuggestion.model.Suggestion;
import dev.uhanov.example.javasuggestion.service.SuggestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/suggestions")
@RequiredArgsConstructor
@Slf4j
public class SuggestionController {
    private final SuggestionService service;

    @PreAuthorize("hasAuthority('WORKER') or (hasAuthority('MANAGER'))")
    @PostMapping
    public ResponseEntity<Suggestion> createSuggestion(@Valid @RequestBody Suggestion suggestion) {
        log.info("Запрос на создание предложения");
        return new ResponseEntity<>(service.createSuggestion(suggestion), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('WORKER') or (hasAuthority('MANAGER'))")
    @PutMapping
    public ResponseEntity<Suggestion> putSuggestion(@Valid @RequestBody Suggestion suggestion) {
        log.info("Обновление предложения c id={}", suggestion.getId());
        return new ResponseEntity<>(service.putSuggestion(suggestion), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('WORKER') or (hasAuthority('MANAGER'))")
    @GetMapping
    public ResponseEntity<List<Suggestion>> findAll() {
        log.info("Запрос на получение всех предложений");
        List<Suggestion> suggestions = service.findAll();
        log.info("Возвращен список из {} предложений", suggestions.size());
        return new ResponseEntity<>(suggestions, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('WORKER') or (hasAuthority('MANAGER'))")
    @GetMapping("/{id}")
    public ResponseEntity<Suggestion> getSuggestionById(@PathVariable("id") long suggestionId) {
        log.info("Запрос на получение предложения с id={}", suggestionId);
        return new ResponseEntity<>(service.getSuggestionById(suggestionId), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('WORKER') or (hasAuthority('MANAGER'))")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteSuggestionById(@PathVariable("id") long suggestionId) {
        log.info("Запрос на удаление предложения с id={}", suggestionId);
        return new ResponseEntity<>(service.deleteSuggestionById(suggestionId), HttpStatus.OK);
    }
}
