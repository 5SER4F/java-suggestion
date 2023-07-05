package dev.uhanov.example.javasuggestion.dao;

import dev.uhanov.example.javasuggestion.model.Employ;

import java.util.Optional;

public interface EmployeeStorage {

    Optional<Employ> getEmployeByName(String name);
}
