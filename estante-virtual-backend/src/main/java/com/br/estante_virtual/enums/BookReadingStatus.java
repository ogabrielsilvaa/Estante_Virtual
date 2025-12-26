package com.br.estante_virtual.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BookReadingStatus {
    QUERO_LER(0, "QUERO_LER"),
    LENDO(1, "LENDO"),
    LIDO(2, "LIDO"),
    PENDENTE(3, "PENDENTE"),
    ABANDONEI(4, "ABANDONEI");

    private final int id;
    private final String descricao;

    BookReadingStatus(int id, String descricao) {
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
     * @JsonCreator: Ensina o Jackson a lidar com o número que vem do Front-end.
     * Quando chegar {"status": 1}, ele chama este método.
     */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static BookReadingStatus fromId(int value) {
        for (BookReadingStatus status : BookReadingStatus.values()) {
            if (status.id == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + value);
    }
}
