package com.br.estante_virtual.service;

import com.br.estante_virtual.dto.request.review.ReviewAtualizarDTORequest;
import com.br.estante_virtual.dto.request.review.ReviewDTORequest;
import com.br.estante_virtual.dto.response.ReviewDTOResponse;
import com.br.estante_virtual.entity.Review;
import com.br.estante_virtual.entity.UserBook;
import com.br.estante_virtual.enums.ReviewStatus;
import com.br.estante_virtual.repository.ReviewRepository;
import com.br.estante_virtual.repository.UserBookRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Camada de serviço para gerenciar a lógica de negócio do catálogo de reviews.
 */
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserBookRepository userBookRepository;

    @Autowired
    public ReviewService(
            ReviewRepository reviewRepository,
            UserBookRepository userBookRepository
    ) {
        this.reviewRepository = reviewRepository;
        this.userBookRepository = userBookRepository;
    }

    /**
     * Busca as reviews que estão com status PUBLICADO.
     * @return lista de reviews publicadas.
     */
    public List<ReviewDTOResponse> listarReviewsPublicadas(Integer userId) {
        return reviewRepository.listarPorStatusEUsuario(ReviewStatus.PUBLICADO, userId)
                .stream()
                .map(ReviewDTOResponse::new)
                .toList();
    }

    /**
     * Busca as reviews que estão com status RASCUNHO.
     * @return lista de reviews rascunho.
     */
    public List<ReviewDTOResponse> listarReviewsRascunhos(Integer userId) {
        return reviewRepository.listarPorStatusEUsuario(ReviewStatus.RASCUNHO, userId)
                .stream()
                .map(ReviewDTOResponse::new)
                .toList();
    }

    /**
     * Busca as reviews que estão com status APAGADO.
     * @return lista de reviews apagadas.
     */
    public List<ReviewDTOResponse> listarReviewsApagados(Integer userId) {
        return reviewRepository.listarPorStatusEUsuario(ReviewStatus.APAGADO, userId)
                .stream()
                .map(ReviewDTOResponse::new)
                .toList();
    }

    /**
     * Busca uma review pelo seu ID.
     * @param reviewId O ID da review a ser buscado.
     * @return O {@link ReviewDTOResponse} correspondente ao ID.
     */
    public ReviewDTOResponse listarReviewPorId(Integer reviewId, Integer userId) {
        Review verifiedReview = validarReview(reviewId, userId);
        return new ReviewDTOResponse(verifiedReview);
    }

    /**
     * Cadastra uma nova avaliação, validando se o livro está na estante e se já não foi avaliado.
     *
     * @param dtoRequest Dados da avaliação (notas, texto e IDs).
     * @return DTO da review criada.
     * @throws EntityNotFoundException Se o livro não estiver na estante.
     * @throws IllegalArgumentException Se o usuário já avaliou este livro.
     */
    @Transactional
    public ReviewDTOResponse cadastrarReview(ReviewDTORequest dtoRequest, Integer userId) {
        //buscar o userBook, agora usando o método que busca pelo par de ids (User + Book)
        UserBook userBook = userBookRepository.findByUserIdAndBookId(userId, dtoRequest.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Este livro não está na estante do usuário."));

        //permite que o usuário crie somente 1 avaliação
        if (reviewRepository.existsByUserBook(userBook)) {
            throw new IllegalArgumentException("Você já avaliou este livro.");
        }

        Review newReview = new Review();

        newReview.setUserBook(userBook);
        newReview.setRatingPlot(dtoRequest.getRatingPlot());
        newReview.setRatingCharacters(dtoRequest.getRatingCharacters());
        newReview.setRatingWriting(dtoRequest.getRatingWriting());
        newReview.setRatingImmersion(dtoRequest.getRatingImmersion());
        newReview.setText(dtoRequest.getText());
        newReview.setStatus(dtoRequest.getStatus());

        Review savedReview = reviewRepository.save(newReview);
        return new ReviewDTOResponse(savedReview);
    }

    /**
     * Atualiza os dados de uma review existente.
     * @param reviewId O ID da review a ser atualizada.
     * @param atualizarDTORequest O DTO com os novos dados.
     * @return O {@link ReviewDTOResponse} da entidade atualizada.
     */
    @Transactional
    public ReviewDTOResponse atualizarReviewPorId(
            Integer reviewId,
            ReviewAtualizarDTORequest atualizarDTORequest,
            Integer userId
            ) {
        Review existingReview = validarReview(reviewId, userId);

        if (atualizarDTORequest.getRatingPlot() != null) {
            existingReview.setRatingPlot(atualizarDTORequest.getRatingPlot());
        }

        if (atualizarDTORequest.getRatingCharacters() != null) {
            existingReview.setRatingCharacters(atualizarDTORequest.getRatingCharacters());
        }

        if (atualizarDTORequest.getRatingWriting() != null) {
            existingReview.setRatingWriting(atualizarDTORequest.getRatingWriting());
        }

        if (atualizarDTORequest.getRatingImmersion() != null) {
            existingReview.setRatingImmersion(atualizarDTORequest.getRatingImmersion());
        }

        if (atualizarDTORequest.getText() != null) {
            existingReview.setText(atualizarDTORequest.getText());
        }

        if (atualizarDTORequest.getStatus() != null) {
            existingReview.setStatus(atualizarDTORequest.getStatus());
        }

        Review updatedReview = reviewRepository.save(existingReview);

        return new ReviewDTOResponse(updatedReview);
    }

    /**
     * Realiza a exclusão lógica de uma review.
     * @param reviewId O ID da review a ser desativada.
     */
    @Transactional
    public void deletarLogico(Integer reviewId, Integer userId) {
        validarReview(reviewId, userId);
        reviewRepository.apagarReviewLogico(reviewId, ReviewStatus.APAGADO);
    }


    /**
     * Valida a existência de uma review e sua posse pelo usuário.
     * @param reviewId o ID da review a ser validada.
     * @return A entidade {@link Review} encontrada.
     * @throws EntityNotFoundException se a review não for encontrada.
     */
    private Review validarReview(Integer reviewId, Integer userId) {
        return reviewRepository.listarPorIdEUsuario(reviewId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Review não encontrada ou acesso negado (ID: " + reviewId + ")"));
    }
}
