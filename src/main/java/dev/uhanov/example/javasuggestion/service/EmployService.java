package dev.uhanov.example.javasuggestion.service;

import dev.uhanov.example.javasuggestion.dao.EmployeeStorage;
import dev.uhanov.example.javasuggestion.exception.ResourceNotExistException;
import dev.uhanov.example.javasuggestion.model.Employ;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployService {

    private final EmployeeStorage employeeStorage;

    public Employ getEmployByName(String name) {
        Optional<Employ> employ = employeeStorage.getEmployeByName(name);
        if (employ.isEmpty()) {
            log.warn("Запрос на получение несуществующего пользователя с именем={}", name);
            throw new ResourceNotExistException("Попытка получить несуществующего пользователя");
        }
        return employ.get();
    }
}
