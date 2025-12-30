package com.br.estante_virtual.dto.openLibrary;

import java.util.List;

public record OpenLibrarySearchResponse(
        Integer numFound,
        List<OpenLibraryBookDTO> docs
) {
}
