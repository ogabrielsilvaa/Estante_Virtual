package com.br.estante_virtual.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ReadingNoteStatus {

    ATIVO(1, "ATIVO"),
    INATIVO(2, "INATIVO");

    private final int id;
    private final String descricao;

    ReadingNoteStatus(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    /**
     * Lógica para converter o número que vem do JSON (ex: 1)
     * no Enum correspondente (ATIVO).
     */
    @JsonCreator
    public static ReadingNoteStatus fromId(Integer value) {
        if (value == null) return null;

        for (ReadingNoteStatus status : ReadingNoteStatus.values()) {
            if (status.id == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status de nota inválido: " + value);
    }

}
