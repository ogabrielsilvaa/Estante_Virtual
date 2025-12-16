package com.br.estante_virtual.controller;

import com.br.estante_virtual.dto.request.book.BookAtualizarDTORequest;
import com.br.estante_virtual.dto.request.book.BookDTORequest;
import com.br.estante_virtual.dto.response.BookDTOResponse;
import com.br.estante_virtual.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book", description = "API para gerenciamento de livros do usuário autenticado.")
public class BookController {

    private BookService bookService;

    /**
     * Construtor para injeção de dependência do BookService.
     * @param bookService O serviço que contém a lógica de negócio para livros.
     */
    public BookController(BookService bookService) { this.bookService = bookService; }

    /**
     * Lista todos os livros ativos do usuário autenticado.
     * @return Uma lista de livros do usuário.
     */
    @GetMapping("/listarLivrosAtivos")
    public ResponseEntity<List<BookDTOResponse>> listarLivros() {
        return ResponseEntity.ok(bookService.listarLivrosAtivos());
    }

    /**
     * Lista todos os livros inativos do usuário autenticado.
     * @return Uma lista de livros do usuário.
     */
    @GetMapping("/listarLivrosInativos")
    public ResponseEntity<List<BookDTOResponse>> listarLivrosInativos() {
        return ResponseEntity.ok(bookService.listarLivrosInativos());
    }

    /**
     * Busca um livro específico pelo seu ID, garantindo que pertença ao usuário autenticado.
     * @param livroId O ID do livro a ser buscado.
     * @return o medicamento encontrado.
     */
    @GetMapping("/listarLivroPorId/{livroId}")
    @Operation(summary = "Listar o livro pelo ID dele.", description = "Endpoint para listar um Livro específico do usuário logado.")
    public ResponseEntity<BookDTOResponse> listarLivroPorId(
            @PathVariable("livroId") Integer livroId
    ) {
        BookDTOResponse bookDTOResponse = bookService.listarLivroPorId(livroId);
        return ResponseEntity.ok(bookDTOResponse);
    }

    /**
     * Cria um novo livro para o usuário autenticado.
     * @param bookDTORequest O DTO contendo os dados do livro a ser criado.
     * @return O livro recém-criado.
     */
    @PostMapping("/cadastrar")
    public ResponseEntity<BookDTOResponse> cadastrarBook(
            @Valid @RequestBody BookDTORequest bookDTORequest
            ) {

        BookDTOResponse bookDTOResponse = bookService.cadastrarLivro(bookDTORequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookDTOResponse);
    }

    /**
     * Atualiza um livro existente do usuário autenticado.
     * @param livroId O ID do livro a ser atualizado.
     * @param bookAtualizarDTORequest O DTO contendo os dados para atualização (apenas os campos que devem ser alterados).
     * @return O livro autalizado.
     */
    @PutMapping("/atualizar/{livroId}")
    @Operation(summary = "Atualizar todos os dados do Livro.", description = "Endpoint para atualizar o registro do Livro existente do usuário logado.")
    public ResponseEntity<BookDTOResponse> atualizarLivroPorId(
            @PathVariable("livroId") Integer livroId,
            @RequestBody @Valid BookAtualizarDTORequest bookAtualizarDTORequest
            ) {

        BookDTOResponse livroAtualizado = bookService.atualizarLivroPorId(livroId, bookAtualizarDTORequest);
        return ResponseEntity.ok(livroAtualizado);
    }

    /**
     * Realiza a exclusão lógica de um livro do usuário autenticado.
     * @param livroId O ID do livro a ser desativado.
     * @return Resposta sem conteúdo.
     */
    @DeleteMapping("/deletar/{livroId}")
    @Operation(summary = "Deletar todos os dados do Livro.", description = "Endpoint para deletar o registro do Livro do usuário logado.")
    public ResponseEntity<Void> deletarLivro(
            @PathVariable("livroId") Integer livroId
    ) {
        bookService.deletarLogico(livroId);
        return ResponseEntity.noContent().build();
    }

}
