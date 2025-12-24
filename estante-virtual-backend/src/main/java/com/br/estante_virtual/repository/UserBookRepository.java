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
     * @param userId ID do usuário logado.
     * @return lista de livros com status ATIVO do usuário logado.
     */
    @Query("SELECT ub FROM UserBook ub WHERE ub.user.id = :userId AND ub.statusActive = true")
    List<UserBook> listarTodosAtivos(@Param("userId") Integer userId);

    /**
     * Busca a estante do usuário de forma paginada.
     * O Spring aplica a paginação automaticamente na Query JPQL.
     * @param userId ID do usuário logado.
     * @param pageable
     * @return lista de livros com status ATIVO do usuário logado, com paginação.
     */
    @Query("SELECT ub FROM UserBook ub WHERE ub.user.id = :userId AND ub.statusActive = true")
    Page<UserBook> listarTodosAtivosPaginado(@Param("userId") Integer userId, Pageable pageable);

    /**
     * Busca um livro específico na estante (somente se ativo)
     * @param userId ID do usuário logado.
     * @param bookId ID do livro.
     * @return livro buscado.
     */
    @Query("SELECT ub FROM UserBook ub WHERE ub.user.id = :userId AND ub.book.id= :bookId AND ub.statusActive = true")
    Optional<UserBook> listarPorId(@Param("userId") Integer userId,
                         @Param("bookId") Integer bookId);

    /**
     * Verifica existência (somente se ativo).
     * @param userId ID do usuário logado.
     * @param bookId ID do livro.
     * @return boolean dizendo se o livro existe ou não.
     */
    @Query("SELECT CASE WHEN COUNT(ub) > 0 THEN true ELSE false END FROM UserBook ub WHERE ub.user.id = :userId AND ub.book.id = :bookId AND ub.statusActive = true")
    boolean verificarLivroNaEstante(@Param("userId") Integer userId, @Param("bookId") Integer bookId);

    /**
     * SOFT DELETE: Apenas desativa o registro, mantendo histórico
     * @param userId ID do usuário logado.
     * @param bookId ID do livro.
     */
    @Modifying
    @Query("UPDATE UserBook ub SET ub.statusActive = false WHERE ub.user.id = :userId AND ub.book.id = :bookId")
    void desativarLivro(@Param("userId") Integer userId, @Param("bookId") Integer bookId);
}