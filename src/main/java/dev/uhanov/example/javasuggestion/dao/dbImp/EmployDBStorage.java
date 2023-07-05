package dev.uhanov.example.javasuggestion.dao.dbImp;

import dev.uhanov.example.javasuggestion.dao.EmployeeStorage;
import dev.uhanov.example.javasuggestion.model.Employ;
import dev.uhanov.example.javasuggestion.model.Title;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class EmployDBStorage implements EmployeeStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Employ> getEmployeByName(String name) {
        String sqlQuery = "SELECT ID, PASSWORD, TITLE " +
                "FROM EMPLOYEE " +
                "WHERE NAME=?; ";
        return Optional.ofNullable(
                jdbcTemplate.queryForObject(sqlQuery,
                        (rs, rowNum) -> Employ.builder()
                                .id(rs.getLong("ID"))
                                .name(name)
                                .password(rs.getString("PASSWORD"))
                                .title(Set.of(Title.valueOf(rs.getString("TITLE"))))
                                .build(),
                        name)
        );
    }
}

