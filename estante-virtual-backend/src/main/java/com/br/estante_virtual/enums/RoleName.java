package com.br.estante_virtual.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RoleName {
    ROLE_ADMIN(1, "Administrador"),
    ROLE_CUSTOMER(2, "Usuário");

    private final int id;
    private final String descricao;

    RoleName(int id, String descricao) {
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
     * Lógica para converter o número que vem do JSON (ex: 2)
     * no Enum (name) correspondente (ROLE_CUSTOMER).
     */
    @JsonCreator
    public static RoleName fromId(int value) {
        for (RoleName status : RoleName.values()) {
            if (status.id == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Rolename inválida: " + value);
    }
}
