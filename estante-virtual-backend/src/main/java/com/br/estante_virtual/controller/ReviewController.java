package com.br.estante_virtual.controller;

import com.br.estante_virtual.dto.request.review.ReviewAtualizarDTORequest;
import com.br.estante_virtual.dto.request.review.ReviewDTORequest;
import com.br.estante_virtual.dto.response.ReviewDTOResponse;
import com.br.estante_virtual.enums.ReviewStatus;
import com.br.estante_virtual.security.UserDetailsImpl;
import com.br.estante_virtual.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;


@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Review", description = "API para gerenciamento das reviews do usuário autenticado.")
public class ReviewController {

    private ReviewService reviewService;

    /**
     * Construtor para injeção de dependência do ReviewService.
     * @param reviewService O serviço que contém a lógica de negócio para reviews.
     */
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * Lista todos as reviews com base no status desejado.
     * @return Uma lista de reviews do usuário.
     */
    @GetMapping("/listarReviews")
    @Operation(summary = "Listar reviews.", description = "Endpoint para listar reviews do usuário logado, filtrando por status.")
    public ResponseEntity<Page<ReviewDTOResponse>> listarReviewsPublicadas(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(defaultValue = "PUBLICADO") ReviewStatus status,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(
                reviewService.listarReviewsPorStatus(userDetails.getUserId(), status, pageable)
        );
    }

    /**
     * Busca uma review específica pelo seu ID, garantindo que pertença ao usuário autenticado.
     * @param reviewId O ID da review a ser buscado.
     * @return a review encontrado.
     */
    @GetMapping("/listarReviewPorId/{reviewId}")
    @Operation(summary = "Listar review pelo ID.", description = "Endpoint para listar uma review específica do usuário logado.")
    public ResponseEntity<ReviewDTOResponse> listarReviewPorId(
            @PathVariable("reviewId") Integer reviewId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        ReviewDTOResponse dtoResponse = reviewService.listarReviewPorId(reviewId, userDetails.getUserId());
        return ResponseEntity.ok(dtoResponse);
    }

    /**
     * Cria uma nova review para o usuário autenticado.
     * @param dtoRequest O DTO contendo os dados da review a ser criada.
     * @return A review recém-criada.
     */
    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastrar Review.", description = "Endpoint para cadastrar Reviews.")
    public ResponseEntity<ReviewDTOResponse> cadastrarReview(
            @Valid @RequestBody ReviewDTORequest dtoRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        ReviewDTOResponse dtoResponse = reviewService.cadastrarReview(dtoRequest, userDetails.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoResponse);
    }

    /**
     * Atualiza uma review existente do usuário autenticado.
     * @param reviewId O ID da review a ser buscado.
     * @param atualizarDTORequest O DTO contendo os dados para atualização (apenas os campos que devem ser alterados).
     * @return A review autalizada.
     */
    @PatchMapping("/atualizar/{reviewId}")
    @Operation(summary = "Atualizar todos os dados da Review.", description = "Endpoint para atualizar o registro de uma Review existente do usuário logado.")
    public ResponseEntity<ReviewDTOResponse> atualizarReviewPorId(
            @PathVariable("reviewId") Integer reviewId,
            @RequestBody @Valid ReviewAtualizarDTORequest atualizarDTORequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        ReviewDTOResponse updatedReview = reviewService.atualizarReviewPorId(reviewId, atualizarDTORequest, userDetails.getUserId());
        return ResponseEntity.ok(updatedReview);
    }

    /**
     * Realiza a exclusão lógica de uma review do usuário autenticado.
     * @param reviewId O ID da review a ser buscado.
     * @return Resposta sem conteúdo.
     */
    @DeleteMapping("/deletar/{reviewId}")
    @Operation(summary = "Deletar todos os dados de uma Review.", description = "Endpoint para deletar o registro de uma Review do usuário logado.")
    public ResponseEntity<Void> deletarReview(
            @PathVariable("reviewId") Integer reviewId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        reviewService.deletarLogico(reviewId, userDetails.getUserId());
        return ResponseEntity.noContent().build();
    }

}
