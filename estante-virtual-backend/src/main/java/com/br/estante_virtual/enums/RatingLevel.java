package com.br.estante_virtual.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum RatingLevel {

    FRACO(1, "Fraco"),
    RAZOAVEL(2, "Razoável"),
    BOM(3, "Bom"),
    MUITO_BOM(4, "Muito Bom"),
    EXCELENTE(5, "Excelente");

    private final Integer codigo;
    private final String descricao;

    RatingLevel(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    /**
     * Quando chegar um número no JSON (Request),
     * ele roda esse método para achar o Enum correspondente.
     */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static RatingLevel toEnum(Integer codigo) {
        if (codigo == null) return null;
        return Stream.of(RatingLevel.values())
                .filter(c -> c.getCodigo().equals(codigo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Código de avaliação inválido: " + codigo));
    }

}
