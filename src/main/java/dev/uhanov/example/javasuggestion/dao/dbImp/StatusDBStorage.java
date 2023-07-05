package dev.uhanov.example.javasuggestion.dao.dbImp;

import dev.uhanov.example.javasuggestion.dao.StatusStorage;
import dev.uhanov.example.javasuggestion.model.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StatusDBStorage implements StatusStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Status> getAll() {
        String sqlQuery = "SELECT NAME AS n FROM PUBLIC.STATUSES;";
        return jdbcTemplate.query(sqlQuery, (
                (rs, rowNum) -> Status.valueOf(rs.getString("n"))
        ));
    }
}
