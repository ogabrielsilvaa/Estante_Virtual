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
     * @param bookId O ID do livro a ser buscado.
     * @return o medicamento encontrado.
     */
    @GetMapping("/listarLivroPorId/{bookId}")
    @Operation(summary = "Listar o livro pelo ID dele.", description = "Endpoint para listar um Livro específico do usuário logado.")
    public ResponseEntity<BookDTOResponse> listarLivroPorId(
            @PathVariable("bookId") Integer bookId
    ) {
        BookDTOResponse dtoResponse = bookService.listarLivroPorId(bookId);
        return ResponseEntity.ok(dtoResponse);
    }

    /**
     * Cria um novo livro para o usuário autenticado.
     * @param dtoRequest O DTO contendo os dados do livro a ser criado.
     * @return O livro recém-criado.
     */
    @PostMapping("/cadastrar")
    public ResponseEntity<BookDTOResponse> cadastrarBook(
            @Valid @RequestBody BookDTORequest dtoRequest
            ) {

        BookDTOResponse dtoResponse = bookService.cadastrarLivro(dtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoResponse);
    }

    /**
     * Atualiza um livro existente do usuário autenticado.
     * @param bookId O ID do livro a ser atualizado.
     * @param atualizarDTORequest O DTO contendo os dados para atualização (apenas os campos que devem ser alterados).
     * @return O livro autalizado.
     */
    @PatchMapping("/atualizar/{livroId}")
    @Operation(summary = "Atualizar todos os dados do Livro.", description = "Endpoint para atualizar o registro do Livro existente do usuário logado.")
    public ResponseEntity<BookDTOResponse> atualizarLivroPorId(
            @PathVariable("livroId") Integer bookId,
            @RequestBody @Valid BookAtualizarDTORequest atualizarDTORequest
            ) {

        BookDTOResponse livroAtualizado = bookService.atualizarLivroPorId(bookId, atualizarDTORequest);
        return ResponseEntity.ok(livroAtualizado);
    }

    /**
     * Realiza a exclusão lógica de um livro do usuário autenticado.
     * @param bookId O ID do livro a ser desativado.
     * @return Resposta sem conteúdo.
     */
    @DeleteMapping("/deletar/{bookId}")
    @Operation(summary = "Deletar todos os dados do Livro.", description = "Endpoint para deletar o registro do Livro do usuário logado.")
    public ResponseEntity<Void> deletarLivro(
            @PathVariable("bookId") Integer bookId
    ) {
        bookService.deletarLogico(bookId);
        return ResponseEntity.noContent().build();
    }

}
