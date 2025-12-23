package com.br.estante_virtual.dto.response;

import com.br.estante_virtual.dto.response.user.UserDTOResponse;
import com.br.estante_virtual.entity.Review;
import com.br.estante_virtual.enums.RatingLevel;
import com.br.estante_virtual.enums.ReviewStatus;

import java.time.LocalDateTime;

public class ReviewDTOResponse {

    private Integer id;
    private UserDTOResponse user;
    private BookDTOResponse book;
    private RatingLevel ratingPlot;
    private RatingLevel ratingCharacters;
    private RatingLevel ratingWriting;
    private RatingLevel ratingImmersion;
    private String text;
    private LocalDateTime createdAt;
    private ReviewStatus status;

    public ReviewDTOResponse() {
    }

    public ReviewDTOResponse(Integer id, UserDTOResponse user, BookDTOResponse book, RatingLevel ratingPlot, RatingLevel ratingCharacters, RatingLevel ratingWriting, RatingLevel ratingImmersion, String text, LocalDateTime createdAt, ReviewStatus status) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.ratingPlot = ratingPlot;
        this.ratingCharacters = ratingCharacters;
        this.ratingWriting = ratingWriting;
        this.ratingImmersion = ratingImmersion;
        this.text = text;
        this.createdAt = createdAt;
        this.status = status;
    }

    public ReviewDTOResponse(Review review) {
        this.id = review.getId();

        if (review.getUserBook() != null && review.getUserBook().getUser() != null) {
            this.user = new UserDTOResponse(review.getUserBook().getUser());
        }

        if (review.getUserBook() != null && review.getUserBook().getBook() != null) {
            this.book = new BookDTOResponse(review.getUserBook().getBook());
        }

        this.ratingPlot = review.getRatingPlot();
        this.ratingCharacters = review.getRatingCharacters();
        this.ratingWriting = review.getRatingWriting();
        this.ratingImmersion = review.getRatingImmersion();
        this.text = review.getText();
        this.createdAt = review.getCreatedAt();
        this.status = review.getStatus();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDTOResponse getUser() {
        return user;
    }

    public void setUser(UserDTOResponse user) {
        this.user = user;
    }

    public BookDTOResponse getBook() {
        return book;
    }

    public void setBook(BookDTOResponse book) {
        this.book = book;
    }

    public RatingLevel getRatingPlot() {
        return ratingPlot;
    }

    public void setRatingPlot(RatingLevel ratingPlot) {
        this.ratingPlot = ratingPlot;
    }

    public RatingLevel getRatingCharacters() {
        return ratingCharacters;
    }

    public void setRatingCharacters(RatingLevel ratingCharacters) {
        this.ratingCharacters = ratingCharacters;
    }

    public RatingLevel getRatingWriting() {
        return ratingWriting;
    }

    public void setRatingWriting(RatingLevel ratingWriting) {
        this.ratingWriting = ratingWriting;
    }

    public RatingLevel getRatingImmersion() {
        return ratingImmersion;
    }

    public void setRatingImmersion(RatingLevel ratingImmersion) {
        this.ratingImmersion = ratingImmersion;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ReviewStatus getStatus() {
        return status;
    }

    public void setStatus(ReviewStatus status) {
        this.status = status;
    }
}
