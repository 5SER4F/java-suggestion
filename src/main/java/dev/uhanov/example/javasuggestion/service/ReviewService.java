package dev.uhanov.example.javasuggestion.service;

import dev.uhanov.example.javasuggestion.dao.ReviewStorage;
import dev.uhanov.example.javasuggestion.exception.ResourceNotExistException;
import dev.uhanov.example.javasuggestion.model.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewStorage reviewStorage;

    public Review createReview(Review review) {
        Review addedReview = reviewStorage.addReview(review);
        log.info("Добавлено ревью с id={}", review.getId());
        return addedReview;
    }


    public Review putReview(Review review) {
        log.info("Попытка обновить ревью с id={}", review.getId());
        return reviewStorage.updateReview(review);
    }


    public List<Review> findAll() {
        return reviewStorage.findAll();
    }


    public Review getReviewById(long reviewId) {
        Optional<Review> review = reviewStorage.findReviewById(reviewId);
        if (review.isEmpty()) {
            log.warn("Запрос не существующего предложения с id={}", reviewId);
            throw new ResourceNotExistException("Попытка получить несуществующее ревью с id=" + reviewId);
        }
        log.info("Получено ревью с id={}", reviewId);
        return review.get();
    }


    public Boolean deleteReviewById(long reviewId) {
        if (!reviewStorage.deleteReviewById(reviewId)) {
            log.warn("Попытка удалить несуществующее ревью с id={}", reviewId);
            throw new ResourceNotExistException("Попытка удалить несуществующее ревью с id=" + reviewId);
        }
        log.info("Удалено ревью с id={}", reviewId);
        return true;
    }
}
