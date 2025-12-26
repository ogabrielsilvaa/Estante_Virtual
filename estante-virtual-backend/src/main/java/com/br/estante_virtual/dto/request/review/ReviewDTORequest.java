package com.br.estante_virtual.dto.request.review;

import com.br.estante_virtual.enums.RatingLevel;
import com.br.estante_virtual.enums.ReviewStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class ReviewDTORequest implements Serializable {

    @NotNull(message = "O ID do livro é obrigatório.")
    private Integer bookId;

    @NotNull(message = "A nota do enredo é obrigatória.")
    @Schema(type = "integer", example = "0", description = "Nota de 1 (Fraco) a 5 (Excelente)")
    private RatingLevel ratingPlot;

    @NotNull(message = "A nota dos personagens é obrigatória.")
    @Schema(type = "integer", example = "0", description = "Nota de 1 (Fraco) a 5 (Excelente)")
    private RatingLevel ratingCharacters;

    @NotNull(message = "A nota da escrita é obrigatória.")
    @Schema(type = "integer", example = "0", description = "Nota de 1 (Fraco) a 5 (Excelente)")
    private RatingLevel ratingWriting;

    @NotNull(message = "A nota da imersão é obrigatória.")
    @Schema(type = "integer", example = "0", description = "Nota de 1 (Fraco) a 5 (Excelente)")
    private RatingLevel ratingImmersion;

    @Size(max = 5000, message = "A resenha deve ter no máximo 5000 caracteres.")
    private String text;

    @NotNull(message = "O status da review é obrigatório.")
    @Schema(type = "integer", example = "0")
    private ReviewStatus status;

    public ReviewDTORequest() {
    }

    public ReviewDTORequest(Integer bookId, RatingLevel ratingPlot, RatingLevel ratingCharacters, RatingLevel ratingWriting, RatingLevel ratingImmersion, String text, ReviewStatus status) {
        this.bookId = bookId;
        this.ratingPlot = ratingPlot;
        this.ratingCharacters = ratingCharacters;
        this.ratingWriting = ratingWriting;
        this.ratingImmersion = ratingImmersion;
        this.text = text;
        this.status = status;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
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

    public ReviewStatus getStatus() {
        return status;
    }

    public void setStatus(ReviewStatus status) {
        this.status = status;
    }
}