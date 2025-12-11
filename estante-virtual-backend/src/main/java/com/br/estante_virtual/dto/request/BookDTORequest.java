package com.br.estante_virtual.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.URL;

public record BookDTORequest(

        @NotBlank(message = "Não é possível cadastrar um livro sem inserir um título.")
        String title,

        @NotBlank(message = "O autor do livro é obrigatório.")
        String author,

        @NotBlank(message = "O ISBN é obrigatório.")
        String isbn,

        @NotBlank(message = "A URL da capa é obrigatória.")
        @URL(message = "A URL da capa informada não é válida.")
        String coverUrl,

        String synopsis,

        @NotNull(message = "O número de páginas é obrigatório.")
        @Positive(message = "O número de páginas deve ser maior que zero.")
        Integer pageCount,

        String publisher,

        @NotNull(message = "O ano de publicação é obrigatório.")
        @Min(value = 1000, message = "Insira um ano válido.")
        Integer publicationYear
) {
}
