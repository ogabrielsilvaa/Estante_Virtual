package com.br.estante_virtual.repository;

import com.br.estante_virtual.entity.Review;
import com.br.estante_virtual.entity.UserBook;
import com.br.estante_virtual.enums.ReviewStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para gerenciar as operações de banco de dados para a entidade {@link com.br.estante_virtual.entity.Review}.
 * Estende JpaRepository para funcionalidades CRUD padrão e define consultas customizadas.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    /**
     * Verifica se existe uma review vinculada a este objeto UserBook exato.
     * @param userBook
     * @return true or false
     */
    boolean existsByUserBook(UserBook userBook);

    /**
     * Lista todos as reviews que correspondem ao status informado.
     * * @param status 1 status para filtragem (ex: PUBLICADO).
     * @return Uma lista de reviews com o status especificado.
     */
    @Query("SELECT r FROM Review r WHERE r.status = :status")
    List<Review> listarPorStatus(@Param("status") ReviewStatus status);

    /**
     * Busca uma review específica pelo seu identificador único.
     * * @param reviewId O ID da review.
     * @return A entidade Review encontrada.
     */
    @Query("SELECT r FROM Review r WHERE r.id = :reviewId")
    Review listarReviewPorId(@Param("reviewId") Integer reviewId);

    /**
     * Realiza a exclusão lógica da review.
     * @param reviewId O ID da review.
     * @param status O status para o qual a review será atualizado (deve ser APAGADO).
     */
    @Modifying
    @Transactional
    @Query("UPDATE Review r SET r.status = :statusApagado WHERE r.id = :reviewId")
    void apagarReviewLogico(@Param("reviewId") Integer reviewId, @Param("statusApagado") ReviewStatus status);

    @Query("SELECT r FROM Review r WHERE r.status = :status AND r.userBook.user.id = :userId")
    Page<Review> listarPorStatusEUsuario(@Param("status") ReviewStatus status, @Param("userId") Integer userId, Pageable pageable);

    /**
     * Busca uma review pelo ID, mas apenas se pertencer ao usuário logado.
     * @param id
     * @param userId
     * @return
     */
    @Query("SELECT r FROM Review r WHERE r.id = :id AND r.userBook.user.id = :userId")
    Optional<Review> listarPorIdEUsuario(Integer id, Integer userId);
}
