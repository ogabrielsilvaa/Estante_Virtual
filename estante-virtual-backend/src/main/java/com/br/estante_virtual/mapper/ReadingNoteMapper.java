package com.br.estante_virtual.mapper;

import com.br.estante_virtual.dto.request.readingNote.ReadingNoteAtualizarDTORequest;
import com.br.estante_virtual.dto.request.readingNote.ReadingNoteDTORequest;
import com.br.estante_virtual.entity.ReadingNote;
import com.br.estante_virtual.entity.UserBook;
import org.springframework.stereotype.Component;

/**
 * Componente responsável pela conversão e mapeamento de dados entre DTOs e a entidade ReadingNote (Notas).
 */
@Component
public class ReadingNoteMapper {

    /**
     * Converte um DTO de criação em uma nova entidade ReadingNote.
     * Associa a avaliação ao registro do livro na estante (UserBook).
     *
     * @param dtoRequest Dados da nota (página, texto).
     * @param userBook O registro do livro na estante do usuário que está sendo avaliado.
     * @return Uma nova instância de {@link ReadingNote} preenchida.
     */
    public ReadingNote toEntity(ReadingNoteDTORequest dtoRequest, UserBook userBook) {
        ReadingNote newNote = new ReadingNote();

        newNote.setUserBook(userBook);
        newNote.setPage(dtoRequest.getPage());
        newNote.setNote(dtoRequest.getNote());

        return newNote;
    }

    /**
     * Atualiza os dados de uma nota existente.
     * Realiza atualização parcial (apenas campos não nulos no DTO são alterados).
     *
     * @param readingNote A entidade de nota a ser atualizada.
     * @param dtoAtualizar O DTO contendo os novos dados (notas, texto ou status).
     */
    public void updateEntity(ReadingNote readingNote, ReadingNoteAtualizarDTORequest dtoAtualizar) {
        if (dtoAtualizar.getPage() != null) {
            readingNote.setPage(dtoAtualizar.getPage());
        }

        if (dtoAtualizar.getNote() != null) {
            readingNote.setNote(dtoAtualizar.getNote());
        }
    }
}
