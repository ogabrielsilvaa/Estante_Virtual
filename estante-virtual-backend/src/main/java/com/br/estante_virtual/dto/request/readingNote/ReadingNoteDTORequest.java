package com.br.estante_virtual.dto.request.readingNote;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class ReadingNoteDTORequest {

    @NotNull(message = "O ID do livro é obrigatório.")
    private Integer bookId;

    @NotNull(message = "O número de páginas é obrigatório.")
    @Positive(message = "O número de páginas deve ser maior que zero.")
    private Integer page;

    @Size(max = 5000, message = "A nota deve ter no máximo 5000 caracteres.")
    private String note;

    public ReadingNoteDTORequest() {
    }

    public ReadingNoteDTORequest(Integer bookId, Integer page, String note) {
        this.bookId = bookId;
        this.page = page;
        this.note = note;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
