package com.br.estante_virtual.repository;

import com.br.estante_virtual.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório para gerenciar as operações de banco de dados para a entidade {@link com.br.estante_virtual.entity.Book}.
 * Estende JpaRepository para funcionalidades CRUD padrão e define consultas customizadas.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    /**
     * Busca todos os livros que estão marcados como ATIVOS no sistema.
     * Usado para exibir o catálogo para os usuários.
     */
    @Query("SELECT b FROM Book b WHERE b.statusActive = true")
    Page<Book> buscarTodosAtivos(Pageable pageable);

    /**
     * Busca todos os livros que estão INATIVOS (Soft Deleted).
     * Usado para painel administrativo.
     */
    @Query("SELECT b FROM Book b WHERE b.statusActive = false")
    Page<Book> buscarTodosInativos(Pageable pageable);

    /**
     * Busca um livro específico pelo ID, mas apenas se ele estiver ATIVO.
     * Evita que um usuário acesse um livro que foi desativado via URL direta.
     */
    @Query("SELECT b FROM Book b WHERE b.id = :id AND b.statusActive = true")
    Optional<Book> buscarPorIdAtivo(@Param("id") Integer id);

    /**
     * Verifica se já existe um livro cadastrado com o ISBN informado.
     * O JPQL retorna um booleano baseado na contagem.
     */
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Book b WHERE b.isbn = :isbn")
    boolean existeIsbn(@Param("isbn") String isbn);

    /**
     * Busca textual: Permite pesquisar por Título OU Autor (apenas livros ativos).
     * Útil para barra de pesquisa da aplicação.
     */
    @Query("""
           SELECT b FROM Book b 
           WHERE b.statusActive = true 
           AND (LOWER(b.title) LIKE LOWER(CONCAT('%', :termo, '%')) 
                OR LOWER(b.author) LIKE LOWER(CONCAT('%', :termo, '%')))
           """)
    Page<Book> pesquisarPorTituloOuAutor(@Param("termo") String termo, Pageable pageable);

    Optional<Book> findByIsbn(String isbn);
}
