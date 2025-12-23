package com.br.estante_virtual.dto.request.review;

import com.br.estante_virtual.enums.RatingLevel;
import com.br.estante_virtual.enums.ReviewStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

public class ReviewAtualizarDTORequest {

    @Schema(type = "integer", example = "0", description = "Nota de 1 (Fraco) a 5 (Excelente)")
    private RatingLevel ratingPlot;

    @Schema(type = "integer", example = "0", description = "Nota de 1 (Fraco) a 5 (Excelente)")
    private RatingLevel ratingCharacters;

    @Schema(type = "integer", example = "0", description = "Nota de 1 (Fraco) a 5 (Excelente)")
    private RatingLevel ratingWriting;

    @Schema(type = "integer", example = "0", description = "Nota de 1 (Fraco) a 5 (Excelente)")
    private RatingLevel ratingImmersion;

    @Schema(type = "integer", example = "0")
    private ReviewStatus status;

    @Size(max = 5000, message = "A resenha deve ter no máximo 5000 caracteres")
    private String text;

    public ReviewAtualizarDTORequest() {
    }

    public ReviewAtualizarDTORequest(RatingLevel ratingPlot, RatingLevel ratingCharacters, RatingLevel ratingWriting, RatingLevel ratingImmersion, ReviewStatus status, String text) {
        this.ratingPlot = ratingPlot;
        this.ratingCharacters = ratingCharacters;
        this.ratingWriting = ratingWriting;
        this.ratingImmersion = ratingImmersion;
        this.status = status;
        this.text = text;
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

    public ReviewStatus getStatus() {
        return status;
    }

    public void setStatus(ReviewStatus status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
