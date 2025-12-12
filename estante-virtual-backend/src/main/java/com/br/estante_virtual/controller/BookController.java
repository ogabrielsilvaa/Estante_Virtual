package com.br.estante_virtual.controller;

import com.br.estante_virtual.dto.request.BookDTORequest;
import com.br.estante_virtual.dto.response.BookDTOResponse;
import com.br.estante_virtual.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) { this.bookService = bookService; }

    @GetMapping("/listar")
    public ResponseEntity<List<BookDTOResponse>> listarBooks() {
        return ResponseEntity.ok(bookService.listarLivros());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<BookDTOResponse> cadastrarBook(
            @Valid @RequestBody BookDTORequest bookDTORequest
            ) {
        BookDTOResponse bookDTOResponse = bookService.cadastrarLivro(bookDTORequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookDTOResponse);
    }
}
