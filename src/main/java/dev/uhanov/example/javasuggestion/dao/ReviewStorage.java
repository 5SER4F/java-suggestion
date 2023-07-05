package dev.uhanov.example.javasuggestion.dao;

import dev.uhanov.example.javasuggestion.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewStorage {
    Review addReview(Review review);

    Review updateReview(Review review);

    List<Review> findAll();

    Optional<Review> findReviewById(Long id);

    boolean deleteReviewById(Long id);
}
