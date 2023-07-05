package dev.uhanov.example.javasuggestion.controller;

import dev.uhanov.example.javasuggestion.model.Review;
import dev.uhanov.example.javasuggestion.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/reviews")
@PreAuthorize("hasAuthority('MANAGER')")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
    private final ReviewService service;

    @PostMapping
    public ResponseEntity<Review> createReview(@Valid @RequestBody Review review) {
        log.info("Запрос на создание предложения ревью");
        return new ResponseEntity<>(service.createReview(review), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Review> putReview(@Valid @RequestBody Review review) {
        log.info("Обновление ревью с id={}", review.getId());
        return new ResponseEntity<>(service.putReview(review), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Review>> findAll() {
        log.info("Запрос на получение всех ревью");
        List<Review> reviews = service.findAll();
        log.info("Возвращен список из {} предложений", reviews.size());
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable("id") long reviewId) {
        log.info("Запрос на получение ревью с id={}", reviewId);
        return new ResponseEntity<>(service.getReviewById(reviewId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteReviewById(@PathVariable("id") long reviewId) {
        log.info("Запрос на удаление ревью с id={}", reviewId);
        return new ResponseEntity<>(service.deleteReviewById(reviewId), HttpStatus.OK);
    }

}
