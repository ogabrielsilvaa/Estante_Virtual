package com.br.estante_virtual.service;

import com.br.estante_virtual.dto.request.readingNote.ReadingNoteAtualizarDTORequest;
import com.br.estante_virtual.dto.request.readingNote.ReadingNoteDTORequest;
import com.br.estante_virtual.dto.response.ReadingNoteDTOResponse;
import com.br.estante_virtual.entity.ReadingNote;
import com.br.estante_virtual.entity.UserBook;
import com.br.estante_virtual.enums.ReadingNoteStatus;
import com.br.estante_virtual.mapper.ReadingNoteMapper;
import com.br.estante_virtual.repository.ReadingNoteRepository;
import com.br.estante_virtual.repository.UserBookRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Camada de serviço para gerenciar a lógica de negócio do catálogo de notas.
 */
@Service
public class ReadingNoteService {

    private final ReadingNoteRepository readingNoteRepository;
    private final UserBookRepository userBookRepository;

    private final ReadingNoteMapper readingNoteMapper;

    @Autowired
    public ReadingNoteService(
            ReadingNoteRepository readingNoteRepository,
            UserBookRepository userBookRepository,
            ReadingNoteMapper readingNoteMapper
    ) {
        this.readingNoteRepository = readingNoteRepository;
        this.userBookRepository = userBookRepository;
        this.readingNoteMapper = readingNoteMapper;
    }

    /**
     * Busca as notas que estão com status desejado.
     * @return lista de {@link ReadingNoteDTOResponse} das notas existentes.
     */
    public Page<ReadingNoteDTOResponse> listarNotasPorStatus(Integer userId, ReadingNoteStatus status, Pageable pageable) {
        return readingNoteRepository.listarPorStatusEUsuario(status, userId, pageable)
                .map(ReadingNoteDTOResponse::new);
    }

    /**
     * Busca uma note pelo seu ID.
     *
     * @param noteId ID da nota a ser buscada.
     * @param userId ID do usuário logado.
     * @return o {@link ReadingNoteDTOResponse} da nota existente.
     */
    public ReadingNoteDTOResponse listarNotaPorId(Integer noteId, Integer userId) {
        ReadingNote verifiedNote = validarReadingNote(noteId, userId);
        return new ReadingNoteDTOResponse(verifiedNote);
    }

    /**
     * Cadastra uma nova nota, validando se o livro está na estante.
     * @param dtoRequest Dados da avaliação (página, texto e IDs).
     * @param userId ID do usuário logado.
     * @return o {@link ReadingNoteDTOResponse} da nota criada.
     */
    @Transactional
    public ReadingNoteDTOResponse cadastrarNota(ReadingNoteDTORequest dtoRequest, Integer userId) {
        UserBook userBook = userBookRepository.listarPorId(userId, dtoRequest.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Este livro não está na estante do usuário."));

        ReadingNote newNote = readingNoteMapper.toEntity(dtoRequest, userBook);

        ReadingNote savedNote = readingNoteRepository.save(newNote);
        return new ReadingNoteDTOResponse(savedNote);
    }

    /**
     * Atualiza os dados de uma nota existente.
     * @param noteId ID da nota a ser buscada.
     * @param dtoAtualizar DTO com os novos dados.
     * @param userId ID do usuário logado.
     * @return o {@link ReadingNoteDTOResponse} da nota atualizada.
     */
    @Transactional
    public ReadingNoteDTOResponse atualizarNotaPorId(
            Integer noteId,
            ReadingNoteAtualizarDTORequest dtoAtualizar,
            Integer userId
    ) {
        ReadingNote existingNote = validarReadingNote(noteId, userId);

        readingNoteMapper.updateEntity(existingNote, dtoAtualizar);

        ReadingNote updatedNote = readingNoteRepository.save(existingNote);

        return new ReadingNoteDTOResponse(updatedNote);
    }

    /**
     * Realiza a exclusão lógica de uma nota.
     * @param noteId ID da nota a ser deletada lógicamente.
     * @param userId ID do usuário logado.
     */
    @Transactional
    public void deletarLogico(Integer noteId, Integer userId) {
        validarReadingNote(noteId, userId);
        readingNoteRepository.deletarLogicamente(noteId, ReadingNoteStatus.INATIVO);
    }


    /**
     * Valida a existência de uma nota e sua posse pelo usuário.
     * @param noteId ID da nota a ser buscada.
     * @param userId ID do usuário logado.
     * @return A entidade {@link ReadingNote} encontrada.
     * @throws EntityNotFoundException se a nota não for encontrada.
     */
    private ReadingNote validarReadingNote(Integer noteId, Integer userId) {
        return readingNoteRepository.listarPorIdEUsuario(noteId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Nota não encontrada ou acesso negado (ID: \" + reviewId + \")"));
    }
}
