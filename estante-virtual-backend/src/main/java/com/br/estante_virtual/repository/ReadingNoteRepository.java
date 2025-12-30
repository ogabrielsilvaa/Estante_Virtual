package com.br.estante_virtual.repository;

import com.br.estante_virtual.entity.ReadingNote;
import com.br.estante_virtual.enums.ReadingNoteStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReadingNoteRepository extends JpaRepository<ReadingNote, Integer> {

    /**
     * Lista as notas de um livro específico na estante, filtrando pelo status.
     * Exemplo de uso: Buscar apenas notas ATIVAS do livro 'O Hobbit'.
     *
     * @param userBookId O ID do vínculo do livro na estante.
     * @param status O status desejado (ex: ATIVO).
     * @param pageable Paginação.
     * @return Página de notas filtradas.
     */
    Page<ReadingNote> findByUserBookIdAndStatus(Integer userBookId, ReadingNoteStatus status, Pageable pageable);

    /**
     * Lista todas as notas do usuário (de todos os livros), filtrando pelo status.
     * Exemplo: "Ver todas as minhas notas ativas no sistema".
     *
     * @param status O status desejado (ex: ATIVO).
     * @param userId O ID do usuário logado.
     * @param pageable Paginação.
     * @return Página de notas.
     */
    @Query("SELECT rn FROM ReadingNote rn WHERE rn.status = :status AND rn.userBook.user.id = :userId")
    Page<ReadingNote> listarPorStatusEUsuario(
            @Param("status") ReadingNoteStatus status,
            @Param("userId") Integer userId,
            Pageable pageable
    );

    /**
     * Busca uma nota pelo ID, garantindo que pertence ao usuário.
     * Usado para validar a posse antes de editar ou excluir.
     */
    @Query("SELECT rn FROM ReadingNote rn WHERE rn.id = :noteId AND rn.userBook.user.id = :userId")
    Optional<ReadingNote> listarPorIdEUsuario(@Param("noteId") Integer noteId, @Param("userId") Integer userId);

    /**
     * Realiza a exclusão lógica da nota.
     * Atualiza o status sem apagar o registro do banco.
     *
     * @param noteId O ID da nota.
     * @param novoStatus O novo status (ex: INATIVO).
     */
    @Modifying
    @Transactional
    @Query("UPDATE ReadingNote rn SET rn.status = :novoStatus WHERE rn.id = :noteId")
    void deletarLogicamente(@Param("noteId") Integer noteId, @Param("novoStatus") ReadingNoteStatus novoStatus);
}