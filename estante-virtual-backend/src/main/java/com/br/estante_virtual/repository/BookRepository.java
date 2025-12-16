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

/**
 * Repositório para gerenciar as operações de banco de dados para a entidade {@link com.br.estante_virtual.entity.Book}.
 * Estende JpaRepository para funcionalidades CRUD padrão e define consultas customizadas.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    /**
     * Lista todos os livros que correspondem ao status informado.
     * * @param status O status para filtragem (ex: ATIVO).
     * @return Uma lista de livros com o status especificado.
     */
    @Query("SELECT b FROM Book b WHERE b.status = :status")
    List<Book> listarPorStatus(@Param("status") BookStatus status);

    /**
     * Busca um livro específico pelo seu identificador único.
     * * @param bookId O ID do livro.
     * @return A entidade Book encontrada.
     */
    @Query("SELECT b FROM Book b WHERE b.id = :bookId")
    Book listarLivroPorId(@Param("bookId") Integer bookId);

    /**
     * Realiza a exclusão lógica do livro.
     * @param bookId O ID do livro.
     * @param status O status para o qual o livro será atualizado (deve ser INATIVO).
     */
    @Modifying
    @Transactional
    @Query("UPDATE Book b SET b.status = :statusInativo WHERE b.id = :bookId")
    void apagarLivroLogico(@Param("bookId") Integer bookId, @Param("statusInativo") BookStatus status);

}
