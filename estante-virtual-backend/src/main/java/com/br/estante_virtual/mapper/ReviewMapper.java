package com.br.estante_virtual.mapper;

import com.br.estante_virtual.dto.request.review.ReviewAtualizarDTORequest;
import com.br.estante_virtual.dto.request.review.ReviewDTORequest;
import com.br.estante_virtual.entity.Review;
import com.br.estante_virtual.entity.UserBook;
import org.springframework.stereotype.Component;

/**
 * Componente responsável pela conversão e mapeamento de dados entre DTOs e a entidade Review (Avaliação).
 */
@Component
public class ReviewMapper {

    /**
     * Converte um DTO de criação em uma nova entidade Review.
     * Associa a avaliação ao registro do livro na estante (UserBook).
     *
     * @param dtoRequest Dados da avaliação (notas, texto, status).
     * @param userBook O registro do livro na estante do usuário que está sendo avaliado.
     * @return Uma nova instância de {@link Review} preenchida.
     */
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

    /**
     * Atualiza os dados de uma avaliação existente.
     * Realiza atualização parcial (apenas campos não nulos no DTO são alterados).
     *
     * @param review A entidade de avaliação a ser atualizada.
     * @param dtoAtualizar O DTO contendo os novos dados (notas, texto ou status).
     */
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
