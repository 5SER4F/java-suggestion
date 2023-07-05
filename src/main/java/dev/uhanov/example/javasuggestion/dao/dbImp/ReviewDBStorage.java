package dev.uhanov.example.javasuggestion.dao.dbImp;

import dev.uhanov.example.javasuggestion.dao.ReviewStorage;
import dev.uhanov.example.javasuggestion.exception.ResourceNotExistException;
import dev.uhanov.example.javasuggestion.model.Review;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class ReviewDBStorage implements ReviewStorage {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReviewDBStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reviews")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Review addReview(Review newReview) {
        newReview.setId(
                simpleJdbcInsert.executeAndReturnKey(newReview.toMap()).longValue()
        );
        return newReview;
    }

    @Override
    public Review updateReview(Review r) {
        String sqlQuery = "UPDATE PUBLIC.REVIEWS " +
                "SET EMPLOYE_ID=?, SUGGESTION_ID=?, CONTENT=?, APPROVE=? " +
                "WHERE ID=?; ";
        if (1 > jdbcTemplate.update(sqlQuery,
                r.getEmployeId(),
                r.getSuggestionId(),
                r.getContent(),
                r.isApprove(),
                r.getId())
        ) {
            throw new ResourceNotExistException(
                    String.format("Попытка обновить не существующее ревью с id=%d", r.getId())
            );
        }
        return r;
    }

    @Override
    public List<Review> findAll() {
        String sqlQuery = "SELECT ID, SUGGESTION_ID AS s, APPROVE AS a " +
                "FROM PUBLIC.REVIEWS; ";
        return jdbcTemplate.query(sqlQuery, this::reviewSimpleMapper);
    }

    @Override
    public Optional<Review> findReviewById(Long id) {
        String sqlQuery = "SELECT ID, EMPLOYE_ID AS e, SUGGESTION_ID AS s, CONTENT AS c, APPROVE AS a " +
                "FROM PUBLIC.REVIEWS " +
                "WHERE ID=?; ";
        return Optional.ofNullable(
                jdbcTemplate.queryForObject(sqlQuery, this::reviewMapper, id)
        );
    }

    @Override
    public boolean deleteReviewById(Long id) {
        String sqlQuery = "DELETE FROM PUBLIC.REVIEWS " +
                "WHERE ID=?; ";
        return jdbcTemplate.update(sqlQuery, id) != 0;
    }

    private Review reviewSimpleMapper(ResultSet rs, int rowNum) throws SQLException {
        return Review.builder()
                .id(rs.getLong("ID"))
                .suggestionId(rs.getLong("s"))
                .approve(rs.getBoolean("a"))
                .build();
    }

    private Review reviewMapper(ResultSet rs, int rowNum) throws SQLException {
        return Review.builder()
                .id(rs.getLong("ID"))
                .employeId(rs.getLong("e"))
                .suggestionId(rs.getLong("s"))
                .content(rs.getString("c"))
                .approve(rs.getBoolean("a"))
                .build();
    }
}
