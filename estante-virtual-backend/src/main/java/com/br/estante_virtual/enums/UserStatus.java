package com.br.estante_virtual.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserStatus {

    ATIVO(1, "Ativo"),
    INATIVO(2, "Inativo"),
    APAGADO(3, "Excluído");

    private final int id;
    private final String descricao;

    UserStatus(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getNome() {
        return name();
    }

    /**
     * Lógica para converter o número que vem do JSON (ex: 1)
     * no Enum correspondente (ATIVO).
     */
    @JsonCreator
    public static UserStatus fromId(int value) {
        for (UserStatus status : UserStatus.values()) {
            if (status.id == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status de usuário inválido: " + value);
    }
}