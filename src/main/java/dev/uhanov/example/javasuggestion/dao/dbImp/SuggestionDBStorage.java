package dev.uhanov.example.javasuggestion.dao.dbImp;

import dev.uhanov.example.javasuggestion.dao.SuggestionStorage;
import dev.uhanov.example.javasuggestion.exception.ResourceNotExistException;
import dev.uhanov.example.javasuggestion.model.Suggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Component
public class SuggestionDBStorage implements SuggestionStorage {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public SuggestionDBStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("suggestions")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Suggestion addSuggestion(Suggestion newSuggestion) {
        newSuggestion.setId(
                simpleJdbcInsert.executeAndReturnKey(newSuggestion.toMap()).longValue()

        );
        return newSuggestion;
    }

    @Override
    public Suggestion updateSuggestion(Suggestion s) {
        String sqlQuery = "UPDATE PUBLIC.SUGGESTIONS " +
                "SET EMPLOYE_ID=?, TOPIC=?, CONTENT=?, LAST_UPDATE=?, STATUS_ID=? " +
                "WHERE ID=?; ";
        if (1 > jdbcTemplate.update(sqlQuery,
                s.getEmployeId(),
                s.getTopic(),
                s.getContent(),
                Timestamp.from(s.getLastUpdate()),
                s.getStatusId(),
                s.getId())
        ) {
            throw new ResourceNotExistException(
                    String.format("Попытка обновить не существующее предложение с id=%d", s.getId())
            );
        }
        return s;
    }

    @Override
    public List<Suggestion> findAll() {
        String sqlQuery = "SELECT ID, TOPIC " +
                "FROM PUBLIC.SUGGESTIONS; ";
        return jdbcTemplate.query(sqlQuery, this::suggestionSimpleMapper);
    }

    @Override
    public Optional<Suggestion> findSuggestionById(Long id) {
        String sqlQuery = "SELECT ID, EMPLOYE_ID, TOPIC, CONTENT, LAST_UPDATE, STATUS_ID " +
                "FROM PUBLIC.SUGGESTIONS " +
                "WHERE id=?; ";
        return Optional.ofNullable(
                jdbcTemplate.queryForObject(sqlQuery, this::suggestionMapper, id)
        );
    }

    @Override
    public boolean deleteSuggestionById(Long id) {
        String sqlQuery = "DELETE FROM PUBLIC.SUGGESTIONS WHERE ID=?; ";
        return jdbcTemplate.update(sqlQuery, id) != 0;
    }

    private Suggestion suggestionMapper(ResultSet rs, int rowNum) throws SQLException {
        return Suggestion.builder().
                id(rs.getLong("id"))
                .employeId(rs.getLong("employe_id"))
                .topic(rs.getString("topic"))
                .content(rs.getString("content"))
                .lastUpdate(rs.getTimestamp("last_update").toInstant())
                .statusId(rs.getLong("status_id"))
                .build();
    }

    private Suggestion suggestionSimpleMapper(ResultSet rs, int rowNum) throws SQLException {
        return Suggestion.builder()
                .id(rs.getLong("id"))
                .topic(rs.getString("topic"))
                .build();
    }
}
