package com.br.estante_virtual.dto.request.readingNote;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ReadingNoteAtualizarDTORequest {

    @NotNull(message = "O número de páginas é obrigatório.")
    @Positive(message = "O número de páginas deve ser maior que zero.")
    private Integer page;

    @Size(max = 5000, message = "A nota deve ter no máximo 5000 caracteres.")
    private String note;

    public ReadingNoteAtualizarDTORequest() {
    }

    public ReadingNoteAtualizarDTORequest(Integer page, String note) {
        this.page = page;
        this.note = note;
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
