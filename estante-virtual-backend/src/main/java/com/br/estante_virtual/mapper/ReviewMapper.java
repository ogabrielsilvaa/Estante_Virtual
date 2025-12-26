package com.br.estante_virtual.mapper;

import com.br.estante_virtual.dto.request.review.ReviewAtualizarDTORequest;
import com.br.estante_virtual.dto.request.review.ReviewDTORequest;
import com.br.estante_virtual.entity.Review;
import com.br.estante_virtual.entity.UserBook;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public Review toEntity(ReviewDTORequest dtoRequest, UserBook userBook) {
        Review newReview = new Review();

        newReview.setUserBook(userBook);
        newReview.setRatingPlot(dtoRequest.getRatingPlot());
        newReview.setRatingCharacters(dtoRequest.getRatingCharacters());
        newReview.setRatingWriting(dtoRequest.getRatingWriting());
        newReview.setRatingImmersion(dtoRequest.getRatingImmersion());
        newReview.setText(dtoRequest.getText());
        newReview.setStatus(dtoRequest.getStatus());

        return newReview;
    }

    public void updateEntity(Review review, ReviewAtualizarDTORequest dtoAtualizar) {

        if (dtoAtualizar.getRatingPlot() != null) {
            review.setRatingPlot(dtoAtualizar.getRatingPlot());
        }

        if (dtoAtualizar.getRatingCharacters() != null) {
            review.setRatingCharacters(dtoAtualizar.getRatingCharacters());
        }

        if (dtoAtualizar.getRatingWriting() != null) {
            review.setRatingWriting(dtoAtualizar.getRatingWriting());
        }

        if (dtoAtualizar.getRatingImmersion() != null) {
            review.setRatingImmersion(dtoAtualizar.getRatingImmersion());
        }

        if (dtoAtualizar.getText() != null) {
            review.setText(dtoAtualizar.getText());
        }

        if (dtoAtualizar.getStatus() != null) {
            review.setStatus(dtoAtualizar.getStatus());
        }
    }

}
