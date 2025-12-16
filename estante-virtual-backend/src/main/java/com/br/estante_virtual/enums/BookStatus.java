package com.br.estante_virtual.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BookStatus {
    ATIVO(1, "Ativo"),
    INATIVO(0, "Inativo");

    private final int id;
    private final String descricao;

    BookStatus(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public int getId() { return id; }
    public String getDescricao() { return descricao; }

    @JsonCreator
    public static BookStatus fromId(int value) {
        for (BookStatus status : BookStatus.values()) {
            if (status.id == value) return status;
        }
        throw new IllegalArgumentException("Status do livro inválido: " + value);
    }
}
