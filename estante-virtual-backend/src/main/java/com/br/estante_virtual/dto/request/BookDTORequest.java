package com.br.estante_virtual.dto.request;

import jakarta.validation.constraints.NotBlank;

public record BookDTORequest(

        @NotBlank(message = "Não é possível cadastrar um livro sem inserir um título.")
        String title,
        String author,
        String isbn,
        String coverUrl,
        String synopsis,
        Integer pageCount,
        String publisher,
        Integer publicationYear
) {
}
