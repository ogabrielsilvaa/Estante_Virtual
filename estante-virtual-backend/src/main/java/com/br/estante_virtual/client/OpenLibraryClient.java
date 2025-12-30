package com.br.estante_virtual.client;

import com.br.estante_virtual.dto.openLibrary.OpenLibrarySearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "openLibraryClient", url = "https://openlibrary.org")
public interface OpenLibraryClient {

    @GetMapping("/search.json?fields=key,title,author_name,first_publish_year,isbn,cover_i,number_of_pages_median,publisher,first_sentence")
    OpenLibrarySearchResponse buscarLivros(@RequestParam("q") String query);
}
