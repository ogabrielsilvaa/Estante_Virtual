package com.br.estante_virtual.repository;

import com.br.estante_virtual.entity.UserBook;
import com.br.estante_virtual.enums.BookReadingStatus;
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
 * Repositório para gerenciar as operações de banco de dados para a entidade {@link UserBook}
 */
@Repository
public interface UserBookRepository extends JpaRepository<UserBook, Integer> {

    /**
     * Busca toda a estante de livros do usuário informado.
     */
    @Query("SELECT ub FROM UserBook ub WHERE ub.user.id = :userId")
    List<UserBook> findByUserId(@Param("userId") Integer userId);

    /**
     * Busca a estante do usuário de forma paginada.
     * O Spring aplica a paginação automaticamente na Query JPQL.
     */
    @Query("SELECT ub FROM UserBook ub WHERE ub.user.id = :userId")
    Page<UserBook> findByUserId(@Param("userId") Integer userId, Pageable pageable);

    /**
     * Busca livros do usuário filtrando pelo status (ex: LENDO, LIDO).
     */
    @Query("SELECT ub FROM UserBook ub WHERE ub.user.id = :userId AND ub.status = :status")
    List<UserBook> findByUserIdAndStatus(@Param("userId") Integer userId,
                                         @Param("status") BookReadingStatus status);

    /**
     * Verifica se este livro já existe na estante deste usuário.
     * Implementado contando se existe algum registro (> 0).
     */
    @Query("SELECT CASE WHEN COUNT(ub) > 0 THEN true ELSE false END FROM UserBook ub WHERE ub.user.id = :userId AND ub.book.id = :bookId")
    boolean existsByUserIdAndBookId(@Param("userId") Integer userId,
                                    @Param("bookId") Integer bookId);

    /**
     * Busca um registro específico pelo par Usuário + Livro.
     */
    @Query("SELECT ub FROM UserBook ub WHERE ub.user.id = :userId AND ub.book.id = :bookId")
    Optional<UserBook> findByUserIdAndBookId(@Param("userId") Integer userId,
                                             @Param("bookId") Integer bookId);

    /**
     * Atualiza o status do livro na estante para realizar a exclusão lógica.
     */
    @Modifying
    @Transactional
    @Query("UPDATE UserBook ub SET ub.status = :status WHERE ub.user.id = :userId AND ub.book.id = :bookId")
    void deletarLogicamente(@Param("userId") Integer userId,
                            @Param("bookId") Integer bookId,
                            @Param("status") BookReadingStatus status);
}