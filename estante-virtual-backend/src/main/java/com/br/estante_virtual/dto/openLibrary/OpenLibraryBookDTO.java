package com.br.estante_virtual.dto.openLibrary;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OpenLibraryBookDTO(
        @JsonProperty("key") String idOpenLibrary,
        @JsonProperty("title") String title,
        @JsonProperty("author_name") List<String> authors, // api retorna lista
        @JsonProperty("first_publish_year") Integer firstPublishYear,
        @JsonProperty("isbn") List<String> isbnList, // api retorna lista
        @JsonProperty("cover_i") Integer coverId,
        @JsonProperty("number_of_pages_median") Integer pageCount,
        @JsonProperty("publisher")
        @JsonAlias("publishers")
        List<String> publishers, // api retorna lista
        @JsonProperty("first_sentence") List<String> firstSentence // tentativa de pegar algo parecido com sinopse
) {
}
