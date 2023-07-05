package dev.uhanov.example.javasuggestion.dao;

import dev.uhanov.example.javasuggestion.model.Suggestion;

import java.util.List;
import java.util.Optional;

public interface SuggestionStorage {

    Suggestion addSuggestion(Suggestion suggestion);

    Suggestion updateSuggestion(Suggestion suggestion);

    List<Suggestion> findAll();

    Optional<Suggestion> findSuggestionById(Long id);

    boolean deleteSuggestionById(Long id);

}
