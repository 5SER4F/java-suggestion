package dev.uhanov.example.javasuggestion.service;

import dev.uhanov.example.javasuggestion.dao.SuggestionStorage;
import dev.uhanov.example.javasuggestion.exception.ResourceNotExistException;
import dev.uhanov.example.javasuggestion.model.Suggestion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SuggestionService {
    private final SuggestionStorage suggestionStorage;

    public Suggestion createSuggestion(Suggestion suggestion) {
        suggestion.setLastUpdate(Instant.now());
        Suggestion addedSuggestion = suggestionStorage.addSuggestion(suggestion);
        log.info("Добавлено предложение с id={}", addedSuggestion.getId());
        return addedSuggestion;
    }

    public Suggestion putSuggestion(Suggestion suggestion) {
        log.info("Попытка обновить предложение с id={}", suggestion.getId());
        suggestion.setLastUpdate(Instant.now());
        return suggestionStorage.updateSuggestion(suggestion);
    }

    public List<Suggestion> findAll() {
        return suggestionStorage.findAll();
    }

    public Suggestion getSuggestionById(long suggestionId) {
        Optional<Suggestion> suggestion = suggestionStorage.findSuggestionById(suggestionId);
        if (suggestion.isEmpty()) {
            log.warn("Запрос не существующего предложения с id={}", suggestionId);
            throw new ResourceNotExistException("Попытка получить несуществующее предложение с id=" + suggestionId);
        }
        log.info("Получено предложение с id={}", suggestionId);
        return suggestion.get();
    }

    public boolean deleteSuggestionById(long suggestionId) {
        if (!suggestionStorage.deleteSuggestionById(suggestionId)) {
            log.warn("Попытка удалить несуществующее предложения с id={}", suggestionId);
            throw new ResourceNotExistException("Попытка удалить несуществующее предложение с id=" + suggestionId);
        }
        log.info("Удалено предложение с id={}", suggestionId);
        return true;
    }
}
