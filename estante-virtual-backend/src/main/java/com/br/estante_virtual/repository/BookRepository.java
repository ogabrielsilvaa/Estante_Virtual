package com.br.estante_virtual.repository;

import com.br.estante_virtual.entity.Book;
import com.br.estante_virtual.enums.BookStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para gerenciar as operações de banco de dados para a entidade {@link com.br.estante_virtual.entity.Book}.
 * Estende JpaRepository para funcionalidades CRUD padrão e define consultas customizadas.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    /**
     * Lista todos os livros que correspondem ao status informado.
     * @param status 1 status para filtragem (ex: ATIVO).
     * @param userId o ID do usuário logado.
     * @return Uma lista de livros com o status especificado.
     */
    @Query("SELECT ub.book FROM UserBook ub WHERE ub.book.status = :status AND ub.user.id = :userId")
    List<Book> listarPorStatusEUsuario(@Param("status") BookStatus status, @Param("userId") Integer userId);

    /**
     * Busca um livro específico pelo seu identificador único.
     * @param bookId O ID do livro.
     * @param userId o ID do usuário logado.
     * @return A entidade Book encontrada.
     */
    @Query("SELECT ub.book FROM UserBook ub WHERE ub.book.id = :bookId AND ub.user.id = :userId")
    Optional<Book> listarLivroPorIdEUsuario(@Param("bookId") Integer bookId, @Param("userId") Integer userId);

}
