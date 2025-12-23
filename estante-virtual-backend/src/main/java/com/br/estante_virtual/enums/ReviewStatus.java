package com.br.estante_virtual.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ReviewStatus {

    PUBLICADO(1, "Publicado"),
    RASCUNHO(2, "Rascunho"),
    APAGADO(3, "Apagado");

    private final int id;
    private final String descricao;

    ReviewStatus(int id, String descricao) {
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
     * no Enum correspondente (PUBLICADO).
     */
    @JsonCreator
    public static ReviewStatus fromId(Integer value) {
        if (value == null) return null;

        for (ReviewStatus status : ReviewStatus.values()) {
            if (status.id == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status de review inválido: " + value);
    }
}
