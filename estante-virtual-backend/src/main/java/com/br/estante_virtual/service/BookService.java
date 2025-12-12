package com.br.estante_virtual.service;

import com.br.estante_virtual.dto.request.BookDTORequest;
import com.br.estante_virtual.dto.response.BookDTOResponse;
import com.br.estante_virtual.entity.Book;
import com.br.estante_virtual.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Camada de serviço para gerenciar a lógica de negócio do catálogo de livros
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(
            BookRepository bookRepository
    ) {
        this.bookRepository = bookRepository;
    }

    public List<BookDTOResponse> listarLivros() {
        return bookRepository.findAll()
                .stream()
                .map(BookDTOResponse::new)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public BookDTOResponse cadastrarLivro(BookDTORequest bookDTORequest) {
        Book novoLivro = new Book();

        novoLivro.setTitle(bookDTORequest.getTitle());
        novoLivro.setAuthor(bookDTORequest.getAuthor());
        novoLivro.setIsbn(bookDTORequest.getIsbn());
        novoLivro.setCoverUrl(bookDTORequest.getCoverUrl());
        novoLivro.setSynopsis(bookDTORequest.getSynopsis());
        novoLivro.setPageCount(bookDTORequest.getPageCount());
        novoLivro.setPublisher(bookDTORequest.getPublisher());
        novoLivro.setPublicationYear(bookDTORequest.getPublicationYear());

        Book livroSalvo = bookRepository.save(novoLivro);

        return new BookDTOResponse(livroSalvo);
    }
}
